package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ChangeAdminPasswordPage extends BasePage {

    private final Locator userDropdown;
    private final Locator changePasswordMenu;
    private final Locator currentPasswordInput;
    private final Locator newPasswordInput;
    private final Locator confirmPasswordInput;
    private final Locator saveButton;
    private final Locator successMessage;
    private final Locator passwordMismatchError;

    public ChangeAdminPasswordPage(Page page) {
        super(page);

        this.userDropdown = page.locator(".oxd-userdropdown-name");

        this.changePasswordMenu = page.locator("a:has-text('Change Password')");

        this.currentPasswordInput = page.locator("input[type='password']").nth(0);

        this.newPasswordInput = page.locator("input[type='password']").nth(1);

        this.confirmPasswordInput = page.locator("input[type='password']").nth(2);

        this.saveButton = page.locator("button:has-text('Save')");

        this.successMessage = page.locator(".oxd-toast")
                .filter(new Locator.FilterOptions()
                        .setHasText("Successfully Updated"));
        this.passwordMismatchError =
                page.locator("span:has-text('Passwords do not match')");
    }

    public boolean isDashboardPageVisible() {

        assertThat(
                page.locator("h6.oxd-topbar-header-breadcrumb-module:has-text('Dashboard')")
        ).isVisible(
                new LocatorAssertions.IsVisibleOptions()
                        .setTimeout(10000)
        );

        return true;
    }

    public void openUserDropdown() {
        userDropdown.click();
    }

    public void clickChangePassword() {
        changePasswordMenu.click();
    }

    public void navigateToChangePassword() {
        openUserDropdown();
        clickChangePassword();
    }

    public void changePassword(String currentPwd, String newPwd) {

        currentPasswordInput.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        currentPasswordInput.fill(currentPwd);

        newPasswordInput.fill(newPwd);

        confirmPasswordInput.fill(newPwd);

        saveButton.click();
    }

    public boolean isSuccessMessageVisible() {

        successMessage.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        return successMessage.isVisible();
    }

    public boolean isRequiredFieldErrorVisible() {

        var error = page.locator("span:has-text('Required')").first();

        error.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        return error.isVisible();
    }

    public boolean isPasswordMismatchErrorVisible() {
        var error = page.locator("span:has-text('Passwords do not match')");
        error.waitFor();
        return error.isVisible();
    }

    public boolean isCurrentPasswordErrorVisible() {
        var error = page.locator("p:has-text('Current Password')");
        error.waitFor();
        return error.isVisible();
    }

    public boolean isPasswordPolicyErrorVisible() {
        var error = page.locator("span:has-text('Should have at least')");
        error.waitFor();
        return error.isVisible();
    }

    public void changePasswordMismatch(String currentPwd,
                                       String newPwd,
                                       String confirmPwd) {

        currentPasswordInput.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        currentPasswordInput.fill(currentPwd);

        newPasswordInput.fill(newPwd);

        confirmPasswordInput.fill(confirmPwd);

        saveButton.click();
    }


}