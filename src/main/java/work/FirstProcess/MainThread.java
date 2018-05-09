package work.FirstProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import work.FirstProcess.Threads.Img;
import work.FirstProcess.Threads.Text;
import work.FirstProcess.Threads.Url;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;

public class MainThread implements Runnable{
    private WebDriver driver;
    private Writing writing;

    MainThread(WebDriver driver, Writing writing){
        this.driver = driver;
        this.writing = writing;
    }

    public void run() {
        System.out.println("Main");
        while(writing.count < 50){
            Thread text = new Thread(new Text(driver, writing), "text");
            Thread pic = new Thread(new Img(driver, writing), "img");
            Thread web = new Thread(new Url(driver, writing), "url");
            text.start();
            pic.start();
            web.start();
            try {
                text.join();
                pic.join();
                web.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //проверить
            List<WebElement> newWebElementList = driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row_unshown\"]"));
            if(!newWebElementList.isEmpty()){
                WebElement newButton = driver.findElement(By.xpath(".//*[@id=\"feed_new_posts\"]"));
                writing.scroll(newButton,driver);
                newButton.click();
            }
            newWebElementList.clear();
            //промотать
            WebElement oldNews = driver.findElement(By.xpath(".//*[@onclick=\"return feed.showMore();\"]"));
            writing.scroll(oldNews,driver);
        }
        try {
            RandomAccessFile file = new RandomAccessFile(writing.getPath(),"rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock();
            file.seek(writing.getPos());
            file.writeBytes("\n ]\n}");
            lock.release();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
