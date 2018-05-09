package trying.threads;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class Test_Class {
    public static void main(String[] args) throws InterruptedException, IOException {
        while(true) {
            try {
                String msg = "fa";
                RandomAccessFile rafile = new RandomAccessFile("C:\\Users\\misch\\OneDrive\\IdeaProjects\\multiprocess\\test.txt", "rw");
                FileChannel fileChannel = rafile.getChannel();
                FileLock lock= fileChannel.lock();
               // read(fileChannel);
                System.out.println(msg.getBytes().length + " msgBL");
                MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, rafile.length(), msg.getBytes().length);
                System.out.println(rafile.length()+" rafile len "+msg.getBytes().length + " msgBL");
               // rafile.setLength(rafile.length()+msg.getBytes().length);
                mbb.put(msg.getBytes());
                mbb.force();
                //read(fileChannel);
                lock.release();
                fileChannel.close();
                rafile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void read(FileChannel fileChannel) throws IOException {        //Create file object
        RandomAccessFile rafile = new RandomAccessFile("C:\\Users\\misch\\.eclipse\\MireaHomeWork\\arr.json", "rw");
        fileChannel = rafile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10000);

        // the buffer now reads the file as if it were loaded in memory.
//        System.out.println(buffer.isLoaded());  //prints false
//        System.out.println(buffer.capacity());  //Get the size based on content size of file

        //You can read the file from this buffer the way you like.
        String str = "";
        for (int i = 0; i < buffer.limit(); i++)
        {
            str = str + (char)buffer.get();
        }
        System.out.println(str.trim());
        System.out.println("---------------------------------");

    }
}