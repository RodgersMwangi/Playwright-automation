package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import net.datafaker.Faker;

public class PIMPage extends BasePage {
    private final Locator PIMHeader;
    private final Locator txtFirstName;
    private final Locator txtMiddleName;
    private final Locator txtLastName;
    private final Locator strEmployeeId;
    private final Locator btnSave;
    private final Locator employeeListLink;
    //private final Locator errorMessage;
    private final Locator btnAddEmployee;


    public PIMPage(Page page) {
        super(page);
        this.PIMHeader=page.getByRole(AriaRole.HEADING,new Page.GetByRoleOptions().setName("PIM"));
        this.txtFirstName =page.locator("input[name='firstName']");
        this.txtMiddleName = page.getByPlaceholder("Middle Name");
        this.txtLastName = page.getByPlaceholder("Last Name");
        this.strEmployeeId = page.locator("//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']");
        this.btnSave = page.locator("button[type='submit']");
        this.employeeListLink=page.getByRole(AriaRole.LINK,new Page.GetByRoleOptions().setName("Employee List"));
        //this.errorMessage=page.getByText("Required", new Page.GetByTextOptions().setExact(true));
        this.btnAddEmployee=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add"));
    }

    public boolean isPIMPageAvailable(){
        PIMHeader.waitFor();
        return PIMHeader.isVisible();
    }

    public void clickAddButton(){
        btnAddEmployee.click();
    }

    public void enterFirstName(String firstName){
        txtFirstName.fill(firstName);
    }

    public void enterMiddleName(String middleName){
        txtMiddleName.fill(middleName);
    }

    public void enterLastName(String lastName){
        txtLastName.fill(lastName);
    }

    public void enterEmployeeID(String id){
        strEmployeeId.fill(id);
    }

    public void clickSaveButton(){
        btnSave.click();
    }

    public Locator getRequiredError() {
        return page.getByText(
                "Required",
                new Page.GetByTextOptions().setExact(true)
        );
    }

    public void saveEmployeeDetails(String firstName, String middleName, String lastName,String id){
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterEmployeeID(id);
        clickSaveButton();
    }




    public void saveEmployeeDetails(String firstName, String middleName, String id){
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterEmployeeID(id);
        clickSaveButton();
    }

    public void saveEmployeeDetails(String middleName, String id){
        enterMiddleName(middleName);
        enterEmployeeID(id);
        clickSaveButton();
    }
}
