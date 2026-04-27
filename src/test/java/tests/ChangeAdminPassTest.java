import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyInfoPage;
import util.ConfigReader;

public class ChangeAdminPassTest extends BaseTest {

    @Test
    public void changeAdminPasswordTest() {

        LoginPage loginPage = new LoginPage(page);

        // Step 1: Login
        loginPage.userLogin(
                ConfigReader.get("admin.username"),
                ConfigReader.get("admin.password")
        );

        MyInfoPage myInfoPage = new MyInfoPage(page);

        // Step 2: Open dropdown → Change Password
        myInfoPage.openUserDropdown();
        myInfoPage.clickChangePassword();

        // Step 3: Change password
        myInfoPage.changePassword(
                ConfigReader.get("admin.password"),
                ConfigReader.get("admin.newPassword")
        );

        // Step 4: Assertion
        Assert.assertTrue(
                myInfoPage.isSuccessMessageVisible(),
                "Password change failed!"
        );
    }
}