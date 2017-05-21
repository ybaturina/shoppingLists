package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Class with edit shopping lists/products scenarios.
 * 
 * @author ybaturina
 *
 */
public class EditScenarios extends AbstractShoppingListsSteps {

	@Test
	public void editListName() {
		getLists().removeAllLists();
		addList("list");
		addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		navigateToListsView();
		verifyListDetails(0, "list", "All items: 2 Sum: 7.4 £");
		getLists().renameList(0, "new list");
		verifyListDetails(0, "new list", "All items: 2 Sum: 7.4 £");
	}

	@Test
	public void editListProducts() {
		getLists().removeAllLists();
		addList("list10");
		addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		verifyProductsCount(2);
		verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
		verifyProductDetails(1, "prod2", "0.1", "10", "comment2");
		Assert.assertEquals(getList().total().getText(), "Total: 7.4 £", "Wrong total amount");
		getList().editProduct(0);
		verifyProductDetailsOnEditScreen("prod1", "3.2", "2.0", "comment1");
		updateProductWithPriceAmountComment("prod3", "10.1", "20", "comment3");
		getList().editProduct(1);
		verifyProductDetailsOnEditScreen("prod2", "0.1", "10.0", "comment2");
		updateProductWithPriceAmountComment("prod4", "20.1", "30", "comment4");
		verifyProductDetails(0, "prod3", "10.1", "20", "comment3");
		verifyProductDetails(1, "prod4", "20.1", "30", "comment4");
		Assert.assertEquals(getList().total().getText(), "Total: 805 £", "Wrong total amount");
		navigateToListsView();
		verifyListDetails(0, "list10", "All items: 2 Sum: 805 £");
	}

	@Test
	public void editMyList() {
		try {
			getLists().goToMyListFromMenu();
			Assert.assertEquals(getList().title().getText(), "My List", "Unexpected shopping list name");
			addProductWithPriceAmountCommentUnitCategory("prod5", "10", "10", "comment5", "pack", "Clothing");
			addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
			verifyProductsCount(2);
			navigateToListsView();
			getLists().goToMySettingsFromMenu();
			getSettings().openEditMyListView();
			Assert.assertEquals(getList().title().getText(), "My List", "Unexpected shopping list name");
			verifyProductsCount(2);
			verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
			verifyProductDetails(1, "prod5", "10", "10", "comment5", "pack", "£");
			getList().editProduct(0);
			verifyProductDetailsOnEditScreen("prod1", "3.2", "2.0", "comment1");
			updateProductWithPriceAmountComment("prod3", "10.1", "20", "comment3");
			getList().editProduct(1);
			verifyProductDetailsOnEditScreen("prod5", "10.0", "10.0", "comment5", "pack", "Clothing, footwear");
			updateProductWithPriceAmountCommentUnitCategory("prod4", "20.1", "3", "comment4", "ten", "Grocery");
			verifyProductDetails(0, "prod3", "10.1", "20", "comment3");
			verifyProductDetails(1, "prod4", "20.1", "3", "comment4", "ten", "£");
		} finally {
			navigateToListsView();
			getLists().goToMyListFromMenu();
			getList().removeAllProducts();
		}
	}

}
