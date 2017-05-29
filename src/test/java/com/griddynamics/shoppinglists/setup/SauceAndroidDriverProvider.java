package com.griddynamics.shoppinglists.setup;

import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * Android driver setup for the execution on the saucelabs.
 * 
 * @author ybaturina
 *
 */
public class SauceAndroidDriverProvider extends WebDriverProvider {

	private final static String LABS_URL_PATH = "http://ybaturina:c9069484-0779-499e-ad23-83eb683c9fc5@ondemand.saucelabs.com:80/wd/hub";

	public SauceAndroidDriverProvider() {
	}

	@Override
	public DesiredCapabilities getCapabilities() {
		if (caps == null) {
			caps = DesiredCapabilities.android();
			caps.setCapability("appiumVersion", "1.6.4");
			caps.setCapability("deviceName", "Android Emulator");
			caps.setCapability("deviceOrientation", "portrait");
			caps.setCapability("platformVersion", "6.0");
			caps.setCapability("platformName", "Android");
			caps.setCapability("browserName", "");
			caps.setCapability("app", "sauce-storage:app.apk");
		}
		return caps;
	}

	@Override
	public void start() throws IOException, InterruptedException {
		getDriver();
	}

	@Override
	public void stop() {
		getDriver().quit();
	}

	@Override
	public AndroidDriver<AndroidElement> getDriver() {
		return getDriver(LABS_URL_PATH);
	}

}
