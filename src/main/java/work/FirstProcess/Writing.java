package work.FirstProcess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import work.record.Record;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Света on 17.04.2018.
 */
public class Writing {

    private ConcurrentMap<String, Record> idToRecord = new ConcurrentHashMap<>();
    private final String path;
    private boolean isFileExist;

    public long getPos() {
        return pos;
    }

    private long pos;
    public volatile List<String> existedId = new ArrayList<>();
    public volatile int count;

    public Writing(String path) {
        this.path = path;
        File f = new File(path);
        isFileExist = f.exists();
        try {
            RandomAccessFile file = new RandomAccessFile(path, "rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock();
            if(!isFileExist){
                file.writeBytes("{ \"array\": [\n");
                pos = file.getFilePointer();
            }
            else{
                //прочитать все айдишники
                String id = "";
                id = file.readLine();
                while (!id.startsWith(" ]")) {
                    id = file.readLine();
                    if(id.startsWith("{")){
                        id = file.readLine();
                        existedId.add(id.substring(9,id.length()-2));
                    }
                }
                //стереть последние символы
                pos = file.getFilePointer()-4;
            }
            lock.release();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getPath() {
        return path;
    }

    public synchronized void addText(String id, String text) {
        Record rec;
        if(idToRecord.containsKey(id)) { rec = idToRecord.get(id); }
        else { rec = new Record(id); }
        rec.setText(text);
        check(rec);
    }
    public synchronized void addPicture(String id, List<String> pic) {
        Record rec;
        if(idToRecord.containsKey(id)) { rec = idToRecord.get(id); }
        else { rec = new Record(id); }
        rec.setImg(pic);
        check(rec);
    }
    public synchronized void addHref(String id, List<String> href) {
        Record rec;
        if(idToRecord.containsKey(id)) { rec = idToRecord.get(id); }
        else { rec = new Record(id); }
        rec.setHref(href);
        check(rec);
    }

    private void check(Record rec){
        if((rec.getHref() != null)&&(rec.getImg() != null)&&(rec.getText() != null))
        {write(rec);
        count ++;
        existedId.add(rec.getId());
        idToRecord.remove(rec.getId());
        } else
        {idToRecord.put(rec.getId(),rec); }
    }

    private void write(Record rec){
        try(RandomAccessFile file = new RandomAccessFile(path,"rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json;
            if(!isFileExist){
                json = gson.toJson(rec);
                isFileExist = true;
            } else {
                json = ",\n"+ gson.toJson(rec);
            }
            byte[] inputBytes = json.getBytes();
            file.seek(pos);
            file.write(inputBytes);
            pos = file.getFilePointer();
            lock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scroll(WebElement to, WebDriver driver){
        String code = "window.scroll(" + (to.getLocation().x) + ","
                + (to.getLocation().y - 100) + ");";

        ((JavascriptExecutor)driver).executeScript(code, to);
    }
}
