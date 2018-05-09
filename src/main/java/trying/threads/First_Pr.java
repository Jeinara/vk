package trying.threads;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class First_Pr {
    public static void main(String[] args) throws InterruptedException {
//            try {
//                RandomAccessFile rafile = new RandomAccessFile("C:\\Users\\misch\\OneDrive\\IdeaProjects\\multiprocess\\test.txt", "rw");
//                FileChannel fileChannel = rafile.getChannel();
//                MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1);
//                String msg = "t";
//                mbb.put(msg.getBytes());
//                mbb.force();
//                fileChannel.close();
//                rafile.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        while(true) {
            try {
                RandomAccessFile rafile = new RandomAccessFile("C:\\Users\\misch\\OneDrive\\IdeaProjects\\multiprocess\\test.txt", "rw");
                FileChannel fileChannel = rafile.getChannel();
                FileLock lock= fileChannel.lock();
                MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1);
                String msg = "t";
                mbb.put(msg.getBytes());
                mbb.force();
                lock.release();
                fileChannel.close();
                rafile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

