import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyInfoPage;
import util.ConfigReader;

public class ChangeAdminPassTest extends BaseTest {
    ConfigReader configReader = ConfigReader.getInstance();


    @Test
    public void changeAdminPasswordTest() {

        LoginPage loginPage = new LoginPage(page);

        // Step 1: Login
        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        MyInfoPage myInfoPage = new MyInfoPage(page);

        // Step 2: Open dropdown → Change Password
        myInfoPage.openUserDropdown();
        myInfoPage.clickChangePassword();

        // Step 3: Change password
        myInfoPage.changePassword(
                configReader.getProperty("admin.password"),
                configReader.getProperty("admin.newPassword")
        );

        // Step 4: Assertion
        Assert.assertTrue(
                myInfoPage.issuccessMessageVisible(),
                "Password change failed!"
        );
    }
}