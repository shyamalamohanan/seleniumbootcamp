package dashBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateDashboard extends BaseClass{
	
	@BeforeTest
	public void setSheetName() {
		sheetName = "Sheet1";
	}

	@Test(dataProvider = "fetchData")
	public void verifyCreateDashboardFeature(String dashboardName, String selectApp, String titleToVerify ) throws InterruptedException {	

		String newDashboardCreated = dashboardName;

		WebElement eleToggleButton = driver.findElement(By.xpath("//div[@class='slds-icon-waffle']"));
		eleToggleButton.click();
		WebElement eleViewAll = driver.findElement(By.xpath("//button[text()='View All']"));
		eleViewAll.click();
		WebElement eleSearchApps = driver.findElement(By.xpath("//input[@placeholder='Search apps or items...']"));
		eleSearchApps.sendKeys(selectApp);
		WebElement eleDashboardsLink = driver.findElement(By.xpath("//p[@class='slds-truncate']"));
		eleDashboardsLink.click();
		WebElement eleNewDasboardButton = driver.findElement(By.xpath("//div[@title='New Dashboard']"));
		eleNewDasboardButton.click();

		WebElement eleFrame = driver.findElement(By.xpath("//iframe[@title='dashboard']")); 
		driver.switchTo().frame(eleFrame);
		WebElement eleDasboardName = driver.findElement(By.xpath("//input[@id='dashboardNameInput']"));
		eleDasboardName.sendKeys(newDashboardCreated);
		WebElement eleCreateButton = driver.findElement(By.xpath("//button[@id='submitBtn']"));
		eleCreateButton.click();
		driver.switchTo().defaultContent();
		Thread.sleep(4000);
		String pageTitle = driver.getTitle();
		if( pageTitle.equals(titleToVerify)){
			WebElement elfr = driver.findElement(By.xpath("//iframe[@title='dashboard']"));
			driver.switchTo().frame(elfr);
			WebElement eleSaveButton = driver.findElement(By.xpath("//button[text()='Save']"));
			eleSaveButton.click();
			WebElement eleDoneButton = driver.findElement(By.xpath("//button[text()='Done']"));
			eleDoneButton.click();
			WebElement eleViewDashboardMessage = driver.findElement(By.xpath("(//div[@class=\"slds-media\"]//child::span)[3]"));
			String actualDasboardCreated = eleViewDashboardMessage.getText();
			System.out.println(actualDasboardCreated);

			Assert.assertEquals(actualDasboardCreated, newDashboardCreated, "The dashborad was not created successfully");
		}
		else {
			System.out.println("Not in correct page");
		}

	}
}
