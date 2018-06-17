package shikimori.threads;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import shikimori.Writing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Desc extends ParentThread implements Runnable {

    public Desc(WebDriver driver, WebElement workTo, Writing writing)
    {
        super(driver,workTo,writing);
    }

    @Override
    public void run()
    {
        List<WebElement> list = workTo.findElements(By.xpath(".//*[@class=\"cover\"]"));
        int count = 0;
        while(count < list.size())
        {
            for(int i=0;i<5;i++){
                if(count == list.size()){break;}
                String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
                list.get(count).sendKeys(selectLinkOpeninNewTab);
                count++;
            }
            ArrayList<String> tabs2 = new ArrayList<> (driver.getWindowHandles());
            for(int i=1;i<tabs2.size();i++){
                driver.switchTo().window(tabs2.get(i));
                driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
                new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver d) {return d.findElement(By.xpath(".//*[@class=\"c-description\"]"));}
                });
                String id = driver.findElement(By.xpath(".//input[@name=\"user_rate[target_id]\"]")).getAttribute("value");
                List<WebElement> hide = driver.findElements(By.xpath(".//*[@class=\"expand\"]"));
                if(hide.size()!=0){hide.get(0).click();}
                new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
                    public WebElement apply(WebDriver d) {return d.findElement(By.xpath(".//*[@class=\"russian\"]/*[@class=\"text\"]"));}
                });
                WebElement hidedDescription = driver.findElement(By.xpath(".//*[@class=\"russian\"]/*[@class=\"text\"]"));
                String desc = hidedDescription.getText();
                writing.addDesc(id,desc);
            }
            for(int i=1;i<tabs2.size();i++){
                driver.switchTo().window(tabs2.get(i));
                driver.close();
            }
            driver.switchTo().window(tabs2.get(0));
        }
    }
}
