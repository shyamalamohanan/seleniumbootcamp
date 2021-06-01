package opprtunityPage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CreateOpportunity {

	public static void main(String[] args) throws InterruptedException {
		//launch the browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(option);

		//login to application 
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cypress@testleaf.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Selbootcamp@123");
		driver.findElement(By.xpath("//input[@id='Login']")).click();

		//select the toggle menu & click on 'view all'option & click on sales link 
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		driver.findElement(By.xpath("//p[text()='Sales']")).click();

		//click on the opportunities tab & click on the "new" button 
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement opportunity = driver.findElement(By.xpath("//a[@title='Opportunities']"));
		js.executeScript("arguments[0].click();", opportunity);
		driver.findElement(By.xpath("//div[@title='New']")).click();

		//to calculate tomorrow date using date class and pass it in the date field
		String tomorrowDate;
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		date = calendar.getTime();
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		tomorrowDate = formatter.format(date);

		//set the close date as tomorrow date & click on save button 
		driver.findElement(By.xpath("//input[@name='CloseDate']")).sendKeys(tomorrowDate);
		driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();

		//verify if the alert message is displayed for Name & Stage fields
		boolean name = driver.findElement(By.xpath("//div[@role='alert'][text()='Complete this field.']")).isDisplayed();
		boolean stage = driver.findElement(By.xpath("//div[@aria-live='assertive'][text()='Complete this field.']")).isDisplayed();
		if((name == true)&(stage == true))
		{
			System.out.println("The alert message for 'Name & Stage' fields are Displayed");
		}
		else
		{
			System.out.println("The alert message for 'Name & Stage' fields are Not Displayed");
		}

		//close the browser
		driver.close();

	}

}
