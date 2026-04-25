import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.MyInfoPage;
import util.ConfigReader;


public class MyInfoTest extends BaseTest {

    ConfigReader configReader = ConfigReader.getInstance();

    // TC-001: Verify Admin user can navigate to My info Page
    @Test(description = "TC-001: Verify My Info page is displayed")
    public void myInfoPageDisplayVerification() {
        LoginPage loginPage = new LoginPage(page);
        MyInfoPage myInfoPage = new MyInfoPage(page);


        loginPage.userLogin(
                configReader.getProperty("admin.username"),
                configReader.getProperty("admin.password")

        );
        page.waitForURL("**/dashboard/**");
        Assert.assertTrue(
                page.url().contains("dashboard"),
                "[TC-001] Login failed.Current URl: " + page.url()
        );
        myInfoPage.clickMyInfoMenu();

        page.waitForURL("**/viewPersonalDetails/**");

        Assert.assertTrue(
                page.url().contains("viewPersonalDetails"),
                "[TC-001] My Info page did not open. Current URL: " + page.url()
        );
        Assert.assertTrue(
                myInfoPage.isPersonDetailHeaderVisible(),
                "[TC-001] Personal Details header is not visible"
        );

        System.out.println("[TC-001] My Info page displayed successfully. URL: " + page.url());
    }
}
