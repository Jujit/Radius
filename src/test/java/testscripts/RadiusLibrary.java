package testscripts;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.NoAlertPresentException;

public class RadiusLibrary extends DriverScript {

    // Stores current window handle
    public static String currentWindowHandle;

    // Store method return result
    public static String methodReturnResult = null;

    // Site name
    public static String testSiteName = "Radius Agent";

    // User name
    public static String userName = null;

    // Expected page titles
    public static String MyProfilePageTitle = null;
    public static String LoginPageTitle = "Radius - Join the Fastest Growing Real Estate Network";
    public static String PageTitle = null;

    /*
     * .............. Name of the WebElements present on the WebPage
     * .................
     */

    public static String nameLoginName = "'Email' button on Radius login page";
    public static String namePassword = "'Password' button on Radius login page";
    public static String nameLogIn = "'LogIn' Button";
    public static String nameLogout = "'Logout' Button";
    public static String nameProfile = "'Profile' Button";

    /* .............. Locators for the test ................. */

    public static By LocatorLoginButton = By.xpath("//a[contains(text(),'Log In')]");
    public static By LocatorEmailField = By.xpath("//input[@placeholder='Email']");
    public static By LocatorPasswordField = By.xpath("//input[@placeholder='Password']");
    public static By LocatorLogInButton = By.xpath("//button[contains(text(),'Login')]");
    public static By LocatorLogoutButton = By.xpath("//a[@ng-click='vm.logout()']");
    //public static By LocatorProfile = By.xpath("//i[@class='fa fa-angle-down block text-center']");
    public static By LocatorProfile = By.xpath("//span[contains(text(),'Profile')]");

    // Create a browser instance and navigate to the test site
    public static String navigate() throws MalformedURLException, InterruptedException {

	System.out.println("Navigating to the test site - " + testSiteName + " ...");
	APPLICATION_LOGS.debug("Navigating to the test site - " + testSiteName + " ...");

	// Open a driver instance if not opened already

	try {

	    if (wbdv == null) {

		if (CONFIG.getProperty("is_remote").equals("true")) {

		    // Generate Remote address
		    String remote_address = "http://" + CONFIG.getProperty("remote_ip") + ":4444/wd/hub";
		    remote_url = new URL(remote_address);

		    if (CONFIG.getProperty("test_browser").equals("InternetExplorer"))
			dc = DesiredCapabilities.internetExplorer();

		    else if (CONFIG.getProperty("test_browser").equals("Firefox"))
			dc = DesiredCapabilities.firefox();

		    else if (CONFIG.getProperty("test_browser").equals("Chrome"))
			dc = DesiredCapabilities.chrome();

		    // Initiate Remote Webdriver instance
		    wbdv = new RemoteWebDriver(remote_url, dc);

		}

		else {

		    if (CONFIG.getProperty("test_browser").equals("InternetExplorer"))
			wbdv = new InternetExplorerDriver();

		    else if (CONFIG.getProperty("test_browser").equals("Firefox"))
			wbdv = new FirefoxDriver();

		    else if (CONFIG.getProperty("test_browser").equals("Chrome"))
			wbdv = new ChromeDriver();

		}

	    }

	}

	catch (Throwable initException) {

	    APPLICATION_LOGS.debug("Error came while initiating driver : " + initException.getMessage());
	    System.err.println("Error came while initiating driver : " + initException.getMessage());

	}

	// Initiate Event Firing Web Driver instance
	driver = new EventFiringWebDriver(wbdv);

	// Implicitly wait for 30 seconds for browser to open
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	// Delete all browser cookies
	driver.manage().deleteAllCookies();

	// Navigate to facebook application
	driver.navigate().to(CONFIG.getProperty("test_site_url"));

	// Maximize browser window
	driver.manage().window().maximize();

	// Verify Login page appears
	expectedTitle = LoginPageTitle;
	methodReturnResult = FunctionLibrary.assertTitle(expectedTitle);
	if (methodReturnResult.contains(failTest)) {

	    // Log result
	    APPLICATION_LOGS.debug("Not navigated to the test site - " + testSiteName);
	    System.err.println("Not navigated to the test site - " + testSiteName);
	    return methodReturnResult;

	}

	APPLICATION_LOGS.debug("Navigated to the test site - " + testSiteName);
	System.out.println("Navigated to the test site - " + testSiteName);

	return "Pass : Navigated to the test site - " + testSiteName;

    }

    // Retrive Login data to the Radius application
    public static String loginAndLogout(int Data_Row_No) throws InterruptedException, IOException {

	APPLICATION_LOGS.debug("Logging in to the test site - " + testSiteName);
	System.out.println("Logging in to the test site - " + testSiteName);

	String userName = null;
	String password = null;

	try {

	    userName = testData.getCellData("Login", "UserId_In", Data_Row_No);

	    password = testData.getCellData("Login", "Password_In", Data_Row_No);

	    APPLICATION_LOGS.debug("Successfully Retrieved data from Xls File :-  Username : " + userName
		    + " and Password : " + password);
	    System.out.println("Successfully Retrieved data from Xls File :-  Username : " + userName
		    + " and Password : " + password);

	}

	catch (Exception e) {

	    APPLICATION_LOGS.debug("Error while retrieving data from xls file" + e.getMessage());
	    System.out.println("Error while retrieving data from xls file" + userName);
	    return "Fail : Error while retrieving data from xls file";

	}

	FunctionLibrary.waitForElementToLoad(LocatorLoginButton);

	methodReturnResult = FunctionLibrary.clickAndWait(LocatorLoginButton, nameLogIn);
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	FunctionLibrary.waitForPageToLoad();

	FunctionLibrary.waitForElementToLoad(LocatorEmailField);
	FunctionLibrary.waitForElementToLoad(LocatorPasswordField);

	FunctionLibrary.clearAndInput(LocatorEmailField, "Email", userName);

	FunctionLibrary.clearAndInput(LocatorPasswordField, "Password", password);

	Thread.sleep(2000);

	methodReturnResult = FunctionLibrary.clickAndWait(LocatorLogInButton, nameLogIn);
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}
	
	FunctionLibrary.clickAndWait(LocatorLogInButton, nameLogIn);

	FunctionLibrary.waitForPageToLoad();

	FunctionLibrary.waitForElementToLoad(LocatorProfile);

	methodReturnResult = FunctionLibrary.clickAndWait(LocatorProfile, nameProfile);
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	}

	FunctionLibrary.waitForElementToLoad(LocatorLogoutButton);

	methodReturnResult = FunctionLibrary.clickAndWait(LocatorLogoutButton, nameLogout);
	if (methodReturnResult.contains(failTest)) {
	    return methodReturnResult;
	} 
	

	Alert alert = driver.switchTo().alert();

	alert.dismiss();

	APPLICATION_LOGS.debug("Successfully logged in & loggedout of the test site - " + testSiteName);
	System.out.println("Successfully logged in & loggedout of the test site - " + testSiteName);

	return "Pass : Successfully logged in & loggedout of the test site - " + testSiteName;

    }

}
