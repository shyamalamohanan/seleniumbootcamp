package opprtunityPage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SrmSteelsOpportunity {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {

		//launch the browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		WebDriver driver = new ChromeDriver(option);

		//Login to application
		driver.get("https://login.salesforce.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("cypress@testleaf.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("Selbootcamp@123");
		driver.findElement(By.xpath("//input[@id='Login']")).click();

		//select the toggle menu & click on 'view all'option
		driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();

		//search for opportunities & click the link. click the option new and keyin "SRMSteels1062021"
		driver.findElement(By.xpath("//input[@type='search'][@class='slds-input']")).sendKeys("opportunities");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//mark[text()='Opportunities']")).click();
		driver.findElement(By.xpath("(//span[@class='slds-checkbox--faux'])[1]")).click();
		driver.findElement(By.xpath("(//div[@title='New'])")).click();
		driver.findElement(By.xpath("//input[@name='Name']")).sendKeys("SRMSteels1062021");

		//scroll to view the option Type. click & select the type as New Customer
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement elementType = driver.findElement(By.xpath("(//label[text()='Type'])"));
		js.executeScript("arguments[0].scrollIntoView();", elementType);
		WebElement eleType = driver.findElement(By.xpath("(//input[@type='text'])[9]"));
		eleType.click();
		List<WebElement> typeOptions = driver.findElements(By.xpath("(//div[@role='listbox'])[4]//lightning-base-combobox-item//child::span[@class='slds-truncate']"));
		for(int i = 0; i<=typeOptions.size()-1; i++) {
			if (typeOptions.get(i).getText().contains("New Customer")) {
				typeOptions.get(i).click();
				break;
			}			
		}

		//click & Select the Leadsource as Partner Referral
		WebElement eleLeadSource = driver.findElement(By.xpath("(//input[@type='text'])[11]"));
		eleLeadSource.click();
		List<WebElement> leadsourceOptions = driver.findElements(By.xpath("(//div[@role='listbox'])[5]//lightning-base-combobox-item//child::span[@class='slds-truncate']"));
		for(int i = 0; i<=leadsourceOptions.size()-1; i++) {
			if (leadsourceOptions.get(i).getText().contains("Partner Referral")) {
				leadsourceOptions.get(i).click();
				break;
			}			
		}

		//scroll to view the amount field & enter amount as 75000
		WebElement elementAmount = driver.findElement(By.xpath("//label[text()='Amount']"));
		js.executeScript("arguments[0].scrollIntoView();", elementAmount);
		driver.findElement(By.xpath("//input[@name='Amount']")).sendKeys("75000");

		//generate the close date & send it to close date field
		String closeMonth,closeYear;
		Date date ;
		Format formatterYear,formatterMonth;
		Calendar calendar = Calendar.getInstance();
		Date d = new Date();
		d.setDate(20);
		calendar.add(Calendar.MONTH, 1);
		date = calendar.getTime();
		formatterYear = new SimpleDateFormat("yyyy");
		formatterMonth = new SimpleDateFormat("M");
		closeYear = formatterYear.format(date);
		closeMonth = formatterMonth.format(date);
		String CloseDate = closeMonth+ "/"+d.getDate()+"/"+ closeYear;
		driver.findElement(By.xpath("//input[@name='CloseDate']")).sendKeys(CloseDate);

		//click & select the stage as Needs Analysis
		WebElement eleStage = driver.findElement(By.xpath("(//input[@type='text'])[8]"));
		eleStage.click();
		List<WebElement> stage = driver.findElements(By.xpath("(//div[@role='listbox'])[3]//lightning-base-combobox-item//child::span[@class='slds-truncate']"));
		for(int i =0; i<=stage.size()-1;i++) {
			if (stage.get(i).getText().contains("Needs Analysis"))
			{
				stage.get(i).click();
				break;
			}
		}

		//select the primary campaign & click on the save button 
		driver.findElement(By.xpath("(//input[@type='text'])[12]")).click();
		WebElement elePCS = driver.findElement(By.xpath("(//div[@role='listbox'])[6]//lightning-base-combobox-item//child::span[@class='slds-truncate']"));
		elePCS.click();
		driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();

		//Verify if the opportunity is created
		WebElement eleOpportunityCreated= driver.findElement(By.xpath("(//lightning-formatted-text)[1]"));
		String oppoprtunityCreated = eleOpportunityCreated.getText();
		String creatdOpporunityName = "SRMSteels1062021";
		if(oppoprtunityCreated.equals(creatdOpporunityName))
		{
			System.out.println("The Opportunity: "+"'"+eleOpportunityCreated.getText()+"'"+" is created successfully");
		}
		else 
		{
			System.out.println("the creation failed");
		}

		//close the driver
		Thread.sleep(3000);
		driver.close();
	}

}
