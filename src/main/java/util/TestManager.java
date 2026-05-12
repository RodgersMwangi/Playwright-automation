package util;

import com.aventstack.extentreports.ExtentTest;

public class TestManager {
    private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    public static void setTest(ExtentTest extentTest){
        test.set(extentTest);
    }

    public static ExtentTest getTest(){
        if (test.get()==null){
            throw new RuntimeException("ExtentTest not initialized. Check listener setup.");
        }
        return test.get();
    }
}
