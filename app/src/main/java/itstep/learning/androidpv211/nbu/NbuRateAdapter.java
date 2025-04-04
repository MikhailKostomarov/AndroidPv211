package itstep.learning.androidpv211.nbu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import itstep.learning.androidpv211.R;
import itstep.learning.androidpv211.orm.NbuRate;

public class NbuRateAdapter extends RecyclerView.Adapter<NbuRateViewHolder> {
    private final List<NbuRate> nbuRates;

    public NbuRateAdapter(List<NbuRate> nbuRates) {
        this.nbuRates = nbuRates;
    }

    @NonNull
    @Override
    public NbuRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nbu_rate_layout,parent,false);
        return new NbuRateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NbuRateViewHolder holder, int position) {
    holder.setNbuRate(nbuRates.get(position));
    }

    @Override
    public int getItemCount() {
        return nbuRates.size();
    }
}
/*
RecyclerView - виджет для ленивого отображения элементов, аналогия с VirtualDOM
- элементы, которые не в поле зрения  - не прорабатываются
- элементы, которые не обновляются (как данные) - не перерисовываются
*/
