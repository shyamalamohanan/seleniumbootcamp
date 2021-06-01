package opprtunityPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;


public class AddProduct {

	public static void main(String[] args) throws InterruptedException {
		//launch the browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(option);;

		//Login to application
		driver.get("https://login.salesforce.com/");
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cypress@testleaf.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Selbootcamp@123");
		driver.findElement(By.xpath("//input[@id='Login']")).click();

		// select the toggle menu & click on 'view all'option and search for opportunities
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		driver.findElement(By.xpath("//input[@type='search'][@class='slds-input']")).sendKeys("opportunities");
		driver.findElement(By.xpath("//mark[text()='Opportunities']")).click();

		//select the opportunity SRMSteels1062021
		driver.findElement(By.xpath("//input[@name='Opportunity-search-input']")).sendKeys("SRMSteels1062021");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebElement eleOpportunity = driver.findElement(By.xpath("//a[@title='SRMSteels1062021']"));
		js.executeScript("arguments[0].click();", eleOpportunity);

		//scroll down select contact roles & edit contact roles
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,200)");
		Thread.sleep(2000);
		WebElement eleContactRoles = driver.findElement(By.xpath("(//div[@data-aura-class='forceDeferredDropDownAction']//a[@role='button'])[3]"));
		js.executeScript("arguments[0].click();", eleContactRoles);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@role='menuitem'][@title='Edit Contact Roles']")).click();

		//Select the first contact & set the role Business User
		driver.findElement(By.xpath("(//table[@role='grid'])[2]//tbody//tr[1]//th")).click();
		driver.findElement(By.xpath("(//li[@role='presentation']//a[@role='option'])[1]")).click();
		driver.findElement(By.xpath("((//table[@role='grid'])[2]//tbody//tr[1]//td[@role='gridcell'])[2]")).click();	
		driver.findElement(By.xpath("//div[@class='uiPopupTrigger']//a[@class='select']")).click();
		List<WebElement> eleRole = driver.findElements(By.xpath("//li[@role='presentation']//a[@role='menuitemradio']"));
		String expectedRole = "Business User"; 
		for(int i = 1; i<=eleRole.size();i++) { 
			String actualRole =driver.findElement(By.xpath("(//li[@role='presentation']//a[@role='menuitemradio'])"+"["+i+"]")).getText(); 
			if(actualRole.contains(expectedRole)) { 
				driver.findElement(By.xpath("(//li[@role='presentation']//a[@role='menuitemradio'])"+"["+i+"]")).click();
				System.out.println("the role is selected "); 
				break;
			}
		}

		//Click on save 
		driver.findElement(By.xpath("(//span[text()='Save'])[2]")).click();

		//close the driver
		Thread.sleep(3000);
		driver.close();
	}

}
