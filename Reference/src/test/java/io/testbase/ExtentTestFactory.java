package io.testbase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestFactory {
	private ExtentTestFactory() {
		// Do-nothing..Do not allow to initialize this class from outside
	}

	private static ExtentTestFactory instance = new ExtentTestFactory();

	public static ExtentTestFactory getInstance() {
		return instance;
	}

	ThreadLocal<ExtentTest> extenttest = new ThreadLocal<ExtentTest>();

	public void setextenttest(ExtentReports extent, String testname) {
		extenttest.set(extent.createTest(testname));
	}

	public void setextenttest(ExtentReports extent, String testname, String description) {
		extenttest.set(extent.createTest(testname, description));
	}

	public ExtentTest getextenttest() // call this method to get the driver object and launch the browser
	{
		return extenttest.get();
	}

}
