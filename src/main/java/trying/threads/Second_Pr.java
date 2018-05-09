package trying.threads;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

public class Second_Pr {
    public static void main(String[] args) throws InterruptedException, IOException {
        while(true) {
            try {
                RandomAccessFile rafile = new RandomAccessFile("C:\\Users\\Svetlana\\Downloads\\multiprocess\\test.txt", "rw");
                FileChannel fileChannel = rafile.getChannel();
                FileLock lock= fileChannel.lock();
                read(fileChannel);
//                MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1);
//                String msg = "f";
//                mbb.put(msg.getBytes());
//                mbb.force();
//                read(fileChannel);
                lock.release();
                fileChannel.close();
                rafile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static String decode(byte[] bytes) {return new String(bytes, UTF8_CHARSET);}
    private static void read(FileChannel fileChannel) throws IOException {        //Create file object
        RandomAccessFile rafile = new RandomAccessFile("test.txt", "rw");
        fileChannel = rafile.getChannel();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10000);

        // the buffer now reads the file as if it were loaded in memory.
//        System.out.println(buffer.isLoaded());  //prints false
//        System.out.println(buffer.capacity());  //Get the size based on content size of file

        //You can read the file from this buffer the way you like.

        byte[] b = new byte[(int) rafile.length()];
        for(int i = 0; i< rafile.length(); i++) {b[i] = buffer.get(i);};
        String actualLine = decode(b);
        System.out.printf(actualLine);
        System.exit(0);
//        String str = "";
//        for (int i = 0; i < buffer.limit(); i++)
//        {
//            str = str + (char)buffer.get();
//        }
//        System.out.println(str.trim());
//        System.out.println("---------------------------------");

    }
}
