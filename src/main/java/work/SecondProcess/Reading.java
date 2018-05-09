package work.SecondProcess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import work.record.Record;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Reading {

    private final String path;
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private long pos = 12;
    public boolean isOver = false;
    public List<Record> fileContent;

    public String getPath() {
        return path;
    }

    Reading(String path){
        this.path = path;
        fileContent = new ArrayList<>();
    }

    public void read(RandomAccessFile file, int c) throws IOException {
        FileChannel fileChannel = file.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 400000);
        byte[] b = new byte[(int) file.length()];
        for(int i = 0; i< file.length(); i++) {b[i] = buffer.get(i);};
        String actualLine = decode(b);
        System.out.print(actualLine);
    }

    public Record read(RandomAccessFile file) throws IOException, NullPointerException {
        StringBuilder builder = new StringBuilder();
        Record currRecord = null;
        file.seek(pos);
        String helpLine = readLine(file);
        if(helpLine == null){System.out.println("Заснул"); return currRecord;}
        if(helpLine.startsWith(" ]")){isOver = true; System.out.println("Вышел"); return currRecord;}
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        while (!helpLine.startsWith("}")){
            builder.append(helpLine);
            builder.append("\n");
            helpLine = readLine(file);
        }
        builder.append("}");
        String actualLine = builder.toString();
        if (actualLine.startsWith(",")) { actualLine = actualLine.substring(1, actualLine.length()); }
        pos = file.getFilePointer();
        System.out.println("\'"+actualLine+"\'");
        currRecord = (gson.fromJson(actualLine,Record.class));
        return currRecord;
    }
    private static String decode(byte[] bytes) {return new String(bytes, UTF8_CHARSET);}

    private String readLine(RandomAccessFile raf) throws IOException {
        String line = raf.readLine();
        if(line==null) return null;
        return new String(line.getBytes("ISO-8859-1"), "UTF-8");
    }
}
