package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import util.ConfigReader;

public class DashboardTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();

    @Test
    public void navigateToPIM(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);

        PIMPage pimPage=dashboardPage.navigateToPIM();

        Assert.assertTrue(pimPage.isPIMPageAvailable());
    }
}
