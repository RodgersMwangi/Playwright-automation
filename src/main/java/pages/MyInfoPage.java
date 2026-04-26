package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
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

    public MyInfoPage(Page page) {
        super(page);
        this.myInfoMenu = page.getByText("My Info");
        this.personalDetailHeader = page.locator("h6:has-text('Personal Details')");
        // Scope the full name inputs to the Personal Details section to avoid matching other inputs on the page
        Locator personalDetailsSection = page.locator("div:has(h6:has-text('Personal Details'))");
        this.firstNameInput = personalDetailsSection.getByPlaceholder("First Name");
        this.middleNameInput = personalDetailsSection.getByPlaceholder("Middle Name");
        this.lastNameInput = personalDetailsSection.getByPlaceholder("Last Name");
        this.personalDetailsSaveButton = personalDetailsSection.locator("button[type='submit']:has-text('Save')").first();
        this.successMessage = page.locator(".oxd-toast").filter(
                new Locator.FilterOptions().setHasText("Successfully Updated"));

        this.requiredValidationMessage = personalDetailsSection.locator(".oxd-input-field-error-message:has-text('Required')");
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

        firstNameInput.press("Control+A");
        firstNameInput.fill(firstName);

        middleNameInput.press("Control+A");
        middleNameInput.fill(middleName);

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
}