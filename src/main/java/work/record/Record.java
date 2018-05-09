package work.record;

import java.util.*;

/**
 * Created by Света on 17.04.2018.
 */
public class Record {
    private String id;
    private String text;
    private List<String> href;
    private List<String> img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getHref() {
        return href;
    }

    public void setHref(List<String> href) {
        this.href = href;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public Record(String id, String text, List<String> href, List<String> img) {
        this.id = id;
        this.text = text;
        this.href = href;
        this.img = img;
    }

    public Record(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", href=" + href +
                ", img=" + img +
                '}';
    }
}
