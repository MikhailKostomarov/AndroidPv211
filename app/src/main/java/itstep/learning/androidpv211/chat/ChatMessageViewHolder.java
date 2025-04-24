package itstep.learning.androidpv211.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import itstep.learning.androidpv211.R;
import itstep.learning.androidpv211.orm.ChatMessage;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {
    public static final SimpleDateFormat momentFormat=
            new SimpleDateFormat("dd.MM HH:mm", Locale.ROOT);
    public static final SimpleDateFormat timeOnly =
            new SimpleDateFormat("HH:mm", Locale.ROOT);
    private ChatMessage chatMessage;
    private final TextView tvAuthor;
    private final TextView tvText;
    private final TextView tvMoment;


    public ChatMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        tvAuthor=itemView.findViewById(R.id.chat_msg_author);
        tvText=itemView.findViewById(R.id.chat_msg_text);
        tvMoment=itemView.findViewById(R.id.chat_msg_moment);
    }
    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
        tvAuthor.setText(this.chatMessage.getAuthor());
        tvText.setText(this.chatMessage.getText());
        Date msgDate = this.chatMessage.getMoment();
        Calendar now = Calendar.getInstance();
        Calendar msgCalendar = Calendar.getInstance();
        msgCalendar.setTime(msgDate);

        now.set(Calendar.HOUR_OF_DAY,0);
        now.set(Calendar.MINUTE,0);
        now.set(Calendar.SECOND,0);
        now.set(Calendar.MILLISECOND,0);
        msgCalendar.set(Calendar.HOUR_OF_DAY,0);
        msgCalendar.set(Calendar.MINUTE,0);
        msgCalendar.set(Calendar.SECOND,0);
        msgCalendar.set(Calendar.MILLISECOND,0);

        long difDays=(now.getTimeInMillis()-msgCalendar.getTimeInMillis())/(1000*3600*24);
        if(difDays==0){
            tvMoment.setText("Today "+timeOnly.format(msgDate));
        } else if (difDays==1) {
            tvMoment.setText("Yesterday "+timeOnly.format(msgDate));
        } else if (difDays<=7) {
            tvMoment.setText(difDays+" days ago "+timeOnly.format(msgDate));
        } else {
            tvMoment.setText(momentFormat.format(msgDate));
        }

    }
}
