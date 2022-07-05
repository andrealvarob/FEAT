package pit.feat.models;

import android.view.MotionEvent;

/**
 * Created by Usuário on 08/06/2016.
 */
public class Message {

    private String author;
    private String content;
    private String data;


    public Message(){

    }

    public Message(String _author, String _content, String _data){
        this.author = _author;
        this.content = _content;
        this.data = _data;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
