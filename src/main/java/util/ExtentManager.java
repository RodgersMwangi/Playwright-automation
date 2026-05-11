package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;

    public static ExtentReports getInstance(){
        if (extentReports==null){
            //Prevents overwriting the reports file on each run,each run now generates its own report
            String path = "test-output/ExtentReport_" + System.currentTimeMillis() + ".html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
            //ExtentSparkReporter sparkReporter=new ExtentSparkReporter("test-output/ExtentReport.html");

            sparkReporter.config().setReportName("Regression test results");
            sparkReporter.config().setDocumentTitle("Test execution report");

            extentReports=new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            extentReports.setSystemInfo("Framework","Playwright+TestNG");
            extentReports.setSystemInfo("Tester","Steve");
        }
        return extentReports;
    }
}
