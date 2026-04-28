package base;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EmployeePersonalDetailsPage;
import pages.LoginPage;

public class EditEmployeeTest extends BaseTest {

    @BeforeMethod
    public void loginAndGoToEmployee() {
        // Login
        LoginPage loginPage = new LoginPage(page);
        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        // Navigate to Employee List
        page.waitForURL("**/dashboard**");
        page.navigate(configReader.getProperty("employeeList.url"));
        page.waitForSelector(".oxd-table-body .oxd-table-row");
        System.out.println("✅ Navigated to Employee List");

        // Click the edit pencil icon of the first employee
        page.locator(".oxd-table-body .oxd-table-row")
                .nth(0)
                .locator("button.oxd-icon-button")
                .nth(1)
                .click();

        // Wait until Personal Details page is fully loaded
        page.waitForURL("**/viewPersonalDetails/**");
        page.waitForSelector("input[name='firstName']");
        System.out.println("✅ Opened Employee Personal Details page");
    }

    // ✅ Test 1: Edit with valid data
    @Test
    public void testEditEmployeePersonalDetailsValid() {
        EmployeePersonalDetailsPage empPage =
                new EmployeePersonalDetailsPage(page);

        empPage.editFirstName(configReader.getProperty("employee.edit.firstname"));
        empPage.editMiddleName(configReader.getProperty("employee.edit.middlename"));
        empPage.editLastName(configReader.getProperty("employee.edit.lastname"));
        empPage.savePersonalDetails();

        Assert.assertTrue(empPage.isSuccessToastVisible(),
                "Expected success toast after saving personal details");

        System.out.println("🎉 Valid edit test PASSED!");
    }

    // ❌ Test 2: Empty first name
    @Test
    public void testEditEmployeeEmptyFirstName() {
        EmployeePersonalDetailsPage empPage =
                new EmployeePersonalDetailsPage(page);

        // Triple-click selects all text, then fill with empty replaces it
        page.locator("input[name='firstName']").dblclick();
        page.locator("input[name='firstName']").fill("");
        empPage.savePersonalDetails();
        System.out.println("✅ Submitted with empty first name");

        // OrangeHRM shows required errors as "span.oxd-text--span" inside form groups
        page.waitForSelector(".oxd-form-row .oxd-text--span");
        String error = page.locator(".oxd-form-row .oxd-text--span")
                .first().textContent().trim();
        System.out.println("✅ Validation error: " + error);

        Assert.assertTrue(error.contains("Required"),
                "Expected 'Required' error but got: " + error);

        System.out.println("🎉 Empty first name negative test PASSED!");
    }

    // ❌ Test 3: Empty last name
    @Test
    public void testEditEmployeeEmptyLastName() {
        EmployeePersonalDetailsPage empPage =
                new EmployeePersonalDetailsPage(page);

        page.locator("input[name='lastName']").dblclick();
        page.locator("input[name='lastName']").fill("");
        empPage.savePersonalDetails();
        System.out.println("✅ Submitted with empty last name");

        page.waitForSelector(".oxd-form-row .oxd-text--span");
        String error = page.locator(".oxd-form-row .oxd-text--span")
                .first().textContent().trim();
        System.out.println("✅ Validation error: " + error);

        Assert.assertTrue(error.contains("Required"),
                "Expected 'Required' error but got: " + error);

        System.out.println("🎉 Empty last name negative test PASSED!");
    }
}