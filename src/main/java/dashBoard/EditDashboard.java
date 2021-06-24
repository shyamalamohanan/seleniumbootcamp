package dashBoard;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EditDashboard extends BaseClass {
	@BeforeTest
	public void setSheetName() {
		sheetName = "Sheet2" ;
		
	}
	
	@Test(dataProvider = "fetchData")
	public void verifyEditDashboardFeature(String dashboardName,String description,String selectApp, String menuOption) throws InterruptedException {	

		String newDashboardCreated = dashboardName;
		String expectedDescription = description;
		JavascriptExecutor js = (JavascriptExecutor)driver;

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

		Thread.sleep(5000);
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

		Thread.sleep(6000);
		WebElement eleFrameElement = driver.findElement(By.xpath("//iframe[@title='dashboard']"));
		driver.switchTo().frame(eleFrameElement);	
		WebElement eleEditIcon =driver.findElement(By.xpath("//button[@title='Edit Dashboard Properties']"));
		eleEditIcon.click();	
		WebElement eleDescription = driver.findElement(By.xpath("//input[@id='dashboardDescriptionInput']"));
		eleDescription.clear();
		eleDescription.sendKeys(expectedDescription);
		WebElement eleSubmit = driver.findElement(By.xpath("//button[@id='submitBtn']"));
		eleSubmit.click();
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		driver.switchTo().frame(eleFrameElement);	
		WebElement eleSaveButton = driver.findElement(By.xpath("//button[text()='Save']"));
		eleSaveButton.click();
		WebElement eleDoneButton = driver.findElement(By.xpath("//button[text()='Done']"));
		eleDoneButton.click();
		WebElement eleSaveOfDone = driver.findElement(By.xpath("//button[@id='modalBtn2']"));
		eleSaveOfDone.click();
		WebElement eleDashboardEdited = driver.findElement(By.xpath("//div[@class='info']//p"));
		String actualDescription = eleDashboardEdited.getText();
		System.out.println(actualDescription);
		Assert.assertEquals(actualDescription,expectedDescription,"The description do not macth");

	}
}
