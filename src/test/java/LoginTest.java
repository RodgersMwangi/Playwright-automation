import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.testng.annotations.Test;
import pages.LoginPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();

    @Test
    public void testWithValidCredentials(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        assertThat(page.locator("h6.oxd-topbar-header-breadcrumb-module:has-text('Dashboard')")).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

    @Test
    public void testWithInvalidCredentials(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin("Admin", "admin");
        assertThat(page.getByText("Invalid credentials")).isVisible();
    }
}



//mvn test -Dtest=SampleTest#testWithInvalidCredentials -Dbrowser=webkit