package views;

import static utils.AndroidDriverUtils.waitForElement;
import static utils.AndroidDriverUtils.selectInDropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import views.blocks.ProductsList;

public class ShoppingListView extends AppView {

	protected ProductsList productsList;
	By total = By.id("com.slava.buylist:id/textView2");
	By selectedDropdownValue = By.id("android:id/text1");

	@AndroidFindBy(id = "com.slava.buylist:id/editText1")
	AndroidElement product;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Add from my list']")
	AndroidElement addFromMyList;

	@AndroidFindBy(id = "com.slava.buylist:id/button2")
	AndroidElement okMyList;

	@AndroidFindBy(id = "com.slava.buylist:id/button2")
	AndroidElement addProduct;

	@AndroidFindBy(id = "com.slava.buylist:id/editText2")
	AndroidElement price;

	@AndroidFindBy(id = "com.slava.buylist:id/editText3")
	AndroidElement amount;

	@AndroidFindBy(id = "com.slava.buylist:id/spinner1")
	AndroidElement unit;

	@AndroidFindBy(id = "com.slava.buylist:id/editText4")
	AndroidElement comment;

	@AndroidFindBy(id = "com.slava.buylist:id/spinner2")
	AndroidElement category;

	public ShoppingListView(AndroidDriver<AndroidElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		productsList = new ProductsList(driver);
	}

	public AndroidElement total() {
		return driver.findElement(total);
	}

	public AndroidElement product() {
		return product;
	}

	public AndroidElement addProduct() {
		return addProduct;
	}

	public AndroidElement price() {
		return price;
	}

	public AndroidElement amount() {
		return amount;
	}

	public AndroidElement unit() {
		return unit;
	}

	public AndroidElement comment() {
		return comment;
	}

	public AndroidElement category() {
		return category;
	}

	public AndroidElement productsList() {
		return productsList.entries();
	}

	public AndroidElement addFromMyList() {
		return addFromMyList;
	}

	public AndroidElement okMyList() {
		return okMyList;
	}

	public int getProductsSize() {
		return productsList.getEntriesSize();
	}

	public String getProductName(int index) {
		return productsList.getName(index);
	}

	public String getProductComment(int index) {
		return productsList.getComment(index);
	}

	public String getProductAmount(int index) {
		return productsList.getProductAmount(index);
	}

	public String getProductPrice(int index) {
		return productsList.getProductPrice(index);
	}

	public void addProductsFromMyList() {
		menu().click();
		addFromMyList().click();
	}

	public void addProductFromMyList(String productName) {
		By prodPath = By.xpath(String.format("//android.widget.TextView[@text='%s']", productName));
		waitForElement(driver, prodPath);
		driver.findElement(prodPath).click();
	}

	public void editProduct(int index) {
		productsList.editProduct(index);
	}

	public void removeProduct(int index) {
		productsList.removeProduct(index);
	}
	
	public void removeAllProducts() {
		productsList.removeAllProducts();
	}

	public void selectCategory(String name) {
		selectInDropdown(driver, name);
	}

	public void selectUnit(String name) {
		selectInDropdown(driver, name);
	}

	public String getSelectedCategoryValue() {
		return category().findElement(selectedDropdownValue).getText();
	}

	public String getSelectedUnitValue() {
		return unit().findElement(selectedDropdownValue).getText();
	}

}
