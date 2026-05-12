package util;

import com.microsoft.playwright.Page;

import java.nio.file.Paths;

public class ScreenshotUtil {

    public static void takeScreenshot(Page page, String testName, String status) {

        String filePath = "target/screenshots/" + status + "/" + testName + "_" + System.currentTimeMillis() + ".png";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(filePath))
                .setFullPage(true));
    }
}