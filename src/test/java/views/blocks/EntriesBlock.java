package views.blocks;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class EntriesBlock {
	
	protected AndroidDriver<AndroidElement> driver;
	private String blockName;

	@AndroidFindBy(id = "com.slava.buylist:id/listView1")
	private AndroidElement entries;
	
	@AndroidFindBy(id = "android:id/button1")
	AndroidElement okDialogButton;

	private By namePath = By.id("com.slava.buylist:id/title");
	private By commentPath = By.id("com.slava.buylist:id/str1");

	public EntriesBlock(String blockName, AndroidDriver<AndroidElement> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.blockName = blockName;
		this.driver = driver;
	}
	
	public AndroidElement okDialogButton() {
		return okDialogButton;
	}
	
	public AndroidElement entries() {
		return entries;
	}

	public int getEntriesSize() {
		return entries().findElements(namePath).size();
	}

	protected void checkEntryIndex(int index) {
		Assert.assertTrue(index < getEntriesSize(),
				String.format("Invalid %s index: should be less than %s", blockName, getEntriesSize()));
	}

	public String getName(int index) {
		checkEntryIndex(index);
		return entries().findElements(namePath).get(index).getText();
	}
	
	public void clickName(int index) {
		checkEntryIndex(index);
		entries().findElements(namePath).get(index).click();
	}

	public String getComment(int index) {
		checkEntryIndex(index);
		return entries().findElements(commentPath).get(index).getText();
	}
}
