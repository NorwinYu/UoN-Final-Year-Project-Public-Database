

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class Cookies
{
public static void main(String args[])
{
  System.setProperty("Webdriver.ie.driver", "D://Automation//geckodriver.exe");
  WebDriver driver=new FirefoxDriver();
  driver.get("https://www.google.com");
  Set<Cookie> cookiees = driver.manage().getCookies();
  for( Cookie c:cookiees)
  {
  System.out.println(c);
  }

  driver.close();
  }
 }