package base;

import com.microsoft.playwright.assertions.LocatorAssertions;
import org.testng.annotations.Test;
import pages.EditPIMPage;
import pages.LoginPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EditPIMTest extends BaseTest {
    @Test
    public void testWithValidCredentials(){
        EditPIMPage editPIMPage = new EditPIMPage(page);
        editPIMPage.editContactDetails();


    }
}
