package itstep.learning.androidpv211.nbu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import itstep.learning.androidpv211.R;
import itstep.learning.androidpv211.orm.NbuRate;

public class NbuRateViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTxt;
    private TextView tvCc;
    private TextView tvRate;
    private TextView tvRow;
    private TextView tvReverse;
    private NbuRate nbuRate;

    public NbuRateViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTxt=itemView.findViewById(R.id.nbu_rate_txt);
        tvRate=itemView.findViewById(R.id.nbu_rate_rate);
        tvCc=itemView.findViewById(R.id.nbu_rate_cc);
        tvRow=itemView.findViewById(R.id.nbu_rate_row);
        tvReverse=itemView.findViewById(R.id.nbu_rate_row_reverse);
    }
    private void showData(){
        tvTxt.setText(nbuRate.getTxt());
        tvCc.setText(nbuRate.getCc());
        tvRow.setText("1 "+nbuRate.getCc()+" = "+String.valueOf(nbuRate.getRate())+" HRN");
        tvReverse.setText("1 HRN = "+ String.format("%.4f", (double)1/(nbuRate.getRate()))+" "+ nbuRate.getCc());
        tvRate.setText(String.valueOf(nbuRate.getRate()));

    }
    public NbuRate getNbuRate() {
        return nbuRate;
    }

    public void setNbuRate(NbuRate nbuRate) {
        this.nbuRate = nbuRate;
        showData();
    }
}
/*
Посредник между разметкой и данными
*/