package work.FirstProcess.Threads;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import work.FirstProcess.Writing;

import java.util.ArrayList;
import java.util.List;

public class ParentThread {

     WebDriver driver;
     Writing writing;

    public ParentThread(WebDriver driver, Writing writing)
    {
        this.driver = driver;
        this.writing = writing;
    }

    protected List<WebElement> cutTheList(List<WebElement> list){
        int count = list.size();
        List<WebElement> hell = new ArrayList<>();
        for(int i = 0; i<count; i++)
        {
            if(writing.existedId.contains(list.get(i).getAttribute("id"))){hell.add(list.get(i));}
        }
        list.removeAll(hell);
        return list;
    }

}
