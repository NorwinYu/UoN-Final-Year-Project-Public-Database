

 import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AlertEx1 {

	public static void main(String[] args) throws InterruptedException
	{
		System.setProperty("Webdriver.chrome.driver","D://Automation//chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://apsrtconline.in/oprs-web/");
		driver.findElement(By.xpath("//*[@id='searchBtn']")).click();
		
		// click on alert ok button
		Thread.sleep(4000);
		driver.switchTo().alert().accept();
		
		// Get Text on alert
		String str=driver.switchTo().alert().getText();
		System.out.println(str);
		
		// click on alert cancel button
		driver.switchTo().alert().dismiss();
		
	    driver.close();
	}
}
      // control move to alert pop up
     // driver.switchTO().alert();