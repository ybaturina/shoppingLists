package com.griddynamics.shoppinglists.views;

import org.openqa.selenium.support.PageFactory;

import com.griddynamics.shoppinglists.views.blocks.ListEntries;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ShoppingListsView extends AppView {

	protected ListEntries listEntries;

	@AndroidFindBy(id = "com.slava.buylist:id/editText1")
	AndroidElement listName;

	@AndroidFindBy(id = "com.slava.buylist:id/button2")
	AndroidElement addList;
	
	public ShoppingListsView(AndroidDriver<AndroidElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		listEntries = new ListEntries(driver);
	}

	public AndroidElement listName() {
		return listName;
	}

	public AndroidElement addList() {
		return addList;
	}

	public String getListName(int index) {
		return listEntries.getName(index);
	}

	public String getListComment(int index) {
		return listEntries.getComment(index);
	}

	public void removeAllLists() {
		listEntries.removeAllLists();
	}
	
	public void renameList(int index, String newName) {
		listEntries.renameList(index, newName);
	}
	
	public void editListProducts(int index) {
		listEntries.updateListProducts(index);
	}
	
	public int getListsSize() {
		return listEntries.getEntriesSize();
	}
}
