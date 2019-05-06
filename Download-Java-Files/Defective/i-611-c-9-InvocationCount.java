package annotation;

import org.testng.annotations.Test;

public class InvocationCount
{
 @Test(invocationCount=3)
 public void add()
 {
	 int a=10;
	 int b=20;
	 int c=a+b;
	 System.out.println(c);
 }
}

// InvocationCount is used along with the @Test ,It repeats number of times.. keyword=invocationCount.
// it also executed by the .xml way