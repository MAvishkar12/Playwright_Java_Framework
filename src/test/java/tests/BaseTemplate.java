package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BaseTemplate {

    Page page;
    Browser browser;
    Playwright playwright;
    String url;
    @BeforeMethod
    public void startMe() throws IOException {
        playwright  = Playwright.create();
        Properties prop = new Properties();
        FileInputStream fs = new FileInputStream("resources/config.properties");
        prop.load(fs);
        System.out.println();

        String browser1=    System.getProperty("browser") != null ? System.getProperty("browser"):prop.getProperty("browser") ;
        System.out.println();
         url = prop.getProperty(System.getProperty("env") != null ? System.getProperty("env") + "_url" : "base_url");
//
        System.out.println("Browser is"+browser1);
        System.out.println("URL is"+url);

        if("firefox".equals(browser1)){
            browser=playwright.firefox().launch();
        }
        else if("chrome".equals(browser1)){
            System.out.println("chrome browser start");
            browser = playwright.chromium().launch((new BrowserType.LaunchOptions().setHeadless(false)));
        }





    }
}
