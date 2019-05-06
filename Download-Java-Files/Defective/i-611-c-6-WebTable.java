import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebTable {

	public static void main(String[] args)
	{
		System.setProperty("WebDriver.driver.chrome", "D://Automation//chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		driver.get("file:///C:/Users/adisesu/Desktop/Student.html");
		String str1=driver.findElement(By.xpath("html/body/table/tbody/tr[3]/td[2]")).getText();
		System.out.println(str1);
		String str2=driver.findElement(By.xpath("html/body/table/tbody/tr[4]/td[3]")).getText();
		System.out.println(str2);
		String str3=driver.findElement(By.xpath("html/body/table/tbody/tr[3]/td[1]")).getText();	
        System.out.println(str3);
        for(int i=2;i<=5;i++)
        {
        	for(int j=1;j<=3;j++)
        	{
        		String str4=driver.findElement(By.xpath("html/body/table/tbody/tr["+i+"]/td["+j+"]")).getText();
        		System.out.print(str4);
        	}
        	System.out.println("           " );
        }
	}

}
