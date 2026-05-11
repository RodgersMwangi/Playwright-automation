package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import util.ExtentManager;
import util.TestManager;

public class TestListener implements ITestListener {
    private static ExtentReports extent =ExtentManager.getInstance();
    //private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String testName=result.getMethod().getMethodName();
        String className=result.getTestClass().getRealClass().getSimpleName();
        ExtentTest extentTest= extent.createTest(className+" - "+testName);
        //test.set(extentTest);
        TestManager.setTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TestManager.getTest().pass("Test passed successfully");
        //test.get().pass("Test passed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TestManager.getTest().fail(result.getThrowable());
        //test.get().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestManager.getTest().skip("Test skipped");
        //test.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); //writes everything to report
    }
}
