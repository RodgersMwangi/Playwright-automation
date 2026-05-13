package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ChangeAdminPasswordPage;
import pages.LoginPage;
import util.ConfigReader;


@Listeners(listeners.TestListener.class)
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

    @Test(description = "TC_J_01 - Validate successful password change with valid current and new password",
            groups = {"positive", "regression", "smoke"})
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

    @Test(description = "TC_J_02 - Validate error message when incorrect current password is provided during password change",
            groups = {"negative", "regression"})
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

    @Test( description = "TC_J_03 - Validate error message when new password and confirm password do not match", groups = {"negative", "regression"})
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

    @Test(description = "TC_J_04 - Validate required field errors when password change form is submitted with empty inputs", groups = {"negative", "regression"})
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

    @Test(description = "TC_J_05 - Verify that system shows validation error when user enters a weak password during password change", groups = {"negative", "regression"})
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