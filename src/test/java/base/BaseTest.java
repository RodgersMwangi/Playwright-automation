package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import util.ConfigReader;

public class BaseTest {
    protected Browser browser;
    protected Page page;

    BrowserFactory browserFactory;
    ConfigReader configReader=ConfigReader.getInstance();

    @BeforeMethod
    public void setUp(){


        browserFactory=new BrowserFactory();
        browser=browserFactory.createBrowser();
        page=browser.newPage();

        page.setDefaultTimeout(60000);
        page.setDefaultNavigationTimeout(60000);

        page.navigate(configReader.getProperty("orangeHrm.url"));
    }

    @AfterMethod
    public void tearDown(){
        browserFactory.closeBrowser();

    }

}
