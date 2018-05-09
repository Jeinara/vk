package work.FirstProcess.Threads;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import work.FirstProcess.Writing;

import java.util.*;

/**
 * Created by Света on 04.03.2018.
 */
public class Text extends ParentThread implements Runnable  {

    public Text(WebDriver driver, Writing writing)
    {
        super(driver,writing);
    }

    public void run() {
        System.out.println("Text");
        List<WebElement> webElementList = driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row \"]/div"));
        webElementList.addAll(driver.findElements(By.xpath(".//*[@id=\"feed_rows\"]/*[@class=\"feed_row\"]/div")));
        webElementList = cutTheList(webElementList);
        for(WebElement e: webElementList)
        {
            try {
                //wall_post_more
                List<WebElement> more = e.findElements(By.xpath(".//div[@class=\"wall_post_text\"]/a[@class=\"wall_post_more\"]"));
                if(!more.isEmpty()) {
                    writing.scroll(more.get(0),driver);
                    more.get(0).click();}
                more.clear();
                writing.addText(e.getAttribute("id"),e.findElement(By.xpath(".//div[@class=\"wall_post_text\"]")).getText());
            } catch (NoSuchElementException ex)
            {
                writing.addText(e.getAttribute("id"),"");
            } catch (ElementNotVisibleException ex)
            {
                System.out.println("ОШИБКА NOTVISIBLE"+e.getAttribute("id"));
            }

        }
    }
}
