package com.bms.demo;

import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.PointOption;

import static io.appium.java_client.touch.offset.PointOption.point;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;

public class ThroughCinema {
  
	private static DesiredCapabilities cap;
	private static AndroidDriver<MobileElement> driver;
	private AppiumDriverLocalService service;

	//AppiumServerJava1 AS=new AppiumServerJava1();

	@BeforeTest
	public void driverInitialization() 
	{
		
		cap = new DesiredCapabilities();
		cap.setCapability("deviceName", "My Phone");
		cap.setCapability("udid", "ZY3227KDDD"); //Give Device ID of your mobile phone//ZY3227KDDD//0123456789ABCDEF
		cap.setCapability("platformName", "Android");
		cap.setCapability("platformVersion", "8.1");
		cap.setCapability("appPackage", "com.bt.bms");
		cap.setCapability("appActivity", "com.movie.bms.splash.views.activities.SplashScreenActivity");
		cap.setCapability("automationName", "uiautomator2");
		cap.setCapability("noReset", true);
		
		
//		service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
//				.withIPAddress("0.0.0.0")
//				.usingAnyFreePort());
		
		
		service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.withIPAddress("0.0.0.0")
				.usingAnyFreePort()
				.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
				.withAppiumJS(new File("C:\\Program Files (x86)\\Appium\\resources\\app\\node_modules\\appium\\build\\lib\\main.js")));

		driver = new AndroidDriver<MobileElement>(service, cap);
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
	
	}
  @Test
  public void test01LandingPage() 
  {
	  try
	  {
		  new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Select App Environment\")"))).isDisplayed();
		  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"PROD\")")).click();
	  }
	  catch(TimeoutException te) {}
	  
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Movies\")"))).isDisplayed();

  }
  
  @Test
  public void test02MoviesPage()
  {
	  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Movies\")")).click();
	  
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.bt.bms:id/now_showing_cinema_text"))).isDisplayed();
  }
  
  @Test
  public void test03SelectCinemaPage()
  {
	  driver.findElement(MobileBy.id("com.bt.bms:id/now_showing_cinema_text")).click();
	  
	  
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Search by cinema or area\")"))).isDisplayed();
	  
  }
  
  @Test
  public void test04SearchCinemaPage()
  {
	  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Search by cinema or area\")")).click();
	  
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.className("android.widget.EditText"))).sendKeys("Lodha Xperia");
	  new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"PVR: Lodha Xperia\")"))).click();
	  
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Search by movie name\")"))).isDisplayed();

  }
  
  @Test
  public void test05SelectShowPage()
  {
	  //xpath to select next day
	  driver.findElement(MobileBy.xpath("//android.widget.HorizontalScrollView[@resource-id='com.bt.bms:id/cinema_show_time_tab_layout']/descendant::android.widget.RelativeLayout[2]")).click();		
	  
	  //xpath to select any available show
	  new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"0 AM\")"))).click();
	  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Accept\")")).click();
	  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Proceed\")")).click();

	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.id("com.bt.bms:id/tv_no_tickets"))).isDisplayed();

  }
  
  @Test
  public void test06SelectSeatPage() throws InterruptedException
  {
	  new TouchAction(driver).tap(point(414, 931)).perform();
	  Thread.sleep(1000);
	  new TouchAction(driver).tap(point(502, 1211)).perform();

	  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Pay \")")).click();
	  
	  try
	  {
		  //if Grab a bite found, click on skip
		  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"Grab a Bite\")"))).isDisplayed();
		  driver.findElement(MobileBy.AndroidUIAutomator("new UiSelector().text(\"SKIP\")")).click();
	  }
	  catch(TimeoutException te) {}
	 
	  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AndroidUIAutomator("new UiSelector().text(\"Review Booking\")"))).isDisplayed();

	  System.out.println("End");
	  Thread.sleep(5000);

  }
  
  @AfterTest
  public void closeDriver() 
  {
	  driver.closeApp();
	  service.stop();
  }

}
