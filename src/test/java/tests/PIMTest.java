package tests;

import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import util.ConfigReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PIMTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();
    PIMPage pimPage;
    Faker faker=new Faker();


    public void loginAndOpenAddPage(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        pimPage=dashboardPage.navigateToPIM();
        pimPage.clickAddButton();
    }

    public String generateLongName(int minLength) {
        String name = faker.name().firstName();

        while (name.length() <= minLength) {
            name += faker.name().firstName();
        }

        return name;
    }

    @Test
    public void saveEmployeeDetails_success(){
        loginAndOpenAddPage();
        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(page.getByText(firstName)).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

    @Test
    public void saveEmployeeDetails_noLastName(){
        loginAndOpenAddPage();

        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(firstName,middleName,id);

        assertThat(pimPage.getRequiredError()).isVisible();
    }

    @Test
    public void saveEmployeeDetails_noFirstAndLastNames(){
        loginAndOpenAddPage();

        String middleName=faker.name().firstName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(middleName,id);

        assertThat(pimPage.getRequiredError()).hasCount(2);
    }

    @Test
    public void saveEmployeeDetails_longCharacterNames(){
        loginAndOpenAddPage();

        String firstName=generateLongName(32);
        String middleName=faker.name().lastName();
        String lastName=faker.name().lastName();
        String id=faker.number().digits(5);

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(pimPage.getLengthError("Should not exceed 30 characters")).isVisible();
    }

    @Test
    public void saveEmployeeDetails_longId(){
        loginAndOpenAddPage();

        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String id=faker.number().digits(12);

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(pimPage.getLengthError("Should not exceed 10 characters")).isVisible();
    }

    @Test
    public void saveEmployeeDetails_duplicateId(){
        loginAndOpenAddPage();

        String firstName=faker.name().firstName();
        String middleName=faker.name().firstName();
        String lastName=faker.name().lastName();
        String id=faker.number().digits(5);

        //Save on first run
        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        //Resave using the same credentials
        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);
    }

}
