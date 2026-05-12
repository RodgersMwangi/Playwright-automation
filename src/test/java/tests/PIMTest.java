package tests;

import base.BaseTest;
import com.microsoft.playwright.assertions.LocatorAssertions;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PIMPage;
import util.ConfigReader;
import util.DataFaker;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(listeners.TestListener.class)
public class PIMTest extends BaseTest {
    ConfigReader configReader=ConfigReader.getInstance();
    PIMPage pimPage;

    public void loginAndOpenAddPage(){
        LoginPage loginPage=new LoginPage(page);
        loginPage.userLogin(configReader.getProperty("admin.username"),configReader.getProperty("admin.password"));

        DashboardPage dashboardPage=new DashboardPage(page);
        pimPage=dashboardPage.navigateToPIM();
        pimPage.clickAddButton();
    }

    public String generateLongName(int minLength) {
        String name= DataFaker.firstName;
        while (name.length() <= minLength) {
            name += DataFaker.firstName;
        }

        return name;
    }

    @Test(description = "Verify successful saving of employee details")
    public void saveEmployeeDetails_success(){
        loginAndOpenAddPage();
        String firstName=DataFaker.firstName;
        String middleName=DataFaker.middleName;
        String lastName=DataFaker.lastName;
        String id=DataFaker.employeeId;

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(page.getByText(firstName)).isVisible(new LocatorAssertions.IsVisibleOptions().setTimeout(10000));
    }

    @Test(description = "Verifies validation when saving with no last name")
    public void saveEmployeeDetails_noLastName(){
        loginAndOpenAddPage();

        String firstName=DataFaker.firstName;
        String middleName=DataFaker.middleName;
        String id=DataFaker.employeeId;

        pimPage.saveEmployeeDetails(firstName,middleName,id);

        assertThat(pimPage.getRequiredError()).isVisible();
    }

    @Test(description = "Verifies validation when saving with no first and last names")
    public void saveEmployeeDetails_noFirstAndLastNames(){
        loginAndOpenAddPage();

        String middleName=DataFaker.middleName;
        String id=DataFaker.employeeId;

        pimPage.saveEmployeeDetails(middleName,id);

        assertThat(pimPage.getRequiredError()).hasCount(2);
    }

    @Test(description = "Verfifies validation when saving with long character names")
    public void saveEmployeeDetails_longCharacterNames(){
        loginAndOpenAddPage();

        String firstName=generateLongName(32);
        String middleName=DataFaker.middleName;
        String lastName=DataFaker.lastName;
        String id=DataFaker.employeeId;

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(pimPage.getLengthError("Should not exceed 30 characters")).isVisible();
    }

    @Test(description = "Verfies validation when saving with an ID exceeding the allowed characters")
    public void saveEmployeeDetails_longId(){
        loginAndOpenAddPage();

        String firstName=DataFaker.firstName;
        String middleName=DataFaker.middleName;
        String lastName=DataFaker.lastName;
        String id=DataFaker.longId();

        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        assertThat(pimPage.getLengthError("Should not exceed 10 characters")).isVisible();
    }

    @Test(description = "Verifies validation when saving an already existing ID")
    public void saveEmployeeDetails_duplicateId(){
        loginAndOpenAddPage();

        String firstName=DataFaker.firstName;
        String middleName=DataFaker.middleName;
        String lastName=DataFaker.lastName;
        String id=DataFaker.employeeId;

        //Save on first run
        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);

        //Resave using the same credentials
        pimPage.saveEmployeeDetails(firstName,middleName,lastName,id);
    }

}
