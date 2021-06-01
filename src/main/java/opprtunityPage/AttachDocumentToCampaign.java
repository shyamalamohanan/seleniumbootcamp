package opprtunityPage;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AttachDocumentToCampaign {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException, AWTException {

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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='slds-icon-waffle']")).click();
		driver.findElement(By.xpath("//button[text()='View All']")).click();
		driver.findElement(By.xpath("//p[text()='Sales']")).click();

		//click on campaigns tab & on the bootcamp link
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement eleCampaign = driver.findElement(By.xpath("//span[text()='Campaigns']"));
		js.executeScript("arguments[0].click();", eleCampaign);
		driver.findElement(By.xpath("//a[@title='BootCamp'][text()='BootCamp']")).click();

		//click on the upload button 
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement eleUpload = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@title='Upload Files']")));
		eleUpload.click();
		
		
		//invoke the Robot class to select the file from local and upload
		Robot rb = new Robot();
		// copying File path to Clipboard
		StringSelection up = new StringSelection("C:\\Users\\shyam\\Desktop\\TestUpload.pdf");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(up, null);
		// press Contol+V for pasting the file path
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		// release Contol+V after pasting the file path
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		// press and release Enter to complete the file upload
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		//click on the Done button to dismiss the alert
		Thread.sleep(5000);
		driver.findElement(By.xpath("//button[@type='button']//span[text()='Done']")).click();
		
		//To verify if the uploaded file name is a hyperlink
		String anchorTag = "a";
		String fileName = "TestUpload";
		String tagName = driver.findElement(By.xpath("//a[@title='TestUpload']")).getTagName();
		String linkText = driver.findElement(By.xpath("//a[@title='TestUpload']")).getText();
		if((tagName.equals(anchorTag))&&(linkText.contains(fileName)))
		{
			System.out.println("It is a hyperlink, since the WebElement begins with anchor tag : <"+tagName+">."
					+" The file uploaded is :"+linkText+" correct.");
		}

		//Click on the uploaded file & verify the upload is successful
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[@title='TestUpload']")).click();

		//Close the driver
		Thread.sleep(1000);
		driver.close();

	}

}
