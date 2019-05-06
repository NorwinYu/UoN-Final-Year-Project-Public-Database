
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class FreeCrm {

	public static void main(String[] args)
	{
	System.setProperty("Webdriver.ie.driver", "‪D:\\Automation\\InternetExplorer.exe");
	WebDriver driver=new InternetExplorerDriver();
	driver.get("https://www.freecrm.com");
	driver.manage().window().maximize();
	List<WebElement> link=driver.findElements(By.tagName("a"));
	System.out.println(link.size());
	List<WebElement> image=driver.findElements(By.tagName("img"));
	System.out.println(image.size());
	List<WebElement> inp=driver.findElements(By.tagName("input"));
	System.out.println(inp.size());
	driver.close();
	}

}
