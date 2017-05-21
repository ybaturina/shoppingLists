package appium;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import utils.ScreenshotUtility;

@Listeners({ ScreenshotUtility.class })
public class AppiumServer {

	public static AndroidDriver<AndroidElement> driver;
	private AppiumDriverLocalService driverLocalService = AppiumDriverLocalService
			.buildService(new AppiumServiceBuilder()
					.withAppiumJS(new File(System.getenv("NODE_MODULES_HOME") + "/appium/build/lib/main.js"))
					.usingDriverExecutable(new File(System.getenv("NODE_HOME"))).usingPort(4723)
					.withArgument(GeneralServerFlag.SESSION_OVERRIDE).withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
					.withCapabilities(getCapabilities()));
	private static DesiredCapabilities caps;

	public static DesiredCapabilities getCapabilities() {
		if (caps == null) {
			File appDir = new File("src");
			File app = new File(appDir, "Shopping list-1.6.apk");

			caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");

			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "100");
			caps.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		}
		return caps;
	}

	@BeforeSuite
	public void start() throws IOException, InterruptedException {
		driverLocalService.start();
	}

	@AfterSuite
	public void stop() {
		driverLocalService.stop();
	}

	public static AndroidDriver<AndroidElement> getDriver() {
		if (driver == null) {
			try {
				driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), getCapabilities());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return driver;
	}

}
