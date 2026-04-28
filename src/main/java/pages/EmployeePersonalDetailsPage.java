package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class EmployeePersonalDetailsPage extends BasePage {

    private final Locator firstNameField;
    private final Locator middleNameField;
    private final Locator lastNameField;
    private final Locator nicknameField;
    private final Locator dobField;
    private final Locator savePersonalDetailsBtn;
    private final Locator successToast;

    public EmployeePersonalDetailsPage(Page page) {
        super(page);
        // Name fields — these are positional inside the name widget
        this.firstNameField  = page.locator("input[name='firstName']");
        this.middleNameField = page.locator("input[name='middleName']");
        this.lastNameField   = page.locator("input[name='lastName']");

        // Nickname — use index-based approach, it's the 4th input on the page
        this.nicknameField   = page.locator(".oxd-form .oxd-input").nth(3);

        // Date of Birth — target the input inside the date row
        this.dobField        = page.locator(
                ".orangehrm-employee-container input.oxd-input").nth(1);

        // Save button — first submit button under Personal Details section
        this.savePersonalDetailsBtn = page.locator(
                ".orangehrm-employee-container button[type='submit']").first();

        this.successToast = page.locator(".oxd-toast--success");
    }

    public void editFirstName(String firstName) {
        firstNameField.clear();
        firstNameField.fill(firstName);
    }

    public void editMiddleName(String middleName) {
        middleNameField.clear();
        middleNameField.fill(middleName);
    }

    public void editLastName(String lastName) {
        lastNameField.clear();
        lastNameField.fill(lastName);
    }

    public void editNickname(String nickname) {
        nicknameField.clear();
        nicknameField.fill(nickname);
    }

    public void editDateOfBirth(String dob) {
        dobField.clear();
        dobField.fill(dob);
        // Click away to trigger validation and close any date picker
        firstNameField.click();
    }

    public void savePersonalDetails() {
        savePersonalDetailsBtn.click();
    }

    public void editAllPersonalDetails(String firstName, String middleName,
                                       String lastName, String nickname, String dob) {
        editFirstName(firstName);
        editMiddleName(middleName);
        editLastName(lastName);
        editNickname(nickname);
        editDateOfBirth(dob);
        savePersonalDetails();
    }

    public boolean isSuccessToastVisible() {
        page.waitForSelector(".oxd-toast--success");
        return successToast.isVisible();
    }

    // Get the required field error message — OrangeHRM uses this span class
    public String getValidationError() {
        page.waitForSelector("span.oxd-text--span");
        return page.locator("span.oxd-text--span").first().textContent().trim();
    }

    public String getFirstNameValue() { return firstNameField.inputValue(); }
    public String getLastNameValue()  { return lastNameField.inputValue(); }
}