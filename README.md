The set of smoke test cases for testing Shopping list-1.6.apk app.

Test plan:
1. Create shopping lists/add products scenarios:
  - Create new shopping list, verify that it's saved.
  - Create new shopping list, add product to it, check that product's data is saved.
  - Create new shopping list, add product with custom unit and category to it, check that product's data is saved.
  - Create new shopping list, add several products with different properties, check that products data is saved.
  - Create 2 shopping lists, add products to them, check their content from Shopping Lists page.
  - Add products to My List, create shopping list and add products from My List to it. Verify the products data.
  - Create shopping list, add several products to it, change sorting order and currency and verify that these settings are applied properly.
2. Edit shopping lists/products scenarios:
  - Create shopping list and change it's name, verify that the data is updated.
  - Create shopping list, add products to it and change their properties, verify that the data is updated.
  - Add products to My List, change their properties, verify that the data is updated.
3. Delete shopping lists/products scenarios:
  - Create shopping lists and delete them, verify that they are removed.
  - Create shopping list, add products to it then remove them, verify that the data is removed.
  - Add products to My List then remove them, verify that the data is removed.
