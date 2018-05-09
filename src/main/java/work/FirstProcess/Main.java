package work.FirstProcess;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Света on 04.03.2018.
 */
public class Main {
    //файл записи
    public static void main(String[] args) {
        String ChromeCash = "--user-data-dir=C:\\Users\\Svetlana\\Documents\\GitHub\\vk\\ChromeCash";
        ChromeOptions options = new ChromeOptions();
        options.addArguments(ChromeCash,"disable-infobars");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://vk.com/");
        //дождаться полной загрузки страницы!
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        new WebDriverWait(driver, 1000).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {return d.findElement(By.className("feed_row"));}
        });
        Writing writing = new Writing("гадим_сюда.json");
        new Thread(new MainThread(driver, writing), "mainThread").start();
        // изучть отдельно
        // Executors

    }
}
