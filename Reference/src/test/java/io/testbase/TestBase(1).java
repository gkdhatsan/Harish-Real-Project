package io.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import org.testng.xml.XmlSuite.ParallelMode;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class TestBase {

	/**
	 * configuration of browsers all reusable methods reusable utilities property
	 * readers XML Readers
	 */
//	public static WebDriver driver;
//	public ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static Properties prop;
	public static FileInputStream fis;

//	public static ExtentTest test;
	public static ExtentReports extent;
	// private ThreadLocal<ExtentTest> extenttest;

	static {
		System.out.println("Extent Report Initialize");
		extent = new ExtentReports();
		System.out.println("User directory - " + System.getProperty("user.dir"));
//		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + System.getProperty("file.separator") +"testreport"+ System.getProperty("file.separator") + "report" + formatter.format(cal.getTime()) + ".html");
		ExtentSparkReporter spark = new ExtentSparkReporter(
				System.getProperty("user.dir") + System.getProperty("file.separator") + "testreport"
						+ System.getProperty("file.separator") + "report" + ".html");
		System.out.println("report path-" + System.getProperty("user.dir") + System.getProperty("file.separator")
				+ "testreport" + System.getProperty("file.separator") + "report" + ".html");
		extent.attachReporter(spark);
		// spark.config().setTheme(Theme.DARK);
		// spark.config().setDocumentTitle("Mousiki Automation");
		try {
			spark.loadXMLConfig("report_config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extent.attachReporter(spark);
	}

	/**
	 * @throws IOException property file initialization
	 */
	@BeforeSuite
	public void properties() throws IOException {
		prop = new Properties();
		fis = new FileInputStream(System.getProperty("user.dir") + System.getProperty("file.separator") + "src"
				+ System.getProperty("file.separator") + "test" + System.getProperty("file.separator") + "java"
				+ System.getProperty("file.separator") + "io" + System.getProperty("file.separator") + "api"
				+ System.getProperty("file.separator") + "config" + System.getProperty("file.separator")
				+ "config.properties");
		prop.load(fis);
		System.out.println("property file data:" + prop);

	}

	/*
	 * public void setdriver(WebDriver newdriver) { this.driver.set(newdriver); }
	 * 
	 * public WebDriver getDriver() { return this.driver.get(); }
	 */

	/**
	 * browser invoke based on browser name in property file
	 * 
	 * @throws IOException
	 */
	public void invoke() throws IOException {

		System.out.println("browser name=" + System.getProperty("browsername"));

		/*
		 * invokeBrowser(prop.getProperty("browsername")); try { hardwait(3000); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		if (System.getProperty("testphase").contains("Sanity")) {
			prop.setProperty("URL", prop.getProperty("PRODURL"));
		}

		BrowserFactory.getInstance().getDriver().get(prop.getProperty("URL"));
		BrowserFactory.getInstance().getDriver().manage().window().maximize();
		waitForLoad();
	}

	/**
	 * different browser initialize components
	 * 
	 * @param browser
	 */

	// @SuppressWarnings("deprecation")
	/*
	 * public void invokeBrowser(String browser) {
	 * if(prop.getProperty("OsName").contains("Win")) {
	 * if(browser.contains("chrome")) { WebDriverManager.chromedriver().setup();
	 * ChromeOptions options = new ChromeOptions();
	 * options.addArguments("--incognito"); DesiredCapabilities cap = new
	 * DesiredCapabilities().chrome(); cap.setCapability(ChromeOptions.CAPABILITY,
	 * options);
	 * 
	 * // driver = new ChromeDriver(cap); setdriver(new ChromeDriver(cap)); }else
	 * if(browser.contains("firefox")) { WebDriverManager.firefoxdriver().setup();
	 * FirefoxOptions fp = new FirefoxOptions(); String path = "";
	 * fp.setBinary(path); // driver = new FirefoxDriver(fp); setdriver(new
	 * FirefoxDriver(fp)); }else { //for other browsers }
	 * 
	 * //do implicit wait
	 * getDriver().manage().timeouts().implicitlyWait(Long.parseLong(prop.
	 * getProperty("Implicitwait")), TimeUnit.SECONDS);
	 * 
	 * }else if(prop.getProperty("OsName").contains("mac")) {
	 * if(browser.contains("safari")) {
	 * 
	 * } } }
	 */

	/**
	 * method to get element text from multiple elements
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param name
	 * @throws IOException
	 */
	public List<String> getmultipleelementtext(By ElementLocator, String name) throws IOException {
		List<WebElement> elementlists = BrowserFactory.getInstance().getDriver().findElements(ElementLocator);
		List<String> elementtextlist = new ArrayList<String>();

		for (WebElement ele : elementlists) {
			try {
//				new Actions(BrowserFactory.getInstance().getDriver()).moveToElement(ele).perform();
				focusElement(ele);
				elementtextlist.add(ele.getText());
			} catch (Exception e) {
			}
		}
		return elementtextlist;
	}

	/**
	 * drag and drop functionality
	 * 
	 * @param Source element
	 * @param Target element
	 * @throws IOException
	 */
	public void draganddrop(By sourceElement, By targetElement) throws IOException {
		Actions a = new Actions(BrowserFactory.getInstance().getDriver());
		a.dragAndDrop(BrowserFactory.getInstance().getDriver().findElement(sourceElement),
				BrowserFactory.getInstance().getDriver().findElement(targetElement)).build().perform();
//		a.dragAndDropBy(BrowserFactory.getInstance().getDriver().findElement(sourceElement), 250, 250);
		reportlog("Source-" + sourceElement.toString() + ", Target-" + targetElement.toString()
				+ ". Drag and drop successfully", "INFO");
	}

	/**
	 * method to click a webelement using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param name
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void click(By ElementLocator, String name) throws IOException {
		try {
			if (checkelementexists(10, ElementLocator)) {
				// WebElement ele =
				// BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
				waitForLoad();
				reportlog(name + " clicked successfully-1", "INFO");
			} else {
				// WebElement ele =
				// BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
				waitForLoad();
				reportlog(name + " clicked successfully-1", "INFO");
			}
		} catch (ElementNotInteractableException v) {
			try {
				windowscrollVisibleElement(ElementLocator);// windowscrolldown();
				if (checkelementexists(2, ElementLocator)) {
					// WebElement ele =
					// BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
					waitForLoad();
					reportlog(name + " clicked successfully-1", "INFO");
				} else {
					// WebElement ele =
					// BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
					waitForLoad();
					reportlog(name + " clicked successfully-1", "INFO");
				}
			} catch (Exception e) {
				try {
					reportlog("An exception occured while click-1 for element " + name + " Exeception:" + e, "INFO");
					windowscrollVisibleElement(ElementLocator);
					jsclick(ElementLocator, name);
				} catch (Exception f) {
					try {
						mouseclick(ElementLocator, name);
					} catch (Exception h) {
						reportlog("An exception occured while click for element " + name + " Exeception:" + f, "FAIL",
								"Click fail");
					}
				}
			}
		} catch (Exception e) {
			try {
				reportlog("An exception occured while click-1 for element " + name + " Exeception:" + e, "INFO");
				windowscrollVisibleElement(ElementLocator);
				jsclick(ElementLocator, name);
			} catch (Exception f) {
				try {
					mouseclick(ElementLocator, name);
				} catch (Exception h) {
					reportlog("An exception occured while click for element " + name + " Exeception:" + f, "FAIL",
							"Click fail");
				}
			}
		}
	}

	/**
	 * method to click a webelement using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param name
	 * @throws IOException
	 */
	public void jsclick(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				focusElement(BrowserFactory.getInstance().getDriver().findElement(ElementLocator));
				JavascriptExecutor executor = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
				executor.executeScript("arguments[0].click();",
						BrowserFactory.getInstance().getDriver().findElement(ElementLocator));
				waitForLoad();
				reportlog(name + " clicked successfully-jsclick", "INFO");
			} else {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				focusElement(BrowserFactory.getInstance().getDriver().findElement(ElementLocator));
				JavascriptExecutor executor = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
				executor.executeScript("arguments[0].click();",
						BrowserFactory.getInstance().getDriver().findElement(ElementLocator));
				waitForLoad();
				reportlog(name + " clicked successfully-jsclick", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while click for element " + name + " Exeception:" + e, "FAIL",
					"Click fail");
		}
	}

	public void mouseclick(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				new Actions(BrowserFactory.getInstance().getDriver())
						.moveToElement(BrowserFactory.getInstance().getDriver().findElement(ElementLocator)).click()
						.build().perform();
				waitForLoad();
				reportlog(name + " clicked successfully-mouseclick1", "INFO");
			} else {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				new Actions(BrowserFactory.getInstance().getDriver())
						.moveToElement(BrowserFactory.getInstance().getDriver().findElement(ElementLocator)).click()
						.build().perform();
				waitForLoad();
				reportlog(name + " clicked successfully-mouseclick2", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while click for element " + name + " Exeception:" + e, "FAIL",
					"Click fail");
		}
	}

	public void doubleclick(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				new Actions(BrowserFactory.getInstance().getDriver()).moveToElement(ele).doubleClick(ele).build()
						.perform();
				waitForLoad();
				reportlog(name + " clicked successfully-mouseclick1", "INFO");
			} else {
				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				new Actions(BrowserFactory.getInstance().getDriver()).moveToElement(ele).doubleClick().perform();
				waitForLoad();
				reportlog(name + " clicked successfully-mouseclick2", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while click for element " + name + " Exeception:" + e, "FAIL",
					"Click fail");
		}
	}

	/**
	 * method to click a webelement using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param name
	 * @throws IOException
	 */
	public void clickifavailable(By ElementLocator, String name) throws IOException {
		try {
			waitForLoad();

			if (checkelementexistsmillis(500, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
				waitForLoad();
				reportlog(name + " clicked successfully", "INFO");
			}
		} catch (Exception e) {
//			try {
//				jsclick(ElementLocator, name);
//			} catch (Exception f) {
//				try {
//					mouseclick(ElementLocator, name);
//					} catch (Exception h) {
//						// do nothing
//					}
//				}
//			}
		}
	}

	public void switchframe(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().switchTo().frame(ele);
				waitForLoad();
				reportlog(name + " switched successfully", "INFO");
			}
		} catch (Exception e) {
			try {
				jsclick(ElementLocator, name);
			} catch (Exception f) {
				try {
					mouseclick(ElementLocator, name);
				} catch (Exception h) {
					// do nothing
				}
			}
		}
	}

	public void switchMainFrame(String name) throws IOException {
		try {

			BrowserFactory.getInstance().getDriver().switchTo().defaultContent();
			waitForLoad();
			reportlog(name + " switched successfully", "INFO");
		} catch (Exception e) {

			// do nothing

		}
	}

	/**
	 * method to enter text in textbox using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param value
	 * @param name
	 * @throws IOException
	 */
	public void entertext(By ElementLocator, String value, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).clear();
				click(ElementLocator, name);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.CONTROL + "a");
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.DELETE);
				click(ElementLocator, name);
				waitForLoad();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value);
				waitForLoad();
				String text = BrowserFactory.getInstance().getDriver().findElement(ElementLocator)
						.getAttribute("value");
				reportlog(name + " field entered '" + text + "' successfully", "INFO");
			} else {
				reportlog("Test box not available in the application for timeout of 10 seconds", "FAIL",
						"Enter text fail");
			}
		} catch (ElementNotInteractableException e) {
			try {
				windowscrollVisibleElement(ElementLocator);// windowscrolldown();

				if (checkelementexists(10, ElementLocator)) {
//					WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).clear();
					hardwait(500);
					click(ElementLocator, name);
					hardwait(500);
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.CONTROL + "a");
					hardwait(500);
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.DELETE);
					waitForLoad();
					click(ElementLocator, name);
					hardwait(500);
					waitForLoad();
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value);
					waitForLoad();
					String text = BrowserFactory.getInstance().getDriver().findElement(ElementLocator)
							.getAttribute("value");
					reportlog(name + " field entered '" + text + "' successfully", "INFO");
				}
			} catch (Exception f) {
				reportlog("An exception occured while enter for element " + name + " Exeception:" + f, "FAIL",
						"Enter text fail");
			}
		} catch (Exception e) {
			reportlog("An exception occured while enter for element " + name + " Exeception:" + e, "FAIL",
					"Enter text fail");
		}
		keyboardentry(Keys.ESCAPE);

	}

	public String getelementattribute(By ElementLocator, String attributename) throws IOException {

		if (checkelementexists(10, ElementLocator)) {
			return BrowserFactory.getInstance().getDriver().findElement(ElementLocator).getAttribute(attributename);
		} else
			return "";
	}

	public String changedateformat(String inputdate, String dateformat) throws Throwable {
		SimpleDateFormat dt = new SimpleDateFormat("dd-mm-yyyy");
		Date date = dt.parse(inputdate);
		SimpleDateFormat dt1 = new SimpleDateFormat(dateformat);
		System.out.println("date format change:" + dt1.format(date));
		return dt1.format(date);
	}

	public void enternexttext(By ElementLocator, String value, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				waitForLoad();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value);
				waitForLoad();
				String text = BrowserFactory.getInstance().getDriver().findElement(ElementLocator)
						.getAttribute("value");
				reportlog(name + " field entered '" + text + "' successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while enter for element " + name + " Exeception:" + e, "FAIL",
					"Enter text fail");
		}

	}

	public void clearelement(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).clear();
				waitForLoad();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.BACK_SPACE);
				waitForLoad();
				reportlog(name + " field space  cleared/emptyed successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while space for element " + name + " Exeception:" + e, "FAIL",
					"Enter space fail");
		}

	}

	public void enterspace(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				// ele.clear();
//				ele.click();
				waitForLoad();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.SPACE);
				waitForLoad();
				reportlog(name + " field space  entered successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while space for element " + name + " Exeception:" + e, "FAIL",
					"Enter space fail");
		}

	}

	public void enterspace1(By ElementLocator, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				waitForLoad();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(Keys.SPACE);
				waitForLoad();
				reportlog(name + " field space  entered successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while space for element " + name + " Exeception:" + e, "FAIL",
					"Enter space fail");
		}

	}

	public String getelementtext(By ElementLocator, String name) throws IOException {
		try {
			if (checkelementexists(20, ElementLocator)) {
				try {
					new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(20))
							.until(ExpectedConditions.textMatches(ElementLocator, Pattern.compile("[a-zA-Z0-9 ]")));
				} catch (Exception f) {
					reportlog("get element text dynamic wait issue for element:" + name + " Exeception:" + f, "INFO",
							"get text fail-wait");
					return BrowserFactory.getInstance().getDriver().findElement(ElementLocator).getText();
				}
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				focusElement(BrowserFactory.getInstance().getDriver().findElement(ElementLocator));
				reportlog("(" + name + ") get text from element successfully", "INFO");
				return BrowserFactory.getInstance().getDriver().findElement(ElementLocator).getText();
				// click(driver, ElementLocator, name);

			} else {
				return "";
			}
		} catch (Exception e) {
			reportlog("An exception occured while get text for element " + name + " Exeception:" + e, "INFO",
					"get text fail");
			return "";
		}

	}

	public String getelementlinkaddress(By ElementLocator, String name) throws IOException {
		String elementtext = "";
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				elementtext = BrowserFactory.getInstance().getDriver().findElement(ElementLocator).getAttribute("href");
				reportlog(name + "get href from element successfully", "INFO");
			}
			return elementtext;
		} catch (Exception e) {
			reportlog("An exception occured while enter for element " + name + " Exeception:" + e, "FAIL",
					"Enter text fail");
			return elementtext;
		}

	}

	/**
	 * method to enter text in textbox using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param value
	 * @param name
	 * @throws IOException
	 */
	public void sendtext(By ElementLocator, String value, String name) throws IOException {
		try {
			waitforelementclickable(20, ElementLocator);
			if (checkelementexists(20, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				click(ElementLocator, name);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).clear();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value);
				waitForLoad();
				reportlog(name + "entered successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while enter for element " + name + " Exeception:" + e, "FAIL",
					"Enter text fail");
		}

	}

	public void sendInputValue(By ElementLocator, String value, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				click(ElementLocator, name);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).click();
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value);
				waitForLoad();
				reportlog(name + "entered successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while enter for element " + name + " Exeception:" + e, "FAIL",
					"Enter text fail");
		}

	}

	public void waitForLoad() {
		new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(60))
				.until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
						.executeScript("return document.readyState").equals("complete"));

		// initialization expression
		int i = 1;
		By circlewebele = By.xpath("//div[@class='gap-patch']/div[@class='circle']");
		By fadewebele = By.xpath("//div[@class='mau-fade-me']");
		while (i < 200) {
			if (BrowserFactory.getInstance().getDriver().findElements(circlewebele).isEmpty()
					&& BrowserFactory.getInstance().getDriver().findElements(fadewebele).isEmpty()) {
				if (!checkelementexistsmillis(250, circlewebele) && !checkelementexistsmillis(250, fadewebele)) {
					i += 300;
				}
			}

			// update expression
			i++;
		}

	}

	/**
	 * method to enter text in textbox using webdriver and element locator
	 * 
	 * @param driver
	 * @param ElementLocator
	 * @param value
	 * @param name
	 * @throws IOException
	 */
	public void selectlist(By ElementLocator, String value, String name) throws IOException {
		try {

			if (checkelementexists(10, ElementLocator)) {
//				WebElement ele = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);
				BrowserFactory.getInstance().getDriver().findElement(ElementLocator).sendKeys(value + Keys.ENTER);
				waitForLoad();
				reportlog(name + "Selected successfully", "INFO");
			}
		} catch (Exception e) {
			reportlog("An exception occured while select for element " + name + " Exeception:" + e, "FAIL",
					"Select text fail");
		}

	}

	/**
	 * scroll down the webdriver
	 * 
	 * @param driver
	 * @throws IOException
	 */
	public void windowscrolldown() throws IOException {
		JavascriptExecutor js = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
		js.executeScript("window.scrollBy(0,350)", "");
		keyboardentry(Keys.ESCAPE);
	}

	/**
	 * scroll up the webdriver
	 * 
	 * @param driver
	 * @throws IOException
	 */
	public void windowscrollup() throws IOException {
		JavascriptExecutor js = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
		js.executeScript("window.scrollBy(0,-350)", "");
		keyboardentry(Keys.ESCAPE);
	}

	public void windowscrollVisibleElement(By ElementLocator) throws InterruptedException {

		// Locating element
		WebElement Element = BrowserFactory.getInstance().getDriver().findElement(ElementLocator);

		// Scrolling down the page till the element is found
		JavascriptExecutor js = (JavascriptExecutor) BrowserFactory.getInstance().getDriver();
		js.executeScript("window.scrollBy(0,0)", "");
		js.executeScript("arguments[0].scrollIntoView();", Element);

		focusElement(Element);
		waitForLoad();
	}

	/**
	 * explicit wait component for webdriver and element locator
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @throws InterruptedException
	 */
	public void waitforelementvisible(int timeout, By ElementLocator) {

		try {
//			WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(),
//					Duration.ofSeconds(timeout));
			new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(timeout))
					.until(ExpectedConditions.visibilityOfElementLocated(ElementLocator));
//			BrowserFactory.getInstance().getwebDriverwait().withTimeout(Duration.ofSeconds(timeout));
//			BrowserFactory.getInstance().getwebDriverwait().until(ExpectedConditions.visibilityOfElementLocated(ElementLocator));
		} catch (Exception e) {
			// do nothing
			System.out.println("wait issue - " + e);
		}
	}

	/**
	 * explicit wait component for webdriver and element locator
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @throws InterruptedException
	 */
	public void waitforelementclickable(int timeout, By ElementLocator) {

		try {
//			WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(),
//					Duration.ofSeconds(timeout));
//			BrowserFactory.getInstance().getwebdriverwait().withTimeout(Duration.ofSeconds(timeout));
			new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(timeout))
					.until(ExpectedConditions.elementToBeClickable(ElementLocator));
		} catch (Exception e) {
			// do nothing
			System.out.println("wait issue - " + e);
		}
	}

	/**
	 * check whether element interactable in the current application page
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @return
	 */
	public boolean checkelementinteractable(int timeout, By ElementLocator) {
		boolean interact = false;
		try {
//			WebDriverWait wait = new WebDriverWait(BrowserFactory.getInstance().getDriver(),
//					Duration.ofSeconds(timeout));
//			BrowserFactory.getInstance().getwebDriverwait().withTimeout(Duration.ofSeconds(timeout));
			new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(timeout))
					.until(ExpectedConditions.elementToBeClickable(ElementLocator));
			interact = true;
		} catch (Exception e) {
			// do nothing
			System.out.println("wait issue - " + e);
			interact = false;
		}
		return interact;
	}

	/**
	 * hard wait for execution to stop in the middle
	 * 
	 * @param timeout
	 * @throws InterruptedException
	 */
	public void hardwait(int timeout) throws InterruptedException {
		Thread.currentThread();
		Thread.sleep(timeout);
	}

	/**
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generaterandomnumber(int min, int max) {
		Random rand = new Random();

		// nextInt as provided by Random is exclusive of the top value so you need to
		// add 1

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static String randomWord() {
		Random random = new Random();
		StringBuilder word = new StringBuilder(3);
		for (int i = 0; i < 3; i++) {
			word.append((char) ('a' + random.nextInt(26)));
		}

		return word.toString();
	}

	public String randomNumForDate() {
		Random random = new Random();
		int randValue = random.nextInt(10);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, randValue);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.YEAR, 1);

		Date date = cal.getTime();
		System.out.println("Date is" + date);

		SimpleDateFormat dateFormat = new SimpleDateFormat("D/MM/YYYY");
		String dateString = dateFormat.format(date);

		System.out.println("Formatted Date:" + dateString);

		return dateString;

	}

	public String currentDate() {
		String currentDate = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		currentDate = dtf.format(now);
		System.out.println(dtf.format(now));
		return currentDate;

	}

	public String nextDate() {
		String currentDate = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = (LocalDateTime.now()).plusDays(1);
		currentDate = dtf.format(now);
		System.out.println(dtf.format(now));
		return currentDate;

	}

	public String pastDate() {
		String currentDate = null;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = (LocalDateTime.now()).minusDays(1);
		currentDate = dtf.format(now);
		System.out.println(dtf.format(now));
		return currentDate;

	}

	/**
	 * check whether Checkbox enabled or not
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @return
	 */
	public boolean checkcheckboxenabled(By ElementLocator) {
		boolean returnvalue = false;
		try {
			waitForLoad();
			waitforelementvisible(10, ElementLocator);
			System.out.println("^^^^^checkbox enable---" + BrowserFactory.getInstance().getDriver()
					.findElement(ElementLocator).getAttribute("background-color"));
			if (BrowserFactory.getInstance().getDriver().findElement(ElementLocator).isSelected()) {
				returnvalue = true;
			}
		} catch (Exception e) {
			System.out.println("^^^^^checkbox enable exception---" + e.getMessage());
			returnvalue = false;
		}
		return returnvalue;
	}

	/**
	 * check whether element exist in the current application page
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @return
	 * @throws IOException
	 * @throws Throwable
	 */
	public boolean checkelementexists(long timeout, By ElementLocator) {
		boolean returnvalue = false;
		try {
			// waitForLoad();

//			WebElement foo = new WebDriverWait(BrowserFactory.getInstance().getDriver(), Duration.ofSeconds(timeout))
//					.until(element -> element.findElement(ElementLocator));
			if (BrowserFactory.getInstance().getDriver().findElements(ElementLocator).isEmpty()) {
				returnvalue = false;
			} else {
				returnvalue = true;
				try {
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).isDisplayed();
				} catch (Exception f) {
					windowscrollVisibleElement(ElementLocator);
				}
			}
			return returnvalue;
		} catch (Exception e) {
			System.out.println("issue check element exist - " + e);
			returnvalue = false;
		}
		return returnvalue;
	}

	public boolean checkelementexistsmillis(long timeout, By ElementLocator) {
		boolean returnvalue = false;
		try {
			// waitForLoad();
			if (BrowserFactory.getInstance().getDriver().findElements(ElementLocator).isEmpty()) {
				returnvalue = false;
			} else {
				returnvalue = true;
				try {
					BrowserFactory.getInstance().getDriver().findElement(ElementLocator).isDisplayed();
				} catch (Exception f) {
					windowscrollVisibleElement(ElementLocator);
				}
			}
			return returnvalue;
		} catch (Exception e) {
			System.out.println("issue check element exist - " + e);
			returnvalue = false;
		}
		return returnvalue;
	}

	/**
	 * do page refresh
	 * 
	 * @param driver
	 * @param timeout
	 * @param ElementLocator
	 * @return
	 */
	public void pagerefresh() {
		BrowserFactory.getInstance().getDriver().navigate().refresh();
		waitForLoad();
	}

	/**
	 * method to connect DB and return query result
	 * 
	 * @param query
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String[][] getDBValues(String query) {

		// String rn[][] = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String hostname = prop.getProperty("DBHostname");
			String username = prop.getProperty("DBUsername");
			String password = prop.getProperty("DBPassword");
			Connection con = DriverManager.getConnection(hostname, username, password);

			/*
			 * Statement stmt=con.createStatement(); ResultSet rs=stmt.executeQuery(query);
			 */

			// alternate method to overcome type forward only exception
			PreparedStatement pstat = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pstat.executeQuery();
			System.out.println("execute query");

			// retrieve row count
			rs.last();
			int rows = rs.getRow();
			System.out.println("DB Rows:" + rows);

			// retrieve column count
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();
			System.out.println("DB column:" + columns);

			if (rows > 0) {
				String data[][] = new String[rows][columns];

				int i = 0;
				rs.beforeFirst();
				while (rs.next()) {
					for (int j = 0; j < columns; j++) {
						data[i][j] = rs.getString(j + 1);
						System.out.println("data[i][j]:" + data[i][j]);
					}
					i++;
				}
				return data;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			return null;
		}

	}

	/**
	 * method to connect DB and update data query result
	 * 
	 * @param query
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void updateDBValues(String query) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String hostname = prop.getProperty("DBHostname");
			String username = prop.getProperty("DBUsername");
			String password = prop.getProperty("DBPassword");
			Connection con = DriverManager.getConnection(hostname, username, password);

			/*
			 * Statement stmt=con.createStatement(); ResultSet rs=stmt.executeQuery(query);
			 */

			// alternate method to overcome type forward only exception
			PreparedStatement pstat = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			int rs = pstat.executeUpdate(query);
			System.out.println("update query:" + rs);
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
		}

	}

	public Object[][] getexcelinput(String sheetname, String testname) {
		try {
			// declare data formatter
			DataFormatter format = new DataFormatter();

			// create file for datasheet
			FileInputStream tsdata = new FileInputStream(
					System.getProperty("user.dir") + System.getProperty("file.separator") + "excelinput"
							+ System.getProperty("file.separator") + "TestData.xlsx");

			// create workbook and worksheet
			XSSFWorkbook wb = new XSSFWorkbook(tsdata);
			XSSFSheet ws = wb.getSheet(sheetname);
//		XSSFSheet ws = wb.getSheetAt(0);

			// get rowcount and columncount
			int rowcount = ws.getPhysicalNumberOfRows();
			XSSFRow row = ws.getRow(0);
			int colcount = row.getLastCellNum();

			System.out.println("Rows:" + rowcount);
			System.out.println("col:" + colcount);
			// create data object
			Object data[][] = new Object[rowcount - 1][colcount];

			System.out.println(sheetname + ":");
			System.out.println(testname + ":");
			// read values from excel cell
			for (int i = 0; i < rowcount - 1; i++) {
				row = ws.getRow(i + 1);
				if (row.getCell(0).toString().equalsIgnoreCase(testname)) {
					System.out.println("matched:" + row.getCell(0).toString());
					for (int j = 0; j < colcount; j++) {
						data[i][j] = format.formatCellValue(row.getCell(j));
						System.out.println("data at " + i + ", " + j + " " + data[i][j]);
					}
				}
			}

			wb.close();
			return data;
		} catch (Exception e) {
			System.out.println("Exception occurs:" + e);
		}
		return null;
	}

	/**
	 * generic function to get value from property file using propertykey name
	 * 
	 * @param PropertyKey
	 * @return
	 */
	public static String getpropertyvalue(String PropertyKey) {
		return prop.getProperty(PropertyKey);
	}

	/**
	 * generic component to take screenshot using web driver
	 * 
	 * @return screenshot path
	 * @throws IOException
	 */
	public String takescreenshot() throws IOException {
		// create screenshot file name
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String temp = formatter.format(cal.getTime());
		int randomNum = generaterandomnumber(100, 999);
		temp = temp.replaceAll("_", "") + randomNum;
		String screenshotpath = System.getProperty("user.dir") + System.getProperty("file.separator") + "screenshots"
				+ System.getProperty("file.separator") + "R_" + temp + ".png";

		// wait for page load
		waitForLoad();

		// Convert webdriver to TakeScreenshot
		File screenshotFile = ((TakesScreenshot) BrowserFactory.getInstance().getDriver())
				.getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(screenshotFile, new File(screenshotpath));

		if (System.getProperty("user.dir").indexOf("jenkins") > -1) {
			String jenkinsserver = System.getProperty("jenkinsserver");// "https://test.mousiki.io";
			String jenkinsbuildname = System.getProperty("jenkinsbuild");// "MosuikiTestBuild";
			screenshotpath = jenkinsserver + "/job/" + jenkinsbuildname + "/ws/screenshots/R_" + temp + ".png";
//			screenshotpath = "https://test.mousiki.io/job/FIrstBuildTest/ws/screenshots/RUN_" + temp + ".png";
		}
		return screenshotpath;
	}

	public void extenttestinitialize(String testname) {
//		test = extent.createTest(testname);
		// extenttest = new ThreadLocal<ExtentTest>();
		// extenttest.set(extent.createTest(testname));

		ExtentTestFactory.getInstance().setextenttest(extent, testname);

	}

	public void extenttestinitialize(String testname, String description) {
		// extenttest = new ThreadLocal<ExtentTest>();
		// extenttest.set(extent.createTest(testname, description));

		ExtentTestFactory.getInstance().setextenttest(extent, testname, description);
	}

	/**
	 * method for logging steps in extend report with screenshot
	 * 
	 * @param stepdescription
	 * @param status
	 * @param screenshotname
	 * @throws IOException
	 */
	public void reportlog(String stepdescription, String status, String screenshotname) throws IOException {
		/*
		 * if(status.equalsIgnoreCase("PASS")) { test.pass(stepdescription);
		 * test.addScreenCaptureFromPath(takescreenshot(), screenshotname); }else
		 * if(status.equalsIgnoreCase("FAIL")) { test.fail(stepdescription);
		 * test.addScreenCaptureFromPath(takescreenshot(), screenshotname); }else
		 * if(status.equalsIgnoreCase("INFO")) { test.info(stepdescription);
		 * test.addScreenCaptureFromPath(takescreenshot(), screenshotname); }else {
		 * test.info(stepdescription); test.addScreenCaptureFromPath(takescreenshot(),
		 * screenshotname); }
		 */
		Assertion softAssert = new SoftAssert();
		if (status.equalsIgnoreCase("PASS")) {
			ExtentTestFactory.getInstance().getextenttest().pass(stepdescription);
			ExtentTestFactory.getInstance().getextenttest().addScreenCaptureFromPath(takescreenshot(), screenshotname);
			Assert.assertTrue(true, stepdescription);
		} else if (status.equalsIgnoreCase("FAIL")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
			ExtentTestFactory.getInstance().getextenttest().addScreenCaptureFromPath(takescreenshot(), screenshotname);
			Assert.fail(stepdescription);
		} else if (status.equalsIgnoreCase("INFO")) {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
			ExtentTestFactory.getInstance().getextenttest().addScreenCaptureFromPath(takescreenshot(), screenshotname);
		} else if (status.equalsIgnoreCase("FAILCONTINUE")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
			ExtentTestFactory.getInstance().getextenttest().addScreenCaptureFromPath(takescreenshot(), screenshotname);
			softAssert.fail(stepdescription);
		} else {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
			ExtentTestFactory.getInstance().getextenttest().addScreenCaptureFromPath(takescreenshot(), screenshotname);
			softAssert.fail(stepdescription);
		}
	}

	/**
	 * method for logging steps in extend report without screenshot
	 * 
	 * @param stepdescription
	 * @param status
	 * @throws IOException
	 */
	public void reportlog(String stepdescription, String status) throws IOException {
		/*
		 * if(status.equalsIgnoreCase("PASS")) { test.pass(stepdescription); }else
		 * if(status.equalsIgnoreCase("FAIL")) { test.fail(stepdescription); }else
		 * if(status.equalsIgnoreCase("INFO")) { test.info(stepdescription); }else {
		 * test.info(stepdescription); }
		 */
		if (status.equalsIgnoreCase("PASS")) {
			ExtentTestFactory.getInstance().getextenttest().pass(stepdescription);
			Assert.assertTrue(true, stepdescription);
		} else if (status.equalsIgnoreCase("FAIL")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
			Assert.fail(stepdescription);
		} else if (status.equalsIgnoreCase("INFO")) {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		} else if (status.equalsIgnoreCase("FAILCONTINUE")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
		} else {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		}
	}

	public void reportlog(String stepdescription, String status, Response code) throws IOException {
		/*
		 * if(status.equalsIgnoreCase("PASS")) { test.pass(stepdescription); }else
		 * if(status.equalsIgnoreCase("FAIL")) { test.fail(stepdescription); }else
		 * if(status.equalsIgnoreCase("INFO")) { test.info(stepdescription); }else {
		 * test.info(stepdescription); }
		 */
		if (status.equalsIgnoreCase("PASS")) {
			ExtentTestFactory.getInstance().getextenttest().createNode(stepdescription)
					.pass(MarkupHelper.createCodeBlock(code.getBody().asPrettyString(), CodeLanguage.JSON));
			Assert.assertTrue(true, stepdescription);
		} else if (status.equalsIgnoreCase("FAIL")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
			Assert.fail(stepdescription);
		} else if (status.equalsIgnoreCase("INFO")) {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		} else if (status.equalsIgnoreCase("FAILCONTINUE")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
		} else {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		}
	}

	@SuppressWarnings("rawtypes")
	public void reportlog(String stepdescription, String status, ResponseBody resp) throws IOException {
		/*
		 * if(status.equalsIgnoreCase("PASS")) { test.pass(stepdescription); }else
		 * if(status.equalsIgnoreCase("FAIL")) { test.fail(stepdescription); }else
		 * if(status.equalsIgnoreCase("INFO")) { test.info(stepdescription); }else {
		 * test.info(stepdescription); }
		 */
		if (status.equalsIgnoreCase("PASS")) {
			ExtentTestFactory.getInstance().getextenttest().createNode(stepdescription)
					.pass(MarkupHelper.createCodeBlock(resp.asPrettyString(), CodeLanguage.JSON));
			Assert.assertTrue(true, stepdescription);
		} else if (status.equalsIgnoreCase("FAIL")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
			Assert.fail(stepdescription);
		} else if (status.equalsIgnoreCase("INFO")) {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		} else if (status.equalsIgnoreCase("FAILCONTINUE")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
		} else {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void reportlog(String stepdescription, String status,String statusCode,String resp) throws IOException {
		/*
		 * if(status.equalsIgnoreCase("PASS")) { test.pass(stepdescription); }else
		 * if(status.equalsIgnoreCase("FAIL")) { test.fail(stepdescription); }else
		 * if(status.equalsIgnoreCase("INFO")) { test.info(stepdescription); }else {
		 * test.info(stepdescription); }
		 */
		if (status.equalsIgnoreCase("PASS")) {
			ExtentTestFactory.getInstance().getextenttest().createNode(stepdescription)
					.pass(MarkupHelper.createCodeBlock(resp, CodeLanguage.JSON)).createNode(statusCode);
			Assert.assertTrue(true, stepdescription);
		} else if (status.equalsIgnoreCase("FAIL")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription).fail(MarkupHelper.createCodeBlock(resp, CodeLanguage.JSON)).createNode(statusCode).createNode(resp);
			Assert.fail(stepdescription);
		} else if (status.equalsIgnoreCase("INFO")) {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		} else if (status.equalsIgnoreCase("FAILCONTINUE")) {
			ExtentTestFactory.getInstance().getextenttest().fail(stepdescription);
		} else {
			ExtentTestFactory.getInstance().getextenttest().info(stepdescription);
		}
	}

	// function to generate a random string of length n
	public String getAlphaNumericString(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	public void switchframebyindex(int index) throws IOException {
		BrowserFactory.getInstance().getDriver().switchTo().frame(index);
	}

	public void switchframebydefault() throws IOException {
		BrowserFactory.getInstance().getDriver().switchTo().defaultContent();
	}

	/**
	 * scroll to page end and load all dynamic elements
	 * 
	 * @throws IOException
	 */
	public void windowscrolldowntoend() throws IOException {
		try {
//            long lastHeight = (long) ((JavascriptExecutor) BrowserFactory.getInstance().getDriver()).executeScript("return document.body.scrollHeight");
			int cont = 1000;
			while (true) {
				((JavascriptExecutor) BrowserFactory.getInstance().getDriver())
						.executeScript("window.scrollTo(0, " + cont + ");");
				waitForLoad();
				hardwait(2000);

				long newHeight = (long) ((JavascriptExecutor) BrowserFactory.getInstance().getDriver())
						.executeScript("return document.body.scrollHeight");
				if (newHeight <= cont) {
					break;
				}
//              lastHeight = newHeight;
				cont += 500;
			}
		} catch (InterruptedException e) {
//            e.printStackTrace();
		}
	}

	/**
	 * scroll to page end and load all dynamic elements
	 * 
	 * @throws IOException
	 */
	public void windowscrolltotop() throws IOException {
		((JavascriptExecutor) BrowserFactory.getInstance().getDriver())
				.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}

	/**
	 * generic application to handle dropdown
	 * 
	 * @param emailid
	 * @param password
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void selectoptionindropdown(By element, String option) throws IOException {
		Actions keyDown = new Actions(BrowserFactory.getInstance().getDriver());
		if ((!option.equalsIgnoreCase("(BLANK)")) && checkelementexists(20, element)) {
			System.out.println("~~~~~~~~~~~~~~If select option");
			keyDown.sendKeys(BrowserFactory.getInstance().getDriver().findElement(element), option).perform();
			try {
				hardwait(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// keyDown.sendKeys(driver.findElement(element), Keys.ENTER).perform();
			keyboardentry(Keys.ENTER);
		} else {
			System.out.println("~~~~~~~~~~~~~~Else select option");
			keyboardentry(Keys.ESCAPE);
			// keyDown.sendKeys(Keys.ESCAPE).build().perform();
		}
	}

	/**
	 * generic application to handle different device dialog
	 * 
	 * @param emailid
	 * @param password
	 * @throws IOException
	 */

	public void keyboardentry(Keys inputkey) throws IOException {
		Actions keyDown = new Actions(BrowserFactory.getInstance().getDriver());
		keyDown.sendKeys(inputkey).build().perform();
	}

	public void focusElement(WebElement element) {
		String javaScript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj);";

		((JavascriptExecutor) BrowserFactory.getInstance().getDriver()).executeScript(javaScript, element);

		// new Actions(driver).moveToElement(element).click().build().perform();
	}

	public static String randomEmailId() {
		String emailId = null;
		int randValue = generaterandomnumber(1, 1000);
		if (randValue > 1) {
			emailId = "testmousiki" + randValue + "@gmail.com";
			System.out.println("Random Generated Email: " + emailId);
		}

		return emailId;

	}

	public String getmonthname(String inputdate) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(inputdate));
		return c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
	}

	public String incrementadate(String inputdate, int incrementdays) throws Throwable {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(inputdate));
		c.add(Calendar.DATE, incrementdays);
		inputdate = sdf.format(c.getTime()); // dt is now the new date
		return inputdate;
	}

	public String getcurrentdate() throws Throwable {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return formatter.format(date);
	}

	public String getcurrenttime(String timezone) throws Throwable {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
//		System.setProperty("user.timezone", timezone);
//		String systemtime = System.getProperty("user.timezone");
		String time = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		time = formatter.format(cal.getTime());
		System.out.println(time);
		return time;
	}

	public String getcurrenttime() throws Throwable {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa");
//		
		String time = null;
		Date date = new Date();
		time = formatter.format(date);
		System.out.println(time);
		return time;
	}

	public String randomEmailByFirstName(String firstName) {
		String emailId = null;
		int randValue = generaterandomnumber(1, 10000);
		if (randValue > 1) {
			emailId = firstName.toLowerCase() + randValue + "@gmail.com";
			System.out.println("Random Generated Email: " + emailId);
		}

		return emailId;

	}

	public void openanewtab(String urlToClick) throws IOException {
		String currentHandle = BrowserFactory.getInstance().getDriver().getWindowHandle();
		Set<String> handles = BrowserFactory.getInstance().getDriver().getWindowHandles();
		for (String actual : handles) {
			if (!actual.equalsIgnoreCase(currentHandle)) {
				// Switch to the opened tab
				BrowserFactory.getInstance().getDriver().switchTo().window(actual);
				// opening the URL saved.
				BrowserFactory.getInstance().getDriver().get(urlToClick);
			}
		}
	}

	public String getwindowhandle() throws IOException {
		return BrowserFactory.getInstance().getDriver().getWindowHandle();
	}

	public Set<String> getwindowhandles() throws IOException {
		return BrowserFactory.getInstance().getDriver().getWindowHandles();
	}

	public void switchtowindowhandle(String windowreference) throws IOException {
		BrowserFactory.getInstance().getDriver().switchTo().window(windowreference);
	}

//	public void openNewWindow() {
//		// To handle parent window
//		String MainWindow = BrowserFactory.getInstance().getDriver().getWindowHandle();
//		System.out.println("Main window handle is " + MainWindow);
//
//		// To handle child window
//		Set<String> s1 = BrowserFactory.getInstance().getDriver().getWindowHandles();
//		System.out.println("Child window handle is" + s1);
//		Iterator<String> i1 = s1.iterator();
//		while (i1.hasNext()) {
//			String ChildWindow = i1.next();
//			if (!MainWindow.equalsIgnoreCase(ChildWindow)) {
//				BrowserFactory.getInstance().getDriver().switchTo().window(ChildWindow);
//				String pageTitle = BrowserFactory.getInstance().getDriver().getTitle();
//				System.out.println("The web page title of child window is:" + pageTitle);
//			}
//		}
//	}

	public void openNewWindow() {
		BrowserFactory.getInstance().getDriver().switchTo().newWindow(WindowType.WINDOW);
		BrowserFactory.getInstance().getDriver().get(prop.getProperty("URL") + "SignIn");
		System.out.println("New Window Page Title:" + BrowserFactory.getInstance().getDriver().getTitle());
//		String currentHandle = BrowserFactory.getInstance().getDriver().getWindowHandle();
//		Set<String> handles = BrowserFactory.getInstance().getDriver().getWindowHandles();
//		for (String actual : handles) {
//			if (!actual.equalsIgnoreCase(currentHandle)) {
//				// Switch to the opened tab
//				BrowserFactory.getInstance().getDriver().switchTo().window(currentHandle);
//				// opening the URL saved.
//				BrowserFactory.getInstance().getDriver().get(prop.getProperty("URL") + "SignIn");
//			}
//		}
		System.out.println("Size:" + BrowserFactory.getInstance().getDriver().getWindowHandles().size());
	}

	@AfterMethod
	public void closebrowser() throws IOException {
		System.out.println("After method");
//		extent.flush();
//		extent.endTest(test);
		// app_Logout();
//		BrowserFactory.getInstance().removeDriver();
	}

	@AfterTest
	public void backtohomepage() throws IOException {
		System.out.println("after test complete");
		extent.flush();
	}

	@AfterClass
	public void extentflush() {
		System.out.println("after class complete - Extent report flush");

//		extent.endTest(test);
//		driver.close();

	}

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		try {
			System.out.println("~~~~~Automation Started");
			int threadcount = Integer.parseInt(System.getProperty("methodthreadcount"));
			if (System.getProperty("testmodule").equalsIgnoreCase("DEFAULT")) {
				context.getSuite().getXmlSuite().setParallel(ParallelMode.TESTS);
				System.out.println("~~~~~Parellel Mode: Tests");
			} else {
				context.getSuite().getXmlSuite().setParallel(ParallelMode.METHODS);
				System.out.println("~~~~~Parellel Mode: Methods");
			}
			context.getSuite().getXmlSuite().setThreadCount(threadcount);

		} catch (Exception e) {
			throw e;
		}
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite(ITestContext context) throws Exception {
		try {
			System.out.println("~~~~~Automation Finished");
		} catch (Exception e) {
			throw e;
		}
	}

}
