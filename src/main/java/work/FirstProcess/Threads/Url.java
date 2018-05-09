package work.FirstProcess.Threads;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import work.FirstProcess.Writing;

import java.util.*;
/**
 * Created by Света on 13.03.2018.
 */
public class Url extends ParentThread implements Runnable  {

    public Url(WebDriver driver, Writing writing)
    {
        super(driver,writing);
    }
    public void run() {
        System.out.println("Url");
        List<WebElement> webElementList = driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row \"]/div"));
        webElementList.addAll(driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row\"]/div")));
        webElementList = cutTheList(webElementList);
        for(WebElement e: webElementList)
        {
            List<String> hr = new ArrayList<>();
            List<WebElement> href = e.findElements(By.xpath(".//div[@class=\"wall_post_text\"]/a"));
            if(href.size() != 0)
            {
                for(WebElement webE: href)
                { hr.add(webE.getAttribute("href")); }

            }
            writing.addHref(e.getAttribute("id"),hr);
        }
    }
}