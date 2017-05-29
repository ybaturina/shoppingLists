package com.griddynamics.shoppinglists.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Class with edit shopping lists/products scenarios.
 * 
 * @author ybaturina
 *
 */
public class EditScenarios extends SetupAndTearDownSuite {

	@Test
	public void editListName() {
		steps.getLists().removeAllLists();
		steps.addList("list");
		steps.addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		steps.addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		steps.navigateToListsView();
		steps.verifyListDetails(0, "list", "All items: 2 Sum: 7.4 £");
		steps.getLists().renameList(0, "new list");
		steps.verifyListDetails(0, "new list", "All items: 2 Sum: 7.4 £");
	}

	@Test
	public void editListProducts() {
		steps.getLists().removeAllLists();
		steps.addList("list10");
		steps.addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		steps.addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		steps.verifyProductsCount(2);
		steps.verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
		steps.verifyProductDetails(1, "prod2", "0.1", "10", "comment2");
		assertEquals(steps.getList().total().getText(), "Total: 7.4 £", "Wrong total amount");
		steps.getList().editProduct(0);
		steps.verifyProductDetailsOnEditScreen("prod1", "3.2", "2.0", "comment1");
		steps.updateProductWithPriceAmountComment("prod3", "10.1", "20", "comment3");
		steps.getList().editProduct(1);
		steps.verifyProductDetailsOnEditScreen("prod2", "0.1", "10.0", "comment2");
		steps.updateProductWithPriceAmountComment("prod4", "20.1", "30", "comment4");
		steps.verifyProductDetails(0, "prod3", "10.1", "20", "comment3");
		steps.verifyProductDetails(1, "prod4", "20.1", "30", "comment4");
		assertEquals(steps.getList().total().getText(), "Total: 805 £", "Wrong total amount");
		steps.navigateToListsView();
		steps.verifyListDetails(0, "list10", "All items: 2 Sum: 805 £");
	}

	@Test
	public void editMyList() {
		try {
			steps.getLists().goToMyListFromMenu();
			assertEquals(steps.getList().title().getText(), "My List", "Unexpected shopping list name");
			steps.addProductWithPriceAmountCommentUnitCategory("prod5", "10", "10", "comment5", "pack", "Clothing");
			steps.addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
			steps.verifyProductsCount(2);
			steps.navigateToListsView();
			steps.getLists().goToMySettingsFromMenu();
			steps.getSettings().openEditMyListView();
			assertEquals(steps.getList().title().getText(), "My List", "Unexpected shopping list name");
			steps.verifyProductsCount(2);
			steps.verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
			steps.verifyProductDetails(1, "prod5", "10", "10", "comment5", "pack", "£");
			steps.getList().editProduct(0);
			steps.verifyProductDetailsOnEditScreen("prod1", "3.2", "2.0", "comment1");
			steps.updateProductWithPriceAmountComment("prod3", "10.1", "20", "comment3");
			steps.getList().editProduct(1);
			steps.verifyProductDetailsOnEditScreen("prod5", "10.0", "10.0", "comment5", "pack", "Clothing, footwear");
			steps.updateProductWithPriceAmountCommentUnitCategory("prod4", "20.1", "3", "comment4", "ten", "Grocery");
			steps.verifyProductDetails(0, "prod3", "10.1", "20", "comment3");
			steps.verifyProductDetails(1, "prod4", "20.1", "3", "comment4", "ten", "£");
		} finally {
			steps.navigateToListsView();
			steps.getLists().goToMyListFromMenu();
			steps.getList().removeAllProducts();
		}
	}

}
