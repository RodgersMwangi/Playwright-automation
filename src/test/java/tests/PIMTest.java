package tests;

import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PIMTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();

    @Test
    public void saveValidEmployeeDetails(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        PIMPage pimPage=dashboardPage.navigateToPIM();
        pimPage.saveEmployeeDetails(configReader.getProperty("employee.firstname"), configReader.getProperty("employee.middlename"), configReader.getProperty("employee.lastname"), configReader.getProperty("employee.id"));

        assertThat(page.getByText(configReader.getProperty("employee.firstname"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));;
    }
}
