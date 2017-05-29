package com.griddynamics.shoppinglists.setup;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Listeners;

import com.griddynamics.shoppinglists.utils.ScreenshotUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * Android driver setup setup for the execution on the local machine.
 * 
 * @author ybaturina
 *
 */
public class LocalAndroidDriverProvider extends WebDriverProvider {

	private AppiumDriverLocalService driverLocalService;
	private final static String LOCAL_URL_PATH = "http://127.0.0.1:4723/wd/hub";
	private final static String APP_FILE = "Shopping list-1.6.apk";

	public LocalAndroidDriverProvider() {
		driverLocalService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
				.withAppiumJS(new File(System.getenv("NODE_MODULES_HOME") + "/appium/build/lib/main.js"))
				.usingDriverExecutable(new File(System.getenv("NODE_HOME"))).usingPort(4723)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
				.withCapabilities(getCapabilities()));
	}

	@Override
	public DesiredCapabilities getCapabilities() {
		if (caps == null) {
			File appDir = new File("src/test/resources");
			File app = new File(appDir, APP_FILE);

			caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");

			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100");
			caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		}
		return caps;
	}

	@Override
	public void start() throws IOException, InterruptedException {
		driverLocalService.start();
		getDriver();
	}

	@Override
	public void stop() {
		driverLocalService.stop();
	}

	@Override
	public AndroidDriver<AndroidElement> getDriver() {
		return getDriver(LOCAL_URL_PATH);
	}

}
