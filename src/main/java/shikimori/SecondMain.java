package shikimori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shikimori.record.Block;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SecondMain {

    public static void main(String[] args){
        ConcurrentMap<String,Block> blocks = new ConcurrentHashMap<>();
        File f = new File("ШикиФайл.json");
        if(!f.exists()){
            System.out.println("Куда ты лезешь, оно тебя сожрет!");
            return;
        }
        try(RandomAccessFile file = new RandomAccessFile("ШикиФайл.json", "rw");
        FileChannel fileChannel = file.getChannel();
        FileLock lock = fileChannel.lock();){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            StringBuilder builder = new StringBuilder();
            String helpLine = file.readLine();
            while (helpLine!=null){
                builder.append(helpLine);
                helpLine = file.readLine();
            }
            String actualLine = builder.toString();
            lock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readLine(RandomAccessFile raf) throws IOException {
        String line = raf.readLine();
        if(line==null) return null;
        return new String(line.getBytes("ISO-8859-1"), "UTF-8");
    }
}
