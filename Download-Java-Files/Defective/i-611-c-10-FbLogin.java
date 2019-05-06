package reusable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FbLogin 
    {
	public static void main(String[] args)
	{
		System.setProperty("Webdriver.chrome.driver", "D://Automation//chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("https://www.facebook.com");
		driver.findElement(By.xpath(WebLocator.Email)).sendKeys("gangulapraveenreddy@gmail.com");
		driver.findElement(By.xpath(WebLocator.password)).sendKeys("praveen414");
		driver.findElement(By.xpath(WebLocator.login)).click();
	}
    }

// Access the data from  another program of same package.
//call the element by using class name because using the static keyword.