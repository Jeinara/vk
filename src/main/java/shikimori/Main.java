package shikimori;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import shikimori.record.Block;
import shikimori.threads.Desc;
import shikimori.threads.Img;
import shikimori.threads.Text;
import shikimori.ui.UI;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars");;
        WebDriver driver = new ChromeDriver(options);
        WebDriver driver1 = new ChromeDriver(options);
        Main.driverStatement(driver);
        Main.driverStatement(driver1);

        ConcurrentMap<String,Block> blocks = new ConcurrentHashMap<>();

        List<WebElement> list = driver.findElements(By.xpath(".//*[@class=\"block m0\"]"));
        List<WebElement> list1 = driver1.findElements(By.xpath(".//*[@class=\"block m0\"]"));
        for (int i = 0; i< list.size(); i++) {
            Writing writing = new Writing();
            Desc desc = new Desc(driver1, list1.get(i), writing);
            Thread des = new Thread(desc,"desc");
            Thread text = new Thread(new Text(driver,list.get(i),writing));
            Thread img = new Thread(new Img(driver,list.get(i),writing));
            des.start();
            text.start();
            img.start();
            try {
                des.join();
                text.join();
                img.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Да, это идентичный кусок кода, отвали, Идея");
            }
            Block bl = new Block(list.get(i).findElement(By.xpath(".//*[contains(@class,\"headline m10\")]"))
                    .getText().split("\n")[0],writing.getIdToCover());
            blocks.put(Integer.toString(i),bl);
        }
        try(RandomAccessFile file = new RandomAccessFile("ШикиФайл.json","rw");
            FileChannel fileChannel = file.getChannel();
            FileLock lock = fileChannel.lock()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(blocks);
            byte[] inputBytes = json.getBytes();
            file.write(inputBytes);
            lock.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                UI.main(args,blocks);
            }
        }).start();
    }

    private static void driverStatement(WebDriver driver){
        driver.get("https://shikimori.org/ongoings");
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        new WebDriverWait(driver, 10).until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver d) {return d.findElement(By.xpath(".//*[@class=\"subheadline m3\"]"));}
        });
    }
}
