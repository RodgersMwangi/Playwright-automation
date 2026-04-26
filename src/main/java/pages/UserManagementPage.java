package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class UserManagementPage extends BasePage {
    private final Locator userRoleDropDown;
    private final Locator statusDropDown;
    private final Locator employeeName;
    private final Locator userName;
    private final Locator password;
    private final Locator confirmPassword;
    private final Locator savebtn;
    private final Locator addbtn;

    public UserManagementPage(Page page){
        super(page);
        this.addbtn=page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add"));
        this.userRoleDropDown=page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText("User Role"))
                .locator(".oxd-select-text");
        this.statusDropDown=page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText("Status"))
                .locator(".oxd-select-text");
        this.employeeName= page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type for hints..."));
        this.userName=page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText("Username"))
                .locator("input");
        this.password=page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Password$")))
                .locator("input");
        this.confirmPassword=page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText(Pattern.compile("^Confirm Password$")))
                .locator("input[type='password']");
        this.savebtn=page.locator("button[type='submit']");



    }

    public void fillDetails(String userRole, String employeeNameHint, String employee_Name, String status, String _username, String pass_word){
        addbtn.click();
        userRoleDropDown.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(userRole)).click();
        employeeName.click();
        employeeName.fill(employeeNameHint);
        page.getByText(employee_Name).click();
        statusDropDown.click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(status)).click();
        userName.fill(_username);
        password.fill(pass_word);
        confirmPassword.fill(pass_word);
        savebtn.click();
        //page.pause();
    }

}
