package shikimori.threads;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import shikimori.Writing;

public class ParentThread {

    protected WebDriver driver;
    protected WebElement workTo;
    protected Writing writing;

    public ParentThread(WebDriver driver, WebElement workTo, Writing writing)
    {
        this.writing = writing;
        this.workTo = workTo;
        this.driver = driver;
    }
}
