package seleniumnewtest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class VerifyErrorMsg {

	public static void main(String[] args) throws InterruptedException {
		//Step1: navigate to APP
		WebDriver driver=new FirefoxDriver();
		driver.navigate().to("https://gmail.com");
		//Step2: login to APP with invalid Email
		driver.findElement(By.id("identifierId")).sendKeys("sapaleesing");
		driver.findElement(By.xpath("//span[text()='Next']")).click();
		Thread.sleep(2000);
		//Verify the error msg.
		String expMsg="Couldn't find your Google Account";
		String actData=driver.findElement(By.xpath("//div[@class='GQ8Pzc']")).getText();
		System.out.println(actData);
		if(expMsg.equals(actData)) {
			System.out.println("Error Msg is verified=PASS");
		}else {
			System.out.println("Error Msg is not verified==Fail");
		}
		//Close the browser
		driver.close();

	}

}
