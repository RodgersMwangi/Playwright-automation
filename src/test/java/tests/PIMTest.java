package tests;

import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PIMTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();
    Faker faker=new Faker();

    @Test
    public void saveEmployeeDetails_success(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        PIMPage pimPage=dashboardPage.navigateToPIM();

        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        //assertThat(page.getByText(configReader.getProperty("employee.firstname"))).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));;
    }

    @Test
    public void saveEmployeeDetails_noLastName(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        PIMPage pimPage=dashboardPage.navigateToPIM();

        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(firstName,middleName,id);

        System.out.println("sdfsdf");
        System.out.println(pimPage.getRequiredError());

        assertThat(pimPage.getRequiredError()).isVisible();
    }

    @Test
    public void saveEmployeeDetails_noFirstAndLastNames(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        PIMPage pimPage=dashboardPage.navigateToPIM();

        String middleName=faker.name().firstName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(middleName,id);

        assertThat(pimPage.getRequiredError()).hasCount(2);
    }

    public void saveEmployeeDetails_duplicateId(){}

}
