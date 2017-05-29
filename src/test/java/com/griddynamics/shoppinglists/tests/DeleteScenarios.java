package com.griddynamics.shoppinglists.tests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Class with delete shopping lists/products scenarios.
 * 
 * @author ybaturina
 *
 */
public class DeleteScenarios extends SetupAndTearDownSuite {

	@Test
	public void deleteShoppingLists() {
		steps.addList("list20");
		steps.addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		steps.addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		steps.navigateToListsView();
		steps.addList("list30");
		steps.addProductWithPriceAmountComment("prod3", "3.2", "2", "comment1");
		steps.addProductWithPriceAmountComment("prod4", "0.1", "10", "comment2");
		steps.navigateToListsView();
		steps.verifyListsCount(2);
		steps.getLists().removeAllLists();
		steps.verifyListsCount(0);
	}

	@Test
	public void deleteProducts() {
		steps.addList("list22");
		steps.addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		steps.addProductWithPriceAmountComment("prod3", "0.21", "20", "comment3");
		steps.addProductWithPriceAmountCommentUnitCategory("prod4", "100.2", "2", "comment4", "m", "Ornamentation");
		steps.verifyProductsCount(3);
		steps.hideKeyboard();
		steps.verifyProductsTotal(205.6, "£");
		steps.getList().removeProduct(0);
		steps.verifyProductsCount(2);
		steps.hideKeyboard();
		steps.verifyProductsTotal(204.6, "£");
		steps.navigateToListsView();
		steps.verifyListDetails(0, "list22", "All items: 2 Sum: 204.6 £");
		steps.getLists().editListProducts(0);
		steps.getList().removeProduct(1);
		steps.verifyProductsCount(1);
		steps.hideKeyboard();
		steps.verifyProductsTotal(4.2, "£");
		steps.navigateToListsView();
		steps.verifyListDetails(0, "list22", "All items: 1 Sum: 4.2 £");
	}

	@Test
	public void deleteProductsFromMyList() {
		try {
			steps.getLists().goToMyListFromMenu();
			assertEquals(steps.getList().title().getText(), "My List", "Unexpected shopping list name");
			steps.addProductWithPriceAmountCommentUnitCategory("prod5", "10", "10", "comment5", "pack", "Clothing");
			steps.addProductWithPriceAmountCommentUnitCategory("prod1", "3.2", "2", "comment1", "couple", "Clothing");
			steps.verifyProductsCount(2);
			steps.getList().removeProduct(1);
			steps.verifyProductsCount(1);
			steps.navigateToListsView();
			steps.getLists().goToMySettingsFromMenu();
			steps.getSettings().openEditMyListView();
			steps.verifyProductsCount(1);
			steps.verifyProductDetails(0, "prod5", "10", "10", "comment5", "pack", "£");
			steps.getList().removeProduct(0);
			steps.verifyProductsCount(0);
			steps.navigateToListsView();
			steps.getLists().goToMySettingsFromMenu();
			steps.getSettings().openEditMyListView();
			steps.verifyProductsCount(0);
		} finally {
			steps.navigateToListsView();
			steps.getLists().goToMyListFromMenu();
			steps.getList().removeAllProducts();
		}
	}

}
