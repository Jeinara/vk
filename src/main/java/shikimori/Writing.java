package shikimori;

import shikimori.record.Cover;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Writing {

    private ConcurrentMap<String, Cover> idToCover = new ConcurrentHashMap<>();

    public ConcurrentMap<String, Cover> getIdToCover() {
        return idToCover;
    }

    public synchronized void addText(String id, String time,String name, String ep) {
        Cover cover;
        if(idToCover.containsKey(id)) { cover = idToCover.get(id); }
        else { cover = new Cover(id); }
        cover.setTime(time);
        cover.setName(name);
        cover.setEp(ep);
        idToCover.put(id,cover);
    }
    public synchronized void addPicture(String id, String img) {
        Cover cover;
        if(idToCover.containsKey(id)) { cover = idToCover.get(id); }
        else { cover = new Cover(id); }
        cover.setPic(img);
        idToCover.put(id,cover);
    }
    public synchronized void addDesc(String id, String desc) {
        Cover cover;
        if(idToCover.containsKey(id)) { cover = idToCover.get(id); }
        else { cover = new Cover(id); }
        cover.setDesc(desc);
        idToCover.put(id,cover);
    }


}
