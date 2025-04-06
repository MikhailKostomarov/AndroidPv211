package itstep.learning.androidpv211;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import itstep.learning.androidpv211.nbu.NbuRateAdapter;
import itstep.learning.androidpv211.orm.NbuRate;

public class RatesActivity extends AppCompatActivity {
    private static final String nbuRatesUrs="https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private ExecutorService pool;
    private final List<NbuRate> nbuRates=new ArrayList<>();
    private NbuRateAdapter nbuRateAdapter;
    private RecyclerView rvContainer;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rates);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets imeBars = insets.getInsets(WindowInsetsCompat.Type.ime());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                    Math.max(systemBars.bottom,imeBars.bottom));
            return insets;
        });
        pool= Executors.newFixedThreadPool(3);
        pool.submit(this::loadRates);
        //new Thread(this::loadRates).start();
        CompletableFuture.supplyAsync(this::loadRates,pool)
                .thenAccept(this::parseNbuResponse).thenRun(this::showNbuRates);
        rvContainer=findViewById(R.id.rates_rv_container);
        RecyclerView.LayoutManager layoutManager= new GridLayoutManager(this,2);
        rvContainer.setLayoutManager(layoutManager);
        nbuRateAdapter=new NbuRateAdapter(nbuRates);
        rvContainer.setAdapter(nbuRateAdapter);


        SearchView svFilter = findViewById(R.id.rates_sv_filter);
        svFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return onFilterChange(s);
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return onFilterChange(s);
            }
        });
    }

    private boolean onFilterChange(String s){
        Log.d("onFilterChange",s);
        nbuRateAdapter.setNbuRates(nbuRates.stream()
                .filter(r->r.getCc().toUpperCase().contains(s.toUpperCase()))
                .collect(Collectors.toList()));
        return true;
    }
    private  void showNbuRates(){
        runOnUiThread(()-> {
            nbuRateAdapter.notifyItemRangeChanged(0,nbuRates.size());
            tvDate= findViewById(R.id.rates_tv_date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String date = sdf.format(nbuRates.get(0).getExchangeDate());
            tvDate.setText(date);
        });
    }
    private void parseNbuResponse(String body){
        try{
            JSONArray arr = new JSONArray(body);
            //nbuRates=new ArrayList<>();
            int len= arr.length();
            for(int i=0;i<len;i++){
                nbuRates.add(NbuRate.fromJsonObject(arr.getJSONObject(i)));
            }
        } catch (JSONException ex) {
           Log.d("parseNbuResponse", "JSONException"+ex.getMessage());
        }
    }
    private String loadRates(){
        try{
            URL url=new URL(nbuRatesUrs);
            InputStream urlStream=url.openStream();
            ByteArrayOutputStream byteBuilder = new ByteArrayOutputStream();
            byte[] buffer=new byte[8192];
            int len;
            while ((len=urlStream.read(buffer))>0){
                byteBuilder.write(buffer,0,len);
            }
            String charsetName= StandardCharsets.UTF_8.name();
            String data = byteBuilder.toString(charsetName);
            urlStream.close();
            return data;
            //runOnUiThread(()->tvContainer.setText(data));

        } catch (MalformedURLException ex) {
            Log.d("LoadRates","MalformedURLException"+ex.getMessage());
        } catch (IOException ex) {
            Log.d("LoadRates","IOException"+ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        pool.shutdownNow();
        super.onDestroy();
    }
}