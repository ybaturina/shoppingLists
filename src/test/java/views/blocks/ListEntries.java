package views.blocks;

import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ListEntries extends EntriesBlock {

	private By removeIcon = By.id("com.slava.buylist:id/imageView1");
	private By editIcon = By.id("com.slava.buylist:id/imageView2");
	private By renameListField = By.className("android.widget.EditText");
	

	@AndroidFindBy(id = "android:id/parentPanel")
	AndroidElement editListPanel;
	
	public ListEntries(AndroidDriver<AndroidElement> driver) {
		super("list", driver);
	}

	public AndroidElement editListPanel() {
		return editListPanel;
	}

	public void removeAllLists() {
		while (getEntriesSize() > 0) {
			entries().findElements(removeIcon).get(0).click();
			driver.findElement(By.id("android:id/button1")).click();
		}
	}

	public void renameList(int index, String newName) {
		checkEntryIndex(index);
		entries().findElements(editIcon).get(index).click();
		editListPanel().findElement(renameListField).clear();
		editListPanel().findElement(renameListField).sendKeys(newName);
		okDialogButton().click();
	}
	
	public void updateListProducts(int index) {
		checkEntryIndex(index);
		clickName(index);
	}

}
