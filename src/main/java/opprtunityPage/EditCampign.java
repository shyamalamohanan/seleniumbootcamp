package opprtunityPage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;


public class EditCampign {

	public static void main(String[] args) throws InterruptedException {
		//launch the browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(option);

		//Login to application
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cypress@testleaf.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Selbootcamp@123");
		driver.findElement(By.xpath("//input[@id='Login']")).click();

		// select the toggle menu & click on 'view all'option & click on sales link
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		driver.findElement(By.xpath("//p[text()='Sales']")).click();

		//click on campaigns tab
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement eleCampaign = driver.findElement(By.xpath("//span[text()='Campaigns']"));
		js.executeScript("arguments[0].click();", eleCampaign);

		//click on the bootcamp link and details tab
		driver.findElement(By.xpath("//a[@title='BootCamp'][text()='BootCamp']")).click();
		driver.findElement(By.xpath("//span[text()='Details']")).click();

		//scroll down the window to view the end date, expected revenue & budget cost fields
		js.executeScript("window.scrollBy(0,400)");

		//select the end date, revenue, budget and clear the fields 
		driver.findElement(By.xpath("//button[@title='Edit End Date']")).click();
		driver.findElement(By.xpath("(//input[@class=' input'])[3]")).clear();
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[1]")).sendKeys(Keys.CONTROL,"a");
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[1]")).sendKeys(Keys.DELETE);
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[2]")).sendKeys(Keys.CONTROL,"a");
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[2]")).sendKeys(Keys.DELETE);

		//compute (currentdate +4days) for setting up new end date 
		String newDate;
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 4);
		date=calendar.getTime();
		formatter = new SimpleDateFormat("M/d/yyyy");
		newDate = formatter.format(date);

		//enter end date & amount in Revenue & Budget fields. click on save
		driver.findElement(By.xpath("(//input[@class=' input'])[3]")).sendKeys(newDate);
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[1]")).sendKeys("1000000");
		driver.findElement(By.xpath("(//input[@data-aura-class='uiInputSmartNumber'])[2]")).sendKeys("100000");
		driver.findElement(By.xpath("//button[@title='Save']")).click();

		//verify if the changes have reflected
		Thread.sleep(10000);
		String revisedDate = driver.findElement(By.xpath("(//span[@class='uiOutputDate'])[9]")).getText();
		System.out.println(revisedDate);
		String revisedRevenue = driver.findElement(By.xpath("(//span[@class='forceOutputCurrency'])[5]")).getText();
		System.out.println(revisedRevenue);
		String setRevenue = "$1,000,000";
		String revisedBudget = driver.findElement(By.xpath("(//span[@class='forceOutputCurrency'])[7]")).getText();
		System.out.println(revisedBudget);
		String setBudget = "$100,000";

		if((revisedDate.equals(newDate))&&(revisedRevenue.equals(setRevenue))&&(revisedBudget.equals(setBudget)))
		{
			System.out.println("The new date is : "+revisedDate+ ". The new revenue is : "+revisedRevenue+". The new cost is : "+revisedBudget+".");
		}
		else {
			System.out.println("There is a issue in the data modified! Kindly check");
		}

		//Close the driver
		Thread.sleep(10000);
		driver.close();
	}

}
