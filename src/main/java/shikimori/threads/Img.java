package shikimori.threads;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import shikimori.Writing;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

public class Img extends ParentThread implements Runnable {

    public Img(WebDriver driver, WebElement workTo, Writing writing) {
        super(driver, workTo, writing);
    }

    @Override
    public void run() {
        List<WebElement> list = workTo.findElements(By.xpath(".//*[contains(@class,\"c-column\")]"));
        for (WebElement element: list) {
            String id = element.getAttribute("id");
            String url = element.findElement(By.xpath(".//img")).getAttribute("src");
            writing.addPicture(id,save(url));
        }
    }

    private String save(String url) {
        String[] ar = url.split("/");
        try(
                ReadableByteChannel in=Channels.newChannel(
                        new URL(url).openStream());
                FileChannel out=new FileOutputStream(
                        "ШикиОбложки/"+ar[ar.length-1].split("\\?")[0]).getChannel() ) {
            out.transferFrom(in, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
            return "null";
        }
        return "ШикиОбложки\\"+ar[ar.length-1].split("\\?")[0];
    }
}
