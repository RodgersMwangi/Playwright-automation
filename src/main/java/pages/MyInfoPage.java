package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MyInfoPage extends BasePage {

    private final Locator myInfoMenu;
    private final Locator personalDetailHeader;
    private final Locator firstNameInput;
    private final Locator middleNameInput;
    private final Locator lastNameInput;
    private final Locator personalDetailsSaveButton;
    private final Locator successMessage;
    private final Locator requiredValidationMessage;
    private final Locator employeeIdInput;
    private final Locator otherIdInput;

    public MyInfoPage(Page page) {
        super(page);
        this.myInfoMenu = page.getByText("My Info");
        this.personalDetailHeader = page.locator("h6:has-text('Personal Details')");
        this.firstNameInput = page.getByPlaceholder("First Name");
        this.middleNameInput = page.getByPlaceholder("Middle Name");
        this.lastNameInput = page.getByPlaceholder("Last Name");
        this.personalDetailsSaveButton = page.locator("button[type='submit']:has-text('Save')").first();
        this.successMessage = page.locator(".oxd-toast").filter(
                new Locator.FilterOptions().setHasText("Successfully Updated"));

        this.requiredValidationMessage = page.locator(".oxd-input-field-error-message:has-text('Required')");
        this.employeeIdInput = page.getByRole(AriaRole.TEXTBOX).nth(4);
        this.otherIdInput = page.getByRole(AriaRole.TEXTBOX).nth(5);
    }

    public void clickMyInfoMenu() {

        myInfoMenu.click();
    }

    public boolean isPersonDetailHeaderVisible() {
        personalDetailHeader.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        return personalDetailHeader.isVisible();
    }

    public void updateFullName(String firstName, String middleName, String lastName) {
        firstNameInput.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );
        firstNameInput.click();
        firstNameInput.press("Control+A");
        firstNameInput.fill(firstName);

        middleNameInput.click();
        middleNameInput.press("Control+A");
        middleNameInput.fill(middleName);

        lastNameInput.click();
        lastNameInput.press("Control+A");
        lastNameInput.fill(lastName);
    }

    public void clearRequiredFullNameFields() {
        firstNameInput.click();
        firstNameInput.press("Control+A");
        firstNameInput.press("Backspace");

        lastNameInput.click();
        lastNameInput.press("Control+A");
        lastNameInput.press("Backspace");
    }

    public boolean areRequiredValidationMessagesVisible() {
        requiredValidationMessage.first().waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );
        return requiredValidationMessage.count() >= 2;
    }

    public void clickPersonalDetailsSaveButton() {
        personalDetailsSaveButton.click();
    }

    public boolean isSuccessMessageVisible() {
        successMessage.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );
        return successMessage.isVisible();
    }

    public String getFirstNameValue() {
        return firstNameInput.inputValue();
    }

    public String getMiddleNameValue() {
        return middleNameInput.inputValue();
    }

    public String getLastNameValue() {
        return lastNameInput.inputValue();
    }

    public void updateEmployeeIdFields(String employeeId, String otherId) {
        employeeIdInput.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(10000)
        );

        employeeIdInput.click();
        employeeIdInput.press("Control+A");
        employeeIdInput.fill(employeeId);

        otherIdInput.click();
        otherIdInput.press("Control+A");
        otherIdInput.fill(otherId);
    }

    public String getEmployeeIdValue() {
        return employeeIdInput.inputValue();
    }

    public String getOtherIdValue() {
        return otherIdInput.inputValue();
    }
}