package work.FirstProcess.Threads;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import work.FirstProcess.Writing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.*;
import java.util.*;

/**
 * Created by Света on 05.03.2018.
 */
public class Img extends ParentThread implements Runnable  {

    public Img(WebDriver driver, Writing writing)
    {
        super(driver,writing);
    }
    public void run() {
        System.out.println("Img");
        List<WebElement> webElementList = driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row \"]/div"));
        webElementList.addAll(driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row\"]/div")));
        webElementList = cutTheList(webElementList);
        for(WebElement e: webElementList)
        {
            List<String> p = new ArrayList<>();
            List<WebElement> pic = e.findElements(By.xpath(".//div[@class=\"wall_post_cont _wall_post_cont\"]/div[@class=\"page_post_sized_thumbs  clear_fix\"]/a"));
            if(pic.size() != 0)
            {
                //при промотке запускается видео, картинка пропадает!!!
                for(WebElement webE: pic)
                {
                    String[] ar = webE.getAttribute("style").split("url");
                    String url = ar[1].substring(2,ar[1].length()-3);
                    if(!url.contains("display")){
                        String linq = save(url);
                        p.add(linq);
                    }
                }
            }
            writing.addPicture(e.getAttribute("id"),p);
        }
    }

    //скачивание картинок
    private String save(String url) {
        String[] ar = url.split("/");
        try(
                ReadableByteChannel in=Channels.newChannel(
                        new URL(url).openStream());
                FileChannel out=new FileOutputStream(
                        "гадство_здесь/"+ar[ar.length-1]).getChannel() ) {
            out.transferFrom(in, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
        return "гадство_здесь\\"+ar[ar.length-1];
    }
}
