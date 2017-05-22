package setup;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * Abstract class with methods for settings AndroidDriver configuration.
 * 
 * @author ybaturina
 *
 */
public abstract class AndroidDriverSetup {
	
	public static AndroidDriver<AndroidElement> driver;
	protected DesiredCapabilities caps;

	protected AndroidDriver<AndroidElement> getDriver(String urlPath) {
		if (driver == null) {
			try {
				driver = new AndroidDriver<AndroidElement>(new URL(urlPath), getCapabilities());
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		return driver;
	}
	
	public abstract DesiredCapabilities getCapabilities();

}
