package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MyInfoPage extends BasePage {

    private final Locator myInfoMenu;
    private final Locator personalDetailHeader;

    public MyInfoPage(Page page) {
        super(page);
        this.myInfoMenu = page.getByText("My Info");
        this.personalDetailHeader = page.locator("h6:has-text('Personal Details')");
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
}