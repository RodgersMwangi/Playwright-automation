import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyInfoPage;
import util.ConfigReader;
import util.DataFaker;
import java.util.concurrent.ThreadLocalRandom;

public class MyInfoTest extends BaseTest {

    ConfigReader configReader = ConfigReader.getInstance();


    // TC-001: Verify Admin user can navigate to My info Page
    @Test(description = "TC-001: Verify My Info page is displayed")
    public void myInfoPageDisplayVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);

        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );
        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-001] Login failed. Current URL: " + page.url()
        );

        myInfoPage.clickMyInfoMenu();
        page.waitForURL("**/viewPersonalDetails/**");

        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-001] My Info page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-001] Personal Details header is not visible"
        );

        System.out.println("[TC-001] My Info page displayed successfully. URL: " + page.url());
    }

    // TC-002: Verify Admin user can update employee full name - using Faker for realistic names
    @Test(description = "TC-002: Verify Admin can update employee full name")
    public void updateEmployeeFullNameVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);

        // Faker for realistic names
        String firstName = DataFaker.FAKER.name().firstName();
        String middleName = DataFaker.FAKER.name().firstName();
        String lastName = DataFaker.FAKER.name().lastName();

        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-002] Login failed. Current URL: " + page.url()
        );

        myInfoPage.clickMyInfoMenu();
        page.waitForURL("**/viewPersonalDetails/**");

        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-002] My Info Personal Details page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-002] Personal Details header is not visible"
        );

        myInfoPage.updateFullName(firstName, middleName, lastName);
        myInfoPage.clickPersonalDetailsSaveButton();

        Assert.assertTrue(
                myInfoPage.isSuccessMessageVisible(),
                "[TC-002] Success message was not displayed after saving full name"
        );
        Assert.assertEquals(
                myInfoPage.getFirstNameValue(),
                firstName,
                "[TC-002] First name was not updated correctly"
        );
        Assert.assertEquals(
                myInfoPage.getMiddleNameValue(),
                middleName,
                "[TC-002] Middle name was not updated correctly"
        );
        Assert.assertEquals(
                myInfoPage.getLastNameValue(),
                lastName,
                "[TC-002] Last name was not updated correctly"
        );

        System.out.println("[TC-002] Employee full name updated successfully: "
                + firstName + " " + middleName + " " + lastName);
    }

    // TC-003: Verify required validation appears when required full name fields are empty
    @Test(description = "TC-003: Verify required validation for empty full name fields")
    public void requiredValidationForEmptyFullNameFieldsVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);

        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-003] Login failed. Current URL: " + page.url()
        );

        myInfoPage.clickMyInfoMenu();
        page.waitForURL("**/viewPersonalDetails/**"); // Fixed - removed /pim/ prefix
        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-003] My Info Personal Details page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-003] Personal Details header is not visible"
        );

        myInfoPage.clearRequiredFullNameFields();
        myInfoPage.clickPersonalDetailsSaveButton();

        Assert.assertTrue(
                myInfoPage.areRequiredValidationMessagesVisible(),
                "[TC-003] Required validation messages were not displayed for empty full name fields"
        );

        System.out.println("[TC-003] Required validation messages displayed correctly for empty full name fields.");
    }

    // TC-004: Verify Admin user can update Employee Id and Other Id - using ThreadLocalRandom for unique IDs
    @Test(description = "TC-004: Verify Admin can update Employee Id and Other Id")
    public void updateEmployeeIdAndOtherIdVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);

        // ThreadLocalRandom for IDs
        int randomNumber = ThreadLocalRandom.current().nextInt(1000, 9999);
        String employeeId = "EMP" + randomNumber;
        String otherId = "OTH" + randomNumber;

        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-004] Login failed. Current URL: " + page.url()
        );

        myInfoPage.clickMyInfoMenu();
        page.waitForURL("**/viewPersonalDetails/**");
        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-004] My Info Personal Details page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-004] Personal Details header is not visible"
        );

        myInfoPage.updateEmployeeIdFields(employeeId, otherId);
        myInfoPage.clickPersonalDetailsSaveButton();

        Assert.assertTrue(
                myInfoPage.isSuccessMessageVisible(),
                "[TC-004] Success message was not displayed after saving Employee Id fields"
        );
        Assert.assertEquals(
                myInfoPage.getEmployeeIdValue(),
                employeeId,
                "[TC-004] Employee Id was not updated correctly"
        );
        Assert.assertEquals(
                myInfoPage.getOtherIdValue(),
                otherId,
                "[TC-004] Other Id was not updated correctly"
        );

        System.out.println("[TC-004] Employee Id and Other Id updated successfully: "
                + employeeId + " / " + otherId);
    }

    // TC-005: Verify Admin user can update Driver's License Number and License Expiry Date
    @Test(description = "TC-005: Verify Admin can update Driver's License Number and License Expiry Date")
    public void updateDriverLicenseAndExpiryDateVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);


        String licenseNumber = "DL" + DataFaker.FAKER.regexify("[0-9]{7}");


        String expiryDate = DataFaker.FAKER.regexify("202[7-9]-(0[1-9]|[12][0-9]|30)-(0[1-9]|1[0-2])");

        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-005] Login failed. Current URL: " + page.url()
        );

        myInfoPage.clickMyInfoMenu();
        page.waitForURL("**/viewPersonalDetails/**");

        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-005] My Info Personal Details page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-005] Personal Details header is not visible"
        );

        myInfoPage.updateDriverLicenseFields(licenseNumber, expiryDate);
        myInfoPage.clickPersonalDetailsSaveButton();

        Assert.assertTrue(
                myInfoPage.isSuccessMessageVisible(),
                "[TC-005] Success message was not displayed after saving Driver's License fields"
        );
        Assert.assertEquals(
                myInfoPage.getDriverLicenseValue(),
                licenseNumber,
                "[TC-005] Driver's License Number was not updated correctly"
        );
        Assert.assertEquals(
                myInfoPage.getLicenseExpiryDateValue(),
                expiryDate,
                "[TC-005] License Expiry Date was not updated correctly"
        );

        System.out.println("[TC-005] Driver's License and Expiry Date updated successfully: "
                + licenseNumber + " / " + expiryDate);
    }
}