package opprtunityPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DeleteAttachment {

	public static void main(String[] args) throws InterruptedException {
		//launch the browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(option);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		//Login to application
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cypress@testleaf.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Selbootcamp@123");
		driver.findElement(By.xpath("//input[@id='Login']")).click();

		// select the toggle menu & click on 'view all'option & click on sales link 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		driver.findElement(By.xpath("//p[text()='Sales']")).click();

		//click on campaigns tab & on the bootcamp link
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement eleCampaign = driver.findElement(By.xpath("//span[text()='Campaigns']"));
		js.executeScript("arguments[0].click();", eleCampaign);
		driver.findElement(By.xpath("//a[@title='BootCamp'][text()='BootCamp']")).click();

		//click on the view all link of attachments
		WebElement eleViewAll = driver.findElement(By.xpath("//span[text()='View All']//span[text()='Attachments']"));
		js.executeScript("arguments[0].scrollIntoView();",eleViewAll);
		js.executeScript("arguments[0].click();",eleViewAll);

		//click on the Lastmodified column if not sorted in descending already & click on the latest file 
		WebElement eleLastModified = driver.findElement(By.xpath("(//table[@data-aura-class='uiVirtualDataTable'])[2]//thead//tr//th[@title='Last Modified']"
				+ "//child::span[@aria-live='assertive']"));
		String sortedValue =eleLastModified.getText();
		System.out.println(sortedValue);
		WebElement eleLatestFile = driver.findElement(By.xpath("((//table[@data-aura-class='uiVirtualDataTable'])[2]//tbody//tr//th)[1]//child::span[@data-aura-class='uiOutputText']"));

		if (sortedValue.equals("Sorted Descending")){
			eleLatestFile.click();
		}
		else {
			eleLastModified.click();
			eleLatestFile.click();
		}

		//Click on the show more and click on the delete menu item
		WebElement eleShowMore = driver.findElement(By.xpath("//a[@title='Show More']"));
		eleShowMore.click();
		Thread.sleep(2000);
		WebElement eleDeleteOption = driver.findElement(By.xpath("(//li[@role='presentation'][@class='uiMenuItem'])[4]"));
		eleDeleteOption.click();
		//click delete on the confirmation prompt
		WebElement eleDeleteButton = driver.findElement(By.xpath("//button[@title='Cancel']//following::button[@title='Delete']"));
		wait.until(ExpectedConditions.visibilityOf(eleDeleteButton)).click();
		Thread.sleep(2000);
		//verify the green banner has file was deleted message
		WebElement eleDeleteMsg = driver.findElement(By.xpath("//span[@data-aura-class='forceActionsText']"));	
		String actualMsg= wait.until(ExpectedConditions.visibilityOf(eleDeleteMsg)).getText();
		System.out.println(actualMsg);
		String expectedMsg = "Record was deleted.";	
		if(actualMsg.equals(expectedMsg)){
			System.out.println("The attachement deleted successfully");
		}
		else{
			System.out.println("There is an issue with the delete!. Please verify.");
		}

		//close the driver
		Thread.sleep(2000);
		driver.close();
	}

}
