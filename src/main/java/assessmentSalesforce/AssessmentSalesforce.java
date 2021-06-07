package assessmentSalesforce;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AssessmentSalesforce {
	public static WebDriver driver;
	public static String salesForce = "https://login.salesforce.com/";
	public static String userName = "cypress@testleaf.com";
	public static String passWord = "Selbootcamp@123";
	public static String newDashboardName = "Shyamala_Workout";
	public static String description = "Testing";

	public static void main(String[] args) throws InterruptedException {
		//launch the Chrome browser and set the chromeoptions
		WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.addArguments("disable-notifications");
		option.addArguments("start-maximized");
		driver = new ChromeDriver(option);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		@SuppressWarnings("deprecation")
		WebDriverWait wait = new WebDriverWait(driver,60);
		Wait<WebDriver> waitFluent = new FluentWait<WebDriver>(driver);
		((FluentWait<WebDriver>) waitFluent).withTimeout(Duration.ofSeconds(30));
		((FluentWait<WebDriver>) waitFluent).pollingEvery(Duration.ofSeconds(5));
		((FluentWait<WebDriver>) waitFluent).ignoring(Exception.class);

		//login to application 
		driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		driver.get(salesForce);
		WebElement eleUserName = driver.findElement(By.xpath("//input[@id='username']"));
		eleUserName.sendKeys(userName);
		WebElement elePaswword = driver.findElement(By.xpath("//input[@id='password']"));
		elePaswword.sendKeys(passWord);
		WebElement eleLoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
		eleLoginButton.click();

		//select the toggle menu, click on 'view all'option & select the service is apps, select home in dropdown
		WebElement eleToggleButton = driver.findElement(By.xpath("//div[@class='slds-icon-waffle']"));
		eleToggleButton.click();
		WebElement eleViewAll = driver.findElement(By.xpath("//button[text()='View All']"));
		eleViewAll.click();
		WebElement eleService = driver.findElement(By.xpath("//p[@class='slds-truncate'][text()='Service']"));
		eleService.click();
		WebElement eleHome = driver.findElement(By.xpath("//span[text()='Home']"));
		js.executeScript("arguments[0].click();", eleHome);

		//capture the closed & open value convert to integer and compute total Amount 
		//If the total is less than 10000 then set the goal as 10000 else set the computed total 
		Thread.sleep(3000);
		WebElement eleClosed = driver.findElement(By.xpath("(//span[text()='Closed']//following::span[@data-aura-class='uiOutputText'])[1]"));	
		String closed = eleClosed.getText();
		Integer closeAmount = Integer.valueOf(closed.replaceAll("[^0-9]", ""));
		WebElement eleOpen = driver.findElement(By.xpath("(//span[text()='Open (>70%)']//following::span[@data-aura-class='uiOutputText'])[1]"));
		String open = eleOpen.getText();
		Integer openAmount = Integer.valueOf(open.replaceAll("[^0-9]", ""));
		Integer totalAmount =closeAmount+openAmount;
		int result;
		if (totalAmount <10000 ) {
			result = 10000;
		}else{
			result = totalAmount ;
		}

		//click on the edit goal and set the result value in Goal 
		WebElement eleEditGoal = driver.findElement(By.xpath("//span[@class='slds-assistive-text'][text()='Edit Goal']"));
		js.executeScript("arguments[0].click();", eleEditGoal);
		WebElement eleCurrencyCode = driver.findElement(By.xpath("//input[@aria-describedby='currencyCode']"));
		eleCurrencyCode.clear();
		eleCurrencyCode.sendKeys(String.valueOf(result));
		WebElement eleSaveButton = driver.findElement(By.xpath("//span[@dir='ltr'][text()='Save']"));
		eleSaveButton.click();

		//click on the Dashbord option & create new dashboard
		WebElement eleDashboards = driver.findElement(By.xpath("//a[@title='Dashboards']"));
		js.executeScript("arguments[0].click();", eleDashboards);
		WebElement eleNewDashboard = driver.findElement(By.xpath("//a[@title='New Dashboard']"));
		eleNewDashboard.click();
		WebElement eleNewDashboardPopUP = driver.findElement(By.xpath("//iframe[@title='dashboard']"));
		driver.switchTo().frame(eleNewDashboardPopUP);
		WebElement eleNewDashboardName = driver.findElement(By.xpath("//input[@id='dashboardNameInput']"));
		eleNewDashboardName.sendKeys(newDashboardName);
		WebElement eleNewDashboardDescription = driver.findElement(By.xpath("//input[@id='dashboardDescriptionInput']"));
		eleNewDashboardDescription.sendKeys(description);
		WebElement eleNewDashboardSubmit = driver.findElement(By.xpath("//button[@id='submitBtn']"));
		eleNewDashboardSubmit.click();

		//verify the newly created dashboard is displayed 
		waitFluent.until(ExpectedConditions.visibilityOf(eleDashboards));	
		js.executeScript("arguments[0].click();", eleDashboards);
		WebElement eleSearchDashboards = driver.findElement(By.xpath("//input[@placeholder='Search recent dashboards...']"));
		eleSearchDashboards.sendKeys(newDashboardName);
		WebElement eleHighlightDashboardName = driver.findElement(By.xpath("//span[@class='highlightSearchText']"));
		String actualDashboardName = eleHighlightDashboardName.getText();
		if(actualDashboardName.equals(newDashboardName)){
			System.out.println("The dashboard is created successfully! The newly created dashboard is : " +actualDashboardName+" .");
		}else{
			System.out.println("The dashboard was not created successfully.");
		}

		//click on dropdown and subscribe
		WebElement eleDashboardDropDown = driver.findElement(By.xpath("//table[@role='grid']//tbody//tr//child::span[text()='Show actions']"));
		js.executeScript("arguments[0].click();", eleDashboardDropDown);
		List<WebElement> eleDashboradMenu = driver.findElements(By.xpath("//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate']"));
		String expectedDOption = "Subscribe";
		for(int i = 1;i<=eleDashboradMenu.size();i++)
		{
			String currentDOption = driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate'])"+"["+i+"]")).getText();
			if(currentDOption.equals(expectedDOption))
			{
				driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate'])"+"["+i+"]")).click();
				break;
			}
		}

		//set frequency, time & save
		WebElement eleFrequency = driver.findElement(By.xpath("//span[text()='Daily']"));
		eleFrequency.click();
		WebElement eleTime = driver.findElement(By.xpath("//select[@id='time']"));
		Select select = new Select(eleTime);
		select.selectByValue("10");
		WebElement eleFrequencySave = driver.findElement(By.xpath("//button[@title='Save']"));
		js.executeScript("arguments[0].click();", eleFrequencySave);

		//verify the success banner for subscription
		WebElement eleSubscribeBanner = driver.findElement(By.xpath("//span[@data-aura-class='forceActionsText']"));
		String displayedSubscribeBanner = wait.until(ExpectedConditions.visibilityOf(eleSubscribeBanner)).getText();
		String expectedSubscribeBanner = "You started a dashboard subscription.";
		if(displayedSubscribeBanner.equals(expectedSubscribeBanner)) {
			System.out.println("The subscription was Successfull.'You started a dashboard subscription.' - banner is displayed.");
		}else {
			System.out.println("The subscription was UnSuccessfull. Expected banner is not displayed.");
		}
		WebElement eleBannerClose = driver.findElement(By.xpath("//button[@title='Close']"));
		eleBannerClose.click();

		//click on the private dashboard & verify if the newly created dashboard is displayed
		WebElement elePrivateDashboards = driver.findElement(By.xpath("//div[@id='navSection']//child::a[@title='Private Dashboards']"));
		elePrivateDashboards.click();
		WebElement eleSearchPDashboards = driver.findElement(By.xpath("//input[@placeholder='Search private dashboards...']"));
		eleSearchPDashboards.sendKeys(newDashboardName);
		WebElement eleHighlightPDashboardName = driver.findElement(By.xpath("//table//tbody//tr/th//child::span[@class='highlightSearchText']"));
		String actualPDashboardName = eleHighlightPDashboardName.getText();
		if(actualPDashboardName.equals(newDashboardName)) {
			System.out.println(actualPDashboardName+" : Dashboard is present in Private Dashboards.");
		}else {
			System.out.println("Dashboard is not present in Private Dashboards.");
		}

		//click on dropdown and select delete option and confirm the delete; the dashboard deleted banner is displayed
		WebElement elePDashboardDropDown = driver.findElement(By.xpath("//table[@role='grid']//tbody//tr//child::span[text()='Show actions']"));
		js.executeScript("arguments[0].click();", elePDashboardDropDown);
		List<WebElement> elePDashboradMenu = driver.findElements(By.xpath("//lightning-menu-item[@role='presentation']//span[@class='slds-truncate']"));
		String expectedPDOption = "Delete";
		for(int j = 1; j<=elePDashboradMenu.size();j++)
		{
			String currentPDOption = driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//span[@class='slds-truncate'])"+"["+j+"]")).getText();
			if(currentPDOption.equals(expectedPDOption)) 
			{
				driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//span[@class='slds-truncate'])"+"["+j+"]")).click();
				break;
			}
		}
		WebElement eleConfirmDelete = driver.findElement(By.xpath("//button[@title='Delete']"));
		eleConfirmDelete.click();

		//Verify if newly created dashboard is not visible in private dashboard	after delete
		WebElement elePDashboardMsg = driver.findElement(By.xpath("//span[@class='emptyMessageTitle']"));
		String actaulMessage = elePDashboardMsg.getText();
		String expctedMessage = "No results found";
		if(actaulMessage.equals(expctedMessage)) {
			System.out.println("Delete was Successful, "+actualPDashboardName+" is not available under Private Dashboard folder, TC Passed.");
		}else {
			System.out.println("Delete was UnSuccessful, "+actualPDashboardName+" is available under Private Dashboard folder, TC Failed.");
		}

		//close the driver
		Thread.sleep(3000);
		driver.close();
	}

}
