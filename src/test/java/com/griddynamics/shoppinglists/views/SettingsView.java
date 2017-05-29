package com.griddynamics.shoppinglists.views;

import static com.griddynamics.shoppinglists.utils.AndroidDriverUtils.selectInDropdown;
import static com.griddynamics.shoppinglists.utils.AndroidDriverUtils.waitForElement;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class SettingsView {

	By sortList = By.xpath("//android.widget.TextView[@text='Sort list']");
	By currency = By.xpath("//android.widget.TextView[@text='Currency']");

	protected AndroidDriver<AndroidElement> driver;

	public SettingsView(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
	}

	private void selectValueOfOption(By option, String value) {
		selectValueOfOptionWithoutClosingSettings(option, value);
		driver.navigate().back();
	}

	private void selectValueOfOptionWithoutClosingSettings(By option, String value) {
		waitForElement(driver, option);
		if (driver.findElements(By.xpath(String.format("//android.widget.TextView[@text='%s']", value))).size() == 0) {
			driver.findElement(option).click();
			By valuePath = By.xpath(String.format("//android.widget.CheckedTextView[@text='%s']", value));
			waitForElement(driver, valuePath);
			driver.findElement(valuePath).click();
		}
	}

	public void selectSortingOrder(String sortName) {
		selectValueOfOption(sortList, sortName);
	}

	public void selectCurrency(String currencyName) {
		selectValueOfOption(currency, currencyName);
	}

	public void applyDefaultSortAndCurrency() {
		selectValueOfOptionWithoutClosingSettings(sortList, "By category");
		selectValueOfOptionWithoutClosingSettings(currency, "£");
		driver.navigate().back();
	}

	public void openEditMyListView() {
		selectInDropdown(driver, "My List");
	}

}
