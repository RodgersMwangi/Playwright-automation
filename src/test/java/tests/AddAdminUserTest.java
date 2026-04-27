package tests;

import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import pages.UserManagementPage;
import util.ConfigReader;
import util.DataFaker;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddAdminUserTest extends BaseTest {
    ConfigReader configReader =ConfigReader.getInstance();
    private Locator btnAdminModule;
    String userRole=configReader.getProperty("userRole.dropdown");
    String employeeFirstName=DataFaker.FAKER.name().firstName();
    String employeeLastName=DataFaker.FAKER.name().lastName();
    String employeeNameHint=employeeFirstName;
    String employee_name = employeeFirstName + " " + employeeLastName;
    String employeeCreationID = DataFaker.FAKER.number().digits(6);
    String statusDropdown=configReader.getProperty("adduser.status.dropdown");
    String userName= DataFaker.FAKER.name().firstName();;
    String existingUserName=configReader.getProperty("existingEmployee");
    String userPassword=DataFaker.FAKER.regexify("[A-Z]{1}[a-z]{5}[0-9]{2}[@#$%]{1}");;
    LoginPage loginPage;

    @BeforeMethod
    public void initializePage(){
        loginPage=new LoginPage(page);
    }

    public void loggingIn(){
        btnAdminModule=page.locator("a[href*='viewAdminModule']");
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));
        assertThat(page.locator("h6.oxd-topbar-header-breadcrumb-module:has-text('Dashboard')")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));

    }

    public void adminCreation(String user_Name){


        UserManagementPage userManagementPage=new UserManagementPage(page);
        //page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Personal Details")).waitFor();

        btnAdminModule.click();
        //page.pause();
        System.out.println(employee_name);
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("User Management"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        userManagementPage.fillDetails(userRole, employeeNameHint, employee_name, statusDropdown,user_Name, userPassword);
    }

    public void employeeCreation(){
        DashboardPage dashboardPage=new DashboardPage(page);
        PIMPage pimPage=dashboardPage.navigateToPIM();
        pimPage.saveEmployeeDetails(employeeFirstName, "", employeeLastName, employeeCreationID);

        assertThat(page.getByText(employeeFirstName)).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(15000));;
    }

    @Test
    public void addAdminUser(){ //Test 1: adding a new admin user
        loggingIn();
        employeeCreation();
        adminCreation(userName);
        try {
            page.getByText("exists").waitFor(new Locator.WaitForOptions().setTimeout(10000));
            System.out.println("User " + userName + " already exists");
        } catch (com.microsoft.playwright.TimeoutError e) {
            // If it doesn't appear, just continue
        }
        assertThat(page.getByText(userName)).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));

    }

    @Test
    public void userAlreadyExist(){
        //Test 2: tests an existing user
        loggingIn();
        employeeCreation();
        adminCreation(existingUserName);
        assertThat(page.getByText("exists")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

}
