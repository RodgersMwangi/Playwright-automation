package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extentReports;

    public static ExtentReports getInstance(){
        if (extentReports==null){
            ExtentSparkReporter sparkReporter=new ExtentSparkReporter("test-output/ExtentReport.html");

            sparkReporter.config().setReportName("Automation test results");
            sparkReporter.config().setDocumentTitle("Test execution report");

            extentReports=new ExtentReports();
            extentReports.attachReporter(sparkReporter);

            extentReports.setSystemInfo("Framework","Playwright+TestNG");
            extentReports.setSystemInfo("Tester","Steve");
        }
        return extentReports;
    }
}
