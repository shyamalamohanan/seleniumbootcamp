package dashBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SortDashboard extends BaseClass{
	@BeforeTest
	public void setSheetName() {
		sheetName = "Sheet5";
	}

	@Test(dataProvider = "fetchData")
	public void verifySortDashboardFeature(String selectApp) throws InterruptedException {	

		String beforeSortValue;
		String afterSortValue;
		String name;
		List<String> befortSortList = new ArrayList<String>() ;
		List<String> manuallySortedData = new ArrayList<String>();
		List<String> afterSortList = new ArrayList<String>() ;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		WebElement eleToggleButton = driver.findElement(By.xpath("//div[@class='slds-icon-waffle']"));
		eleToggleButton.click();
		WebElement eleViewAll = driver.findElement(By.xpath("//button[text()='View All']"));
		eleViewAll.click();
		WebElement eleSearchApps = driver.findElement(By.xpath("//input[@placeholder='Search apps or items...']"));
		eleSearchApps.sendKeys(selectApp);
		WebElement eleDashboardsLink = driver.findElement(By.xpath("//p[@class='slds-truncate']"));
		eleDashboardsLink.click();
		WebElement eleDasboradTab = driver.findElement(By.xpath("//span[@class='slds-truncate'][text()='Dashboards']"));
		js.executeScript("arguments[0].click();", eleDasboradTab);

		Thread.sleep(4000);
		WebElement eleDasboradItems = driver.findElement(By.xpath("(//span[@data-aura-class='uiOutputText'])[2]"));
		String items=eleDasboradItems.getText();
		int count = Integer.parseInt(items.replaceAll("[^0-9]", ""));		

		WebElement eleDNameUnSorted = driver.findElement(By.xpath("(//table[@role='grid']//thead//tr//th//child::span[@aria-live='assertive'])[1]"));
		beforeSortValue = eleDNameUnSorted.getText();
		System.out.println("The value of sort in Dom before applying sort is - "+beforeSortValue);
		for(int i=1; i<=count;i++){
			name = driver.findElement(By.xpath("//table[@role='grid']//tbody//tr["+i+"]//th[1]")).getText();
			befortSortList.add(name);
			driver.findElement(By.xpath("//table[@role='grid']//tbody//tr["+i+"]//th[1]")).sendKeys(Keys.ARROW_DOWN);
		}
		System.out.println("The dashboard names as in the UI before applying sort : "+befortSortList);
		Collections.sort(befortSortList,String.CASE_INSENSITIVE_ORDER);
		for(String sort : befortSortList){
			manuallySortedData.add(sort);
		}
		System.out.println("The dashboard names sorted manually : "+manuallySortedData);


		WebElement eleSortArrow = driver.findElement(By.xpath("(//table[@role='grid']//thead//tr//th//child::lightning-primitive-icon[@class='slds-icon_container'])[1]"));
		js.executeScript("arguments[0].click();", eleSortArrow);
		WebElement eleDNameSorted = driver.findElement(By.xpath("(//table[@role='grid']//thead//tr//th//child::span[@aria-live='assertive'])[1]"));
		afterSortValue = eleDNameSorted.getText();
		System.out.println("The value of sort in Dom after applying sort is - "+afterSortValue);
		for(int i=1; i<=count;i++){
			name = driver.findElement(By.xpath("//table[@role='grid']//tbody//tr["+i+"]//th[1]")).getText();
			afterSortList.add(name);
			driver.findElement(By.xpath("//table[@role='grid']//tbody//tr["+i+"]//th[1]")).sendKeys(Keys.ARROW_DOWN);
		}
		System.out.println("The dashboard names as in the UI after applying sort : "+afterSortList);


		Assert.assertEquals(manuallySortedData, afterSortList, "There is a mismatch in the sorting order");

	}
}

