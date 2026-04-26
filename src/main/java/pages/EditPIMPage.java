package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import locators.EditPIMLocators;
import util.ConfigReader;

public class EditPIMPage extends EditPIMLocators {
    ConfigReader configReader = ConfigReader.getInstance();
    public EditPIMPage(Page page) {
        super(page);
    }

    public void editContactDetails() {
        editPIM.click();
        editPIMButton.click();
        editPIMContactTab.click();
        editPIMStreet1.fill((configReader.getProperty("street1")));

    }
}
