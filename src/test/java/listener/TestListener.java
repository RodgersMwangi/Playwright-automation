package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.ExtentManager;

public class TestListener implements ITestListener {
    private static ExtentReports extent =ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String classname=result.getTestClass().getName();
        String testname=result.getMethod().getMethodName();
        String description=result.getMethod().getDescription();
        ExtentTest extentTest= extent.createTest(classname+" - "+testname,description);
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); //writes everything to report
    }
}
