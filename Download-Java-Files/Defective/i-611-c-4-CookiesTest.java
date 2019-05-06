 import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;

public class CookiesTest 
{

	public static void main(String[] args)
	{
	System.setProperty("Webdriver.chrome.driver",".Praveen/chromedriver.exe");
	WebDriver driver=new ChromeDriver();
	driver.get("http://www.facebook.com");
	// Get the Cookies
	 Set<Cookie> l=driver.manage().getCookies();
	 for(Cookie c1:l)
	 {
		 System.out.println(c1);
		
	 }
	 System.out.println("---------------------------------------");
	 
	 // Add Cookies
	Cookie c2=new Cookie("praveen","0987654321");
	 driver.manage().addCookie(c2);
	 Set<Cookie> l2=driver.manage().getCookies();
		for(Cookie c3:l2)
		{
			System.out.println(c3);
		}  
		System.out.println("---------------------------------------");
		
		//Delete Cookies
		driver.manage().deleteCookie(c2);
		Set<Cookie> l3=driver.manage().getCookies();
		for(Cookie c4:l3)
		{
			System.out.println(c4);
		}
		System.out.println("---------------------------------------");
		// Delete All Cookies
	  driver.manage().deleteAllCookies();
	
	  Set<Cookie> l4=driver.manage().getCookies();
	  for(Cookie c5:l4)
     	{
		System.out.println(c5);
	    }
	  System.out.println("---------------------------------------");
	}

    }
