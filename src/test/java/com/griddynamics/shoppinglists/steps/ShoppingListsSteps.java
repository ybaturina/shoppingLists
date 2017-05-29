package com.griddynamics.shoppinglists.steps;

import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.griddynamics.shoppinglists.setup.WebDriverProvider;
import com.griddynamics.shoppinglists.views.SettingsView;
import com.griddynamics.shoppinglists.views.ShoppingListView;
import com.griddynamics.shoppinglists.views.ShoppingListsView;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/**
 * Class with common steps and test verification.
 * 
 * @author ybaturina
 *
 */
public class ShoppingListsSteps{
	
	private WebDriverProvider driverProvider;
	private ShoppingListsView listsPage;
	private ShoppingListView listPage;
	private SettingsView settingsPage;
	
	public ShoppingListsSteps(WebDriverProvider driverProvider){
		this.driverProvider = driverProvider;
	}
	
	public ShoppingListsView getLists() {
		if (listsPage == null) {
			listsPage = new ShoppingListsView(getDriver());
		}
		return listsPage;
	}

	public ShoppingListView getList() {
		if (listPage == null) {
			listPage = new ShoppingListView(getDriver());
		}
		return listPage;
	}

	public SettingsView getSettings() {
		if (settingsPage == null) {
			settingsPage = new SettingsView(getDriver());
		}
		return settingsPage;
	}

	protected AndroidDriver<AndroidElement> getDriver() {
		return driverProvider.getDriver();
	}

	public void hideKeyboard() {
		try {
			getDriver().hideKeyboard();
		} catch (WebDriverException e) {
			if (!e.getMessage().contains("Soft keyboard not present, cannot hide keyboard")) {
				throw e;
			}
		}
	}

	public void navigateToListsView() {
		hideKeyboard();
		while (!(getDriver().findElements(getList().titlePath()).size() > 0 && getList().title().getText().equals("Buy list"))) {
			getDriver().navigate().back();
		}
	}

	public void addList(String listName) {
		getLists().listName().sendKeys(listName);
		getLists().addList().click();
	}

	public void verifyListDetails(int index, String name, String comment) {
		Assert.assertEquals(getLists().getListName(index), name, "Wrong list name");
		Assert.assertTrue(getLists().getListComment(index).startsWith(comment),
				String.format("Wrong list comment: expected %s, found %s", comment, getLists().getListComment(index)));
	}

	protected void selectFieldData(AndroidElement field) {
		TouchAction touchAction = new TouchAction(getDriver());
		touchAction.longPress(field).perform();
	}

	public void addProductWithPriceAmountComment(String name, String price, String amount, String comment) {
		getList().product().sendKeys(name);
		getList().price().sendKeys(price);
		getList().amount().sendKeys(amount);
		getList().comment().sendKeys(comment);
		getList().addProduct().click();
	}

	public void updateProductWithPriceAmountComment(String name, String price, String amount, String comment) {
		updateProductOnEditScreen(name, price, amount, comment);
		getList().addProduct().click();
	}

	private void updateProductOnEditScreen(String name, String price, String amount, String comment) {
		selectFieldData(getList().product());
		getList().product().sendKeys(name);
		selectFieldData(getList().price());
		getList().price().sendKeys(price);
		selectFieldData(getList().amount());
		getList().amount().sendKeys(amount);
		selectFieldData(getList().comment());
		getList().comment().sendKeys(comment);
	}

	public void updateProductWithPriceAmountCommentUnitCategory(String name, String price, String amount,
			String comment, String unit, String category) {
		updateProductOnEditScreen(name, price, amount, comment);
		getList().category().click();
		getList().selectCategory(category);
		getList().unit().click();
		getList().selectUnit(unit);
		getList().addProduct().click();
	}

	public void verifyProductDetails(int index, String name, String price, String amount, String comment) {
		Assert.assertEquals(getList().getProductName(index), name, "Wrong product name");
		Assert.assertEquals(getList().getProductComment(index), comment, "Wrong product comment");
		Assert.assertEquals(getList().getProductAmount(index), String.format("%s pcs.", amount),
				"Wrong product amount");
		Assert.assertEquals(getList().getProductPrice(index), String.format("%s £", price), "Wrong product price");
	}

	public void verifyProductDetailsOnEditScreen(String name, String price, String amount, String comment) {
		Assert.assertEquals(getList().product().getText(), name, "Wrong product name");
		Assert.assertEquals(getList().comment().getText(), comment, "Wrong product comment");
		Assert.assertEquals(getList().amount().getText(), amount, "Wrong product amount");
		Assert.assertEquals(getList().price().getText(), price, "Wrong product price");
	}

	public void addProductWithPriceAmountCommentUnitCategory(String name, String price, String amount,
			String comment, String unit, String category) {
		getList().product().sendKeys(name);
		getList().price().sendKeys(price);
		getList().amount().sendKeys(amount);
		getList().comment().sendKeys(comment);
		getList().category().click();
		getList().selectCategory(category);
		getList().unit().click();
		getList().selectUnit(unit);
		getList().addProduct().click();
	}

	public void verifyProductDetails(int index, String name, String price, String amount, String comment,
			String unit, String currency) {
		Assert.assertEquals(getList().getProductName(index), name, "Wrong product name");
		Assert.assertEquals(getList().getProductComment(index), comment, "Wrong product comment");
		Assert.assertEquals(getList().getProductAmount(index), String.format("%s %s", amount, unit),
				"Wrong product amount");
		Assert.assertEquals(getList().getProductPrice(index), String.format("%s %s", price, currency),
				"Wrong product price");
	}

	public void verifyProductDetailsOnEditScreen(String name, String price, String amount, String comment,
			String unit, String category) {
		Assert.assertEquals(getList().product().getText(), name, "Wrong product name");
		Assert.assertEquals(getList().comment().getText(), comment, "Wrong product comment");
		Assert.assertEquals(getList().amount().getText(), amount, "Wrong product amount");
		Assert.assertEquals(getList().price().getText(), price, "Wrong product price");
		Assert.assertEquals(getList().getSelectedUnitValue(), unit, "Wrong product unit");
		Assert.assertEquals(getList().getSelectedCategoryValue(), category, "Wrong product category");
	}

	public void verifyListsCount(int count) {
		Assert.assertEquals(getLists().getListsSize(), count, "Wrong lists count:");
	}

	public void verifyProductsCount(int count) {
		Assert.assertEquals(getList().getProductsSize(), count, "Wrong products count:");
	}

	public void verifyProductsTotal(double total, String unit) {
		String expectedTotal = String.format("Total: %s %s", total, unit);
		Assert.assertEquals(getList().total().getText(), expectedTotal, "Wrong products total:");
	}

}
