package tests;

import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.LoginPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Epic("OrangeHRM")
@Feature("Login")
public class LoginTest extends BaseTest {

    ConfigReader configReader = ConfigReader.getInstance();

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that the Admin user can login with valid credentials and is redirected to the Dashboard")
    public void testWithValidCredentials() {
        Allure.step("Login with valid credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            loginPage.userLogin(
                    configReader.getProperty("admin.username"),
                    configReader.getProperty("admin.password")
            );
        });

        Allure.step("Verify Dashboard is displayed", () ->
                assertThat(page.locator(
                        "h6.oxd-topbar-header-breadcrumb-module:has-text('Dashboard')"))
                        .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000))
        );
    }

    @Test
    @Story("Negative Tests")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an error message is displayed when logging in with invalid credentials")
    public void testWithInvalidCredentials() {
        Allure.step("Login with invalid credentials", () -> {
            LoginPage loginPage = new LoginPage(page);
            loginPage.userLogin("Admin", "admin");
        });

        Allure.step("Verify error message is displayed", () ->
                // OrangeHRM wraps the error inside this specific element
                assertThat(page.locator(".oxd-alert-content-text"))
                        .isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000))
        );
    }
}



//mvn test -Dtest=SampleTest#testWithInvalidCredentials -Dbrowser=webkit