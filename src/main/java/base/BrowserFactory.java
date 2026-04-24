package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.util.Scanner;

public class BrowserFactory {
    private Browser browser;
    private Playwright playwright;

    public Browser createBrowser(){
        playwright=Playwright.create();
        String browserOption = System.getProperty("browser", "chromium");;

        if(browserOption.equalsIgnoreCase("webkit")) {
            System.out.println("Using webkit...");
            return browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        else if(browserOption.equalsIgnoreCase("firefox")) {
            System.out.println("Using firefox...");
            return browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
        System.out.println("Using chromium...");
        return browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    public void closeBrowser(){
        if(browser!=null){
            browser.close();
        }
        if(playwright!=null){
            playwright.close();
        }

    }
    // TODO: Add multi-browser selection logic. Default shd be chromium
}
