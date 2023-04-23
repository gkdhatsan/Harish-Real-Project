package io.testbase;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerTest implements ITestListener {

	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener-onFinish-Accounting Test Automation - Finished");
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener-onStart-Accounting Test Automation - Started test - " + arg0.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener-onTestFailedButWithinSuccessPercentage-Test - " + arg0.getName() + "Status -"
				+ arg0.getStatus() + " is failed with success percentage");
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		// TODO Auto-generated method stub
		System.out.println(
				"Listener-onTestFailure-Test - " + arg0.getName() + "Status -" + arg0.getStatus() + " is Failed");
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener-onTestSkipped-Test - " + arg0.getName() + "Status -" + arg0.getStatus()
				+ arg0.getThrowable() + " is Skipped");
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener-onTestStart-Accounting Test Automation - Name:" + arg0.getName() + "Status -"
				+ arg0.getStatus());
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		System.out.println(
				"Listener-onTestSuccess-Test - " + arg0.getName() + "Status -" + arg0.getStatus() + " is succeed");
	}
}
