package setup.sauce;

import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import setup.AndroidDriverSetup;

/**
 * Android driver setup for the execution on the saucelabs.
 * 
 * @author ybaturina
 *
 */
public class SauceLabsSetup extends AndroidDriverSetup{
	
	private final static String LABS_URL_PATH = "http://ybaturina:c9069484-0779-499e-ad23-83eb683c9fc5@ondemand.saucelabs.com:80/wd/hub";

	@Override
	public DesiredCapabilities getCapabilities() {
		if (caps == null) {
			caps = DesiredCapabilities.android();
			caps.setCapability("appiumVersion", "1.6.4");
			caps.setCapability("deviceName","Android Emulator");
			caps.setCapability("deviceOrientation", "portrait");
			caps.setCapability("platformVersion","6.0");
			caps.setCapability("platformName","Android");
			caps.setCapability("browserName", "");
			caps.setCapability("app","sauce-storage:app.apk");
		}
		return caps;
	}

	@BeforeSuite
	public void start() throws IOException, InterruptedException {
		getDriver();
	}

	@AfterSuite
	public void stop() {
		getDriver().quit();
	}

	public AndroidDriver<AndroidElement> getDriver() {
		return getDriver(LABS_URL_PATH);
	}

}
