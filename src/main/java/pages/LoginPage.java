package pages;

import base.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

    private final Locator txtUsername;
    private final Locator txtPassword;
    private final Locator btnLogin;

    public LoginPage(Page page) {
        super(page);
        this.txtUsername=page.getByPlaceholder("Username"); //diff elements cld share the same placeholder so this is not very accurate at times
        this.txtPassword=page.locator("input[name='password']");
        this.btnLogin=page.locator("button[type='submit']");
    }

    public void enterUsername(String username){
        txtUsername.fill(username);
    }
    public void enterPassword(String password){
        txtPassword.fill(password);
    }
    public void clickBtnLogin(){
        btnLogin.click();
    }

    public void userLogin(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickBtnLogin();
    }
}
