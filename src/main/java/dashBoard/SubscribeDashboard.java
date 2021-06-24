package dashBoard;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SubscribeDashboard extends BaseClass {
	@BeforeTest
	public void setSheetName() {
		sheetName = "Sheet3" ;
		
	}
	
	@Test(dataProvider = "fetchData")
	public void verifySubscribeDasboard(String dashboardName,String selectApp, String menuOption) throws InterruptedException {
		String newDashboardCreated = dashboardName;
		JavascriptExecutor js = (JavascriptExecutor)driver;
		WebDriverWait wait = new WebDriverWait(driver,60);

		WebElement eleToggleButton = driver.findElement(By.xpath("//div[@class='slds-icon-waffle']"));
		eleToggleButton.click();
		WebElement eleViewAll = driver.findElement(By.xpath("//button[text()='View All']"));
		eleViewAll.click();
		WebElement eleSearchApps = driver.findElement(By.xpath("//input[@placeholder='Search apps or items...']"));
		eleSearchApps.sendKeys(selectApp);
		WebElement eleDashboardsLink = driver.findElement(By.xpath("//p[@class='slds-truncate']"));
		eleDashboardsLink.click();

		WebElement eleDashboardsTab = driver.findElement(By.xpath("//span[@class='slds-truncate'][text()='Dashboards']"));
		js.executeScript("arguments[0].click();", eleDashboardsTab);
		WebElement eleSearchDasboard = driver.findElement(By.xpath("//input[@placeholder='Search recent dashboards...']"));
		eleSearchDasboard.sendKeys(newDashboardCreated);

		Thread.sleep(4000);
		WebElement eleDashboardDropDown = driver.findElement(By.xpath("//table[@role='grid']//tbody//tr//child::span[text()='Show actions']"));
		js.executeScript("arguments[0].click();", eleDashboardDropDown);
		List<WebElement> eleDashboradMenu = driver.findElements(By.xpath("//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate']"));
		String expectedOption = menuOption;
		for(int i = 1;i<=eleDashboradMenu.size();i++)
		{
			String currentOption = driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate'])"+"["+i+"]")).getText();
			if(currentOption.equals(expectedOption))
			{
				driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//child::span[@class='slds-truncate'])"+"["+i+"]")).click();
				break;
			}
		}
		WebElement eleFrequency = driver.findElement(By.xpath("//span[text()='Daily']"));
		eleFrequency.click();
		WebElement eleFrequencySave = driver.findElement(By.xpath("//button[@title='Save']"));
		js.executeScript("arguments[0].click();", eleFrequencySave);
		WebElement eleSubscribeBanner = driver.findElement(By.xpath("//span[@data-aura-class='forceActionsText']"));
		String actualSubscribeBanner = wait.until(ExpectedConditions.visibilityOf(eleSubscribeBanner)).getText();
		String expectedSubscribeBanner = "You started a dashboard subscription.";
		
		Assert.assertEquals(actualSubscribeBanner, expectedSubscribeBanner, "The subcription was unsuccessful");
		
	}

}
