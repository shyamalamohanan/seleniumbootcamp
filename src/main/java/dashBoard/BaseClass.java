package dashBoard;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public WebDriver driver;
	public String sheetName;

	@Parameters({"browser","url","login","password"})
	@BeforeMethod
	public void launchApplication(String browserName,String applicationUrl,String loginName, String loginPwd ) {

		if(browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-notifications");
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
		}
		else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			EdgeOptions options = new EdgeOptions();
			options.addArguments("disable-notifications");
			options.addArguments("start-maximized");
			driver = new EdgeDriver(options);
		}

		driver.get(applicationUrl);
		WebElement eleUserName = driver.findElement(By.xpath("//input[@id='username']"));
		eleUserName.sendKeys(loginName);
		WebElement eleUserPassword = driver.findElement(By.xpath("//input[@id='password']"));
		eleUserPassword.sendKeys(loginPwd);
		WebElement eleLoginButton = driver.findElement(By.xpath("//input[@id='Login']"));	
		eleLoginButton.click();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void closeApplication() throws InterruptedException {
		Thread.sleep(2000);
		driver.close();
	}

	@DataProvider(name = "fetchData")
	public String[][] sendData() throws IOException {
		return ReadExcel.readData(sheetName);
	}
}
