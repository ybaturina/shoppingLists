package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Class with delete shopping lists/products scenarios.
 * 
 * @author ybaturina
 *
 */
public class DeleteScenarios extends AbstractShoppingListsSteps {

	@Test
	public void deleteShoppingLists() {
		addList("list20");
		addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		navigateToListsView();
		addList("list30");
		addProductWithPriceAmountComment("prod3", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod4", "0.1", "10", "comment2");
		navigateToListsView();
		verifyListsCount(2);
		getLists().removeAllLists();
		verifyListsCount(0);
	}

	@Test
	public void deleteProducts() {
		addList("list22");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		addProductWithPriceAmountComment("prod3", "0.21", "20", "comment3");
		addProductWithPriceAmountCommentUnitCategory("prod4", "100.2", "2", "comment4", "m", "Ornamentation");
		verifyProductsCount(3);
		hideKeyboard();
		verifyProductsTotal(205.6, "£");
		getList().removeProduct(0);
		verifyProductsCount(2);
		hideKeyboard();
		verifyProductsTotal(204.6, "£");
		navigateToListsView();
		this.verifyListDetails(0, "list22", "All items: 2 Sum: 204.6 £");
		getLists().editListProducts(0);
		getList().removeProduct(1);
		verifyProductsCount(1);
		hideKeyboard();
		verifyProductsTotal(4.2, "£");
		navigateToListsView();
		this.verifyListDetails(0, "list22", "All items: 1 Sum: 4.2 £");
	}

	@Test
	public void deleteProductsFromMyList() {
		try {
			getLists().goToMyListFromMenu();
			Assert.assertEquals(getList().title().getText(), "My List", "Unexpected shopping list name");
			addProductWithPriceAmountCommentUnitCategory("prod5", "10", "10", "comment5", "pack", "Clothing");
			addProductWithPriceAmountCommentUnitCategory("prod1", "3.2", "2", "comment1", "couple", "Clothing");
			verifyProductsCount(2);
			getList().removeProduct(1);
			verifyProductsCount(1);
			navigateToListsView();
			getLists().goToMySettingsFromMenu();
			getSettings().openEditMyListView();
			verifyProductsCount(1);
			verifyProductDetails(0, "prod5", "10", "10", "comment5", "pack", "£");
			getList().removeProduct(0);
			verifyProductsCount(0);
			navigateToListsView();
			getLists().goToMySettingsFromMenu();
			getSettings().openEditMyListView();
			verifyProductsCount(0);
		} finally {
			navigateToListsView();
			getLists().goToMyListFromMenu();
			getList().removeAllProducts();
		}
	}

}
