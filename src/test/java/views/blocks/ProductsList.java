package views.blocks;

import org.openqa.selenium.By;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsList extends EntriesBlock {
	public ProductsList(AndroidDriver<AndroidElement> driver) {
		super("product", driver);
	}

	By productAmountPath = By.id("com.slava.buylist:id/TextView01");
	By productPricePath = By.id("com.slava.buylist:id/textView1");

	@AndroidFindBy(id = "android:id/select_dialog_listview")
	AndroidElement selectProductAction;

	public AndroidElement selectProductAction() {
		return selectProductAction;
	}

	public String getProductAmount(int index) {
		checkEntryIndex(index);
		return entries().findElements(productAmountPath).get(index).getText();
	}

	public String getProductPrice(int index) {
		checkEntryIndex(index);
		return entries().findElements(productPricePath).get(index).getText();
	}

	private void doWithProduct(int index, String actionName) {
		checkEntryIndex(index);
		TouchAction action = new TouchAction(driver);
		action.longPress(entries().findElements(productPricePath).get(index)).release().perform();
		selectProductAction().findElement(By.xpath(String.format("//android.widget.TextView[@text='%s']", actionName)))
				.click();
	}

	public void editProduct(int index) {
		doWithProduct(index, "Edit");
	}
	
	public void removeProduct(int index) {
		doWithProduct(index, "Remove");
		okDialogButton().click();
	}
	
	public void removeAllProducts() {
		while (getEntriesSize() > 0) {
			removeProduct(0);
		}
	}
}