package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * Utilities for common complicated AndroidDriver methods.
 * 
 * @author ybaturina
 *
 */
public class AndroidDriverUtils {

	public static void waitForElement(AndroidDriver<AndroidElement> driver, AndroidElement element) {
		(new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElement(AndroidDriver<AndroidElement> driver, By xpath) {
		(new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOf(driver.findElement(xpath)));
	}
	
	public static void selectInDropdown(AndroidDriver<AndroidElement> driver, String name) {
		String scroll = String.format("new UiScrollable(new UiSelector()).scrollIntoView(textStartsWith(\"%s\"));",
				name);
		driver.findElementByAndroidUIAutomator(scroll).click();
	}

}
