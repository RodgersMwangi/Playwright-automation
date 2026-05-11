package base;

import com.microsoft.playwright.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import util.ConfigReader;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {
    protected Browser browser;
    protected BrowserContext context;
    public static Page page;

    BrowserFactory browserFactory;
    ConfigReader configReader = ConfigReader.getInstance();

    @BeforeMethod
    public void setUp(Method method){
        browserFactory=new BrowserFactory();
        browser=browserFactory.createBrowser();
        page=browser.newPage();
    public void setUp() {

        browserFactory = new BrowserFactory();
        browser = browserFactory.createBrowser();

        // Create context with video recording
        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get("target/videos/"))
                        .setRecordVideoSize(1280, 720)
        );

        page = context.newPage();

        page.setDefaultTimeout(600000);
        page.setDefaultNavigationTimeout(600000);

        page.navigate(configReader.getProperty("orangeHrm.url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (context != null) {
            context.close(); // MUST close first for video to be saved
        }

        // Handle video after context is closed
        if (page != null && page.video() != null) {

            Path videoPath = page.video().path();

            if (result.getStatus() == ITestResult.FAILURE) {

                Path targetPath = Paths.get(
                        "target/videos/failed/" +
                                result.getName() + ".webm"
                );

                targetPath.toFile().getParentFile().mkdirs();

                videoPath.toFile().renameTo(targetPath.toFile());

            } else {
                // delete video if test passed
                videoPath.toFile().delete();
            }
        }

        browserFactory.closeBrowser();
    }
}