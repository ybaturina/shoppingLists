package com.griddynamics.shoppinglists.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.griddynamics.shoppinglists.setup.LocalAndroidDriverProvider;
import com.griddynamics.shoppinglists.setup.WebDriverProvider;

/**
 * Utilities for catching screenshots on test failures.
 * 
 * @author ybaturina
 *
 */
@ContextConfiguration(locations = { "classpath:**/spring-test-config.xml" })
public class ScreenshotUtility extends AbstractTestNGSpringContextTests implements ITestListener {

	@Autowired
	protected WebDriverProvider driverProvider;
	private static final String SCREENSHOTS = "target/screenshots";
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

	public ScreenshotUtility() {
		try {
			springTestContextPrepareTestInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This method will execute before starting of Test suite.
	public void onStart(ITestContext tr) {

	}

	// This method will execute, Once the Test suite is finished.
	public void onFinish(ITestContext tr) {

	}

	// This method will execute only when the test is pass.
	public void onTestSuccess(ITestResult tr) {
	}

	// This method will execute only on the event of fail test.
	public void onTestFailure(ITestResult tr) {
		captureScreenShot(tr);
	}

	// This method will execute before the main test start (@Test)
	public void onTestStart(ITestResult tr) {

	}

	// This method will execute only if any of the main test(@Test) get skipped
	public void onTestSkipped(ITestResult tr) {
		captureScreenShot(tr);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
	}

	// Function to capture screenshot.
	public void captureScreenShot(ITestResult result) {
		if (driverProvider.getClass().equals(LocalAndroidDriverProvider.class)) {
			String failMethod = result.getMethod().getRealClass().getSimpleName() + "."
					+ result.getMethod().getMethodName();
			// To capture screenshot.
			File scrFile = ((TakesScreenshot) driverProvider.getDriver()).getScreenshotAs(OutputType.FILE);
			// To create folder to store screenshots
			File screenshotsDir = new File(SCREENSHOTS);
			if (!screenshotsDir.exists()) {
				new File(SCREENSHOTS).mkdirs();
			}
			// Set file name with combination of test class name + date time.
			String destFile = failMethod + " - " + DATE_FORMAT.format(new Date()) + ".png";

			try {
				// Store file at destination folder location
				FileUtils.copyFile(scrFile, new File(SCREENSHOTS + "/" + destFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
