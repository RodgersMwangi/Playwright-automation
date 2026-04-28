package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ChangeAdminPasswordPage;
import pages.LoginPage;
import util.ConfigReader;

public class ChangeAdminPassTest extends BaseTest {

    ConfigReader configReader = ConfigReader.getInstance();

    private ChangeAdminPasswordPage changeAdminPasswordPage;

    @BeforeMethod
    public void setupChangePasswordPage() {

        LoginPage loginPage = new LoginPage(page);

        // Login
        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")
        );

        // Navigate to Change Password page
        changeAdminPasswordPage = new ChangeAdminPasswordPage(page);

        changeAdminPasswordPage.openUserDropdown();
        changeAdminPasswordPage.clickChangePassword();
    }

    @Test
    public void changeAdminPasswordTest() {

        changeAdminPasswordPage.changePassword(
                configReader.getProperty("admin.password"),
                configReader.getProperty("admin.newPassword")
        );

        Assert.assertTrue(
                changeAdminPasswordPage.isDashboardPageVisible(),
                "Dashboard page not displayed after password change"
        );
    }

    @Test
    public void changePasswordWithWrongCurrentPassword() {

        changeAdminPasswordPage.changePassword(
                configReader.getProperty("invalid.current.password"),
                configReader.getProperty("admin.newPassword")
        );

        Assert.assertTrue(
                changeAdminPasswordPage.isCurrentPasswordErrorVisible(),
                "Expected current password error not displayed"
        );
    }

    @Test
    public void changePasswordWithMismatchedPasswords() {

        changeAdminPasswordPage.changePasswordMismatch(
                configReader.getProperty("admin.password"),
                configReader.getProperty("mismatch.new.password"),
                configReader.getProperty("mismatch.confirm.password")
        );

        Assert.assertTrue(
                changeAdminPasswordPage.isPasswordMismatchErrorVisible(),
                "Password mismatch validation not displayed"
        );
    }

    @Test
    public void changePasswordWithEmptyFields() {

        changeAdminPasswordPage.changePassword(
                configReader.getProperty("empty.value"),
                configReader.getProperty("empty.value")
        );

        Assert.assertTrue(
                changeAdminPasswordPage.isRequiredFieldErrorVisible(),
                "Required field validation not displayed"
        );
    }

    @Test
    public void changePasswordWithWeakPassword() {

        changeAdminPasswordPage.changePassword(
                configReader.getProperty("admin.password"),
                configReader.getProperty("weak.password")
        );

        Assert.assertTrue(
                changeAdminPasswordPage.isPasswordPolicyErrorVisible(),
                "Weak password validation not displayed"
        );
    }
}