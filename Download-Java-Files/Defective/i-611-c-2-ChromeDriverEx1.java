import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class ChromeDriverEx1
{
public static void main(String args[]) throws InterruptedException
{
	System.setProperty("Webdriver.chrome.driver", "D.//Automation//chromedriver.exe");
	WebDriver driver=new ChromeDriver();
	driver.manage().window().maximize();
	driver.get("https://www.facebook.com");
	
	Select day=new Select(driver.findElement(By.xpath("//select[@id='day']")));
	day.selectByIndex(9);

	Thread.sleep(3000);
	
    Select month=new Select(driver.findElement(By.xpath("//select[@id='month']")));
    month.selectByValue("6");
    
   
    List<WebElement> alloptions= month.getOptions();
      for(WebElement e:alloptions)
     {
	  System.out.println(e.getText()); 
     }
    
    List<WebElement> a=month.getAllSelectedOptions();
    for(WebElement e:a)
    {
    	System.out.println(e.getText());
    }
      
      Select year=new Select(driver.findElement(By.xpath("//select[@id='year']")));
      year.selectByVisibleText("1992");

//  driver.findElement(By.id("u_0_j")).sendKeys("Rushi");
//  driver.findElement(By.id("u_0_j")).clear(); 
//  driver.findElement(By.id("u_0_j")).sendKeys("Rishi");

 // driver.findElement(By.id("u_0_a")).click();
 // Thread.sleep(5000);


//Thread.sleep(3000);

	
	Thread.sleep(3000);
    driver.close();
}
}
