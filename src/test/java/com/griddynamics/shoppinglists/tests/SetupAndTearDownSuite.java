package com.griddynamics.shoppinglists.tests;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.griddynamics.shoppinglists.setup.WebDriverProvider;
import com.griddynamics.shoppinglists.steps.ShoppingListsSteps;
import com.griddynamics.shoppinglists.utils.ScreenshotUtility;

@Listeners({ ScreenshotUtility.class })
@ContextConfiguration(locations = {"classpath:**/spring-test-config.xml"})
/**
 * Class with methods for preparing driver before the test and quitting after the test.
 * @author ybaturina
 *
 */
public class SetupAndTearDownSuite extends AbstractTestNGSpringContextTests {
	
	@Autowired
	protected ShoppingListsSteps steps;
	
	@Autowired
	protected WebDriverProvider driverProvider;
	
	@BeforeSuite
	public void start() throws IOException, InterruptedException {
		try {
			springTestContextPrepareTestInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driverProvider.start();
	}
	
	@BeforeMethod
	public void reset() {
		steps.navigateToListsView();
		steps.getLists().removeAllLists();
		steps.getList().goToMySettingsFromMenu();
		steps.getSettings().applyDefaultSortAndCurrency();
	}

	@AfterSuite
	public void stop() {
		driverProvider.stop();
	}

}
