import base.BaseTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.UserManagementPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddAdminUserTest extends BaseTest {
    ConfigReader configReader =ConfigReader.getInstance();
    private Locator btnAdminModule;
    String userRole=configReader.getProperty("userRole.dropdown");
    String employee_name= configReader.getProperty("employee.name");
    String employeeNameHint=configReader.getProperty("employeeName.hint");
    String statusDropdown=configReader.getProperty("adduser.status.dropdown");
    String userName=configReader.getProperty("adduser.username");
    String existingUserName=configReader.getProperty("existingEmployee");
    String userPassword=configReader.getProperty("adduser.password");


    public void adminCreation(String user_Name){
        LoginPage loginPage=new LoginPage(page);
        UserManagementPage userManagementPage=new UserManagementPage(page);
        btnAdminModule=page.locator("a[href*='viewAdminModule']");
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));
        assertThat(page.locator("h6.oxd-topbar-header-breadcrumb-module:has-text('Dashboard')")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        btnAdminModule.click();
        //page.pause();
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("User Management"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
        userManagementPage.fillDetails(userRole, employeeNameHint, employee_name, statusDropdown,user_Name, userPassword);
    }
    @Test
    public void addAdminUser(){ //Test 1: adding a new admin user
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
        adminCreation(existingUserName);
        assertThat(page.getByText("exists")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

}
