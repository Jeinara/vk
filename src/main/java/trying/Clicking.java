package trying;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Света on 05.03.2018.
 */
public class Clicking {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("http://otakumole.ch");
        WebElement element = driver.findElement(By.name("username"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(element));
        element.click();
        element.sendKeys("jeinara");
        driver.quit();
    }
}
