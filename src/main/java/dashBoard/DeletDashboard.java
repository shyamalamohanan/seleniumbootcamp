package dashBoard;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeletDashboard extends BaseClass{

	@BeforeTest
	public void setSheetName() {
		sheetName = "Sheet4" ;
		
	}
	@Test(dataProvider = "fetchData")
	public void verifyDeleteDashboard(String dashboardName,String selectApp, String menuOption) throws InterruptedException {
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
		List<WebElement> eleDashboradMenu = driver.findElements(By.xpath("//lightning-menu-item[@role='presentation']//span[@class='slds-truncate']"));
		String expectedOption = menuOption;
		for(int j = 1; j<=eleDashboradMenu.size();j++)
		{
			String currentOption = driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//span[@class='slds-truncate'])"+"["+j+"]")).getText();
			if(currentOption.equals(expectedOption)) 
			{
				driver.findElement(By.xpath("(//lightning-menu-item[@role='presentation']//span[@class='slds-truncate'])"+"["+j+"]")).click();
				break;
			}
		}
		WebElement eleConfirmDelete = driver.findElement(By.xpath("//button[@title='Delete']"));
		eleConfirmDelete.click();
		
		WebElement eleDashboardMsg = driver.findElement(By.xpath("//span[@class='emptyMessageTitle']"));
		String actualMessage = eleDashboardMsg.getText();
		String expctedMessage = "No results found";
		Assert.assertEquals(actualMessage, expctedMessage, "The Dashboard was not deleted successfully");
		
		
	}

}
