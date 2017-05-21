package views;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class AppView {

	protected AndroidDriver<AndroidElement> driver;

	public AppView(AndroidDriver<AndroidElement> driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@AndroidFindBy(id = "com.slava.buylist:id/button1")
	AndroidElement menu;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Settings']")
	AndroidElement settings;
	
	@AndroidFindBy(xpath = "//android.widget.TextView[@text='My List']")
	AndroidElement myList;

	By title = By.id("com.slava.buylist:id/textView1");

	public AndroidElement menu() {
		return menu;
	}

	public AndroidElement settings() {
		return settings;
	}
	
	public AndroidElement myList() {
		return myList;
	}

	public AndroidElement title() {
		return driver.findElement(title);
	}
	
	public By titlePath() {
		return title;
	}
	
	public void goToMyListFromMenu() {
		menu().click();
		myList().click();
	}
	
	public void goToMySettingsFromMenu() {
		menu().click();
		settings().click();
	}	
}
