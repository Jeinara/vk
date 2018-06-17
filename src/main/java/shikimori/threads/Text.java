package shikimori.threads;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import shikimori.Writing;

import java.util.List;

public class Text extends ParentThread implements Runnable {

    public Text(WebDriver driver, WebElement workTo, Writing writing) {
        super(driver, workTo, writing);
    }

    @Override
    public void run() {
        List<WebElement> list = workTo.findElements(By.xpath(".//*[contains(@class,\"c-column\")]"));
        for (WebElement element: list) {
            String name = element.findElement(By.xpath(".//span[@class=\"name-ru\"]")).getAttribute("data-text");
            String id = element.getAttribute("id");
            List<WebElement> timeAndEp = element.findElements(By.xpath(".//span[@class=\"misc\"]/span"));
            String time = timeAndEp.get(0).getText();
            String ep = timeAndEp.get(1).getText();
            writing.addText(id,time,name,ep);
        }
    }
}
