package tests;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Class with create shopping lists and adding products test scenarios.
 * 
 * @author ybaturina
 *
 */
public class CreateScenarios extends AbstractShoppingListsSteps {

	@Test
	public void checkSavedListsData() {
		getLists().removeAllLists();
		String expectedListName1 = "list8";
		addList(expectedListName1);
		addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		verifyProductsCount(2);
		navigateToListsView();
		String expectedListName2 = "list7";
		addList(expectedListName2);
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		addProductWithPriceAmountComment("prod3", "0.2", "20", "comment3");
		addProductWithPriceAmountComment("prod4", "100.2", "2", "comment4");
		verifyProductsCount(3);
		navigateToListsView();
		verifyListDetails(0, expectedListName1, "All items: 2 Sum: 7.4 £");
		verifyListDetails(1, expectedListName2, "All items: 3 Sum: 205.4 £");
		getLists().goToMySettingsFromMenu();
		getSettings().selectCurrency("$");
		verifyListDetails(0, expectedListName1, "All items: 2 Sum: 7.4 $");
		verifyListDetails(1, expectedListName2, "All items: 3 Sum: 205.4 $");
	}

	@Test
	public void createMyListAndAddFromIt() {
		try {
			getLists().goToMyListFromMenu();
			Assert.assertEquals(getList().title().getText(), "My List", "Unexpected shopping list name");
			addProductWithPriceAmountCommentUnitCategory("prod5", "10", "10", "comment5", "pcs.", "Clothing");
			addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
			addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
			verifyProductsCount(3);
			verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
			verifyProductDetails(1, "prod2", "0.1", "10", "comment2");
			verifyProductDetails(2, "prod5", "10", "10", "comment5", "pcs.", "£");
			getDriver().hideKeyboard();
			getDriver().navigate().back();
			addList("list5");
			addProductWithPriceAmountCommentUnitCategory("prod3", "10.1", "1", "comment3", "kg.", "Dairy produce");
			addProductWithPriceAmountComment("prod4", "1.0", "2", "comment4");
			getList().addProductsFromMyList();
			getList().addProductFromMyList("prod1");
			getList().addProductFromMyList("prod2");
			getList().okMyList().click();
			verifyProductsCount(4);
			verifyProductDetails(0, "prod4", "1", "2", "comment4");
			verifyProductDetails(1, "prod1", "3.2", "2", "comment1");
			verifyProductDetails(2, "prod2", "0.1", "10", "comment2");
			verifyProductDetails(3, "prod3", "10.1", "1", "comment3", "kg.", "£");
		} finally {
			navigateToListsView();
			getLists().goToMyListFromMenu();
			getList().removeAllProducts();
		}

	}

	@Test
	public void checkSortingOrder() {

		@SuppressWarnings("serial")
		Map<String, Map<String, String>> productsData = new LinkedHashMap<String, Map<String, String>>() {
			{
				put("royal canin", new HashMap<String, String>() {
					{
						put("price", "20");
						put("amount", "2");
						put("comment", "for dog");
						put("unit", "couple");
						put("category", "Pet products");
					}
				});
				put("lemon", new HashMap<String, String>() {
					{
						put("price", "3.3");
						put("amount", "1");
						put("comment", "for tea");
						put("unit", "ten");
						put("category", "Grocery");
					}
				});
				put("apple", new HashMap<String, String>() {
					{
						put("price", "1.1");
						put("amount", "2");
						put("comment", "for breakfast");
						put("unit", "pack");
						put("category", "Grocery");
					}
				});
				put("eggs", new HashMap<String, String>() {
					{
						put("price", "10.1");
						put("amount", "1");
						put("comment", "for breakfast");
						put("unit", "box");
						put("category", "Meat");
					}
				});
			}
		};

		String expectedListName = "list3";
		addList(expectedListName);
		for (Map.Entry<String, Map<String, String>> product : productsData.entrySet()) {
			Map<String, String> params = product.getValue();
			addProductWithPriceAmountCommentUnitCategory(product.getKey(), params.get("price"), params.get("amount"),
					params.get("comment"), params.get("unit"), params.get("category"));
		}
		verifyProductsCount(4);
		// Select sorting order By category
		getList().goToMySettingsFromMenu();
		getSettings().selectSortingOrder("By category");
		Map<String, String> eggsData = productsData.get("eggs");
		verifySavedProductDetails(0, "eggs", eggsData);
		Map<String, String> lemonData = productsData.get("lemon");
		verifySavedProductDetails(1, "lemon", lemonData);
		Map<String, String> appleData = productsData.get("apple");
		verifySavedProductDetails(2, "apple", appleData);
		Map<String, String> caninData = productsData.get("royal canin");
		verifySavedProductDetails(3, "royal canin", caninData);
		// Select sorting order By alphabet
		getList().goToMySettingsFromMenu();
		getSettings().selectSortingOrder("By alphabet");
		verifySavedProductDetails(0, "apple", appleData);
		verifySavedProductDetails(1, "eggs", eggsData);
		verifySavedProductDetails(2, "lemon", lemonData);
		verifySavedProductDetails(3, "royal canin", caninData);
	}

	@Test
	public void addNewList() {
		String expectedListName = "New list";
		addList(expectedListName);
		Assert.assertEquals(getList().title().getText(), expectedListName, "Unexpected shopping list name");
	}

	@Test
	public void addSimpleProductToList() {
		String expectedListName = "list2";
		String expectedProductName = "product1";
		addList(expectedListName);
		getList().product().sendKeys(expectedProductName);
		getList().addProduct().click();
		verifyProductsCount(1);
		Assert.assertEquals(getList().getProductName(0), expectedProductName, "Wrong product name");
	}

	@Test
	public void addProductWithPriceAmountCommentToList() {
		String expectedListName = "list3";
		addList(expectedListName);
		String expectedProductName = "product2";
		String expectedPrice = "10.3";
		String expectedAmount = "3";
		String expectedComment = "some product";
		addProductWithPriceAmountComment(expectedProductName, expectedPrice, expectedAmount, expectedComment);
		verifyProductsCount(1);
		verifyProductDetails(0, expectedProductName, expectedPrice, expectedAmount, expectedComment);
	}

	@Test
	public void addMultipleProducts() {
		String expectedListName = "list4";
		addList(expectedListName);
		addProductWithPriceAmountComment("prod1", "3.2", "2", "comment1");
		addProductWithPriceAmountComment("prod2", "0.1", "10", "comment2");
		addProductWithPriceAmountComment("prod3", "0.2", "20", "comment3");
		addProductWithPriceAmountComment("prod4", "100.2", "2", "comment4");
		verifyProductsCount(4);
		verifyProductDetails(0, "prod1", "3.2", "2", "comment1");
		verifyProductDetails(1, "prod2", "0.1", "10", "comment2");
		verifyProductDetails(2, "prod3", "0.2", "20", "comment3");
		verifyProductDetails(3, "prod4", "100.2", "2", "comment4");
	}

	private void verifySavedProductDetails(int index, String name, Map<String, String> productData) {
		verifyProductDetails(index, name, productData.get("price"), productData.get("amount"),
				productData.get("comment"), productData.get("unit"), "£");
	}

}
