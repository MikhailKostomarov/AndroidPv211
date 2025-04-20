package itstep.learning.androidpv211.orm;

import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {
    private static final SimpleDateFormat dateFormat=
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
    private static final SimpleDateFormat sqliteFormat=
            new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
    private String id;
    private String author;
    private String text;
    private Date moment;
    public ChatMessage(){}
    public ChatMessage(String author, String text){
        this.author=author;
        this.text=text;

    }
    public static ChatMessage fromCursor(Cursor cursor){
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setId(cursor.getString(0));
        chatMessage.setAuthor(cursor.getString(1));
        chatMessage.setText(cursor.getString(2));
        try{
            chatMessage.setMoment(sqliteFormat.parse(cursor.getString(3)));
        }
        catch (Exception ignore){}
        return chatMessage;
    }
    public static ChatMessage fromJsonObject(JSONObject jsonObject) throws JSONException {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setId(jsonObject.getString("id"));
        chatMessage.setAuthor(jsonObject.getString("author"));
        chatMessage.setText(jsonObject.getString("text"));
        try {
            chatMessage.setMoment(dateFormat.parse(jsonObject.getString("moment")));
        } catch (ParseException ex) {
            throw new JSONException(ex.getMessage());
        }
        return chatMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }



}
