package listeners;

import base.BaseTest;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.ScreenshotUtil;

public class TestListener extends BaseTest implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        ScreenshotUtil.takeScreenshot(
                page,
                result.getName(),
                "passed"
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ScreenshotUtil.takeScreenshot(
                page,
                result.getName(),
                "failed"
        );
    }
}