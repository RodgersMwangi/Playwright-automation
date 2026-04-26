package locators;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

public class EditPIMLocators extends BasePage {
    public Locator editPIM;
    public Locator editPIMButton;
    public Locator editPIMContactTab;
    public Locator editPIMStreet1;

    public EditPIMLocators(Page page) {
        super(page);
        this.editPIM = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("PIM"));
        this.editPIMButton = page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText(Pattern.compile("^$"))).nth(3);
        this.editPIMContactTab = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Contact Details"));
        this.editPIMStreet1 = page.getByRole(AriaRole.TEXTBOX).nth(1);

    }


}