package shikimori.record;

public class Cover {
    private String name;
    private String desc;
    private String time;
    private String ep;
    private String pic;
    private String id;

    public Cover(String id) {this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getEp() {
        return ep;
    }
    public void setEp(String ep) {
        this.ep = ep;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
