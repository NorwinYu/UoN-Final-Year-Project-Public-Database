package annotation;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DifferentAnnotation 
{
  @BeforeSuite 
  public void browser()
  {
	  System.out.println("open browser");  
  }
  @BeforeTest
  public void launch()
  {
	  System.out.println("double on browser");
  }
  @BeforeClass
  public void onbrowser()
  {
	  System.out.println("click on broswer");
	 
  }
  @BeforeMethod
  public void inbrowser()
  {
	  System.out.println("strike on image");
  }
  @Test
  public void title()
  {
	  System.out.println("playing in game");
  }
  @Test
  public void tortise()
  {
	  System.out.println("slow walk");
  }
  @AfterMethod
  public void outbrowser()
  {
	  System.out.println("strike on image2");
  }
  @AfterClass
  public void allbrowser()
  {
	  System.out.println("click on title");
	  
  }
  @AfterTest
  public void lunch()
  {
	  System.out.println("taking in breakfast");
  }
  @AfterSuite
  public void ganu()
  {
	  System.out.println("lllllllllll");
  }
}

 // All Annotation of TestNg are used in program but @BeforeMethod @Test @AfterMethod are Executed a pair.
 // Here Two @Test Annotation are Executed  Two pairs........
