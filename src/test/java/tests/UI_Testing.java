package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UI_Testing {
    Playwright playwright;
    Browser browser;
    Page page;
    BrowserContext context;
    @BeforeMethod(alwaysRun = true)
    public  void beforeMethod(){
       playwright= Playwright.create();
       browser= playwright.chromium().launch(new BrowserType.LaunchOptions()
               .setHeadless(false));
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page= context.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");


    }

    @AfterMethod
    public  void closeMethod(){
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }

    @Test(groups = {"smoke"})
    public void TestUI(){
        page.locator("#show-textbox").click();
      Locator input= page.getByPlaceholder("Hide/Show Example", new Page.GetByPlaceholderOptions().setExact(true));
      assertThat(input).isVisible();
//        page.locator("#hide-textbox").click();
//        assertThat(input).isHidden();
//        page.screenshot(new Page.ScreenshotOptions()
//                .setPath(Paths.get("screenshot.png")));
        input.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("newscreenshot.png")));

    }

    @Test(groups = {"sanity"})
    public void MosueHover(){
        page.getByText("Mouse Hover", new Page.GetByTextOptions().setExact(true)).hover();
        page.getByText("Top", new Page.GetByTextOptions().setExact(true)).click();
    }
    @Test(groups = {"sanity"})
    public  void Frame(){
       FrameLocator frameL= page.frameLocator("#courses-iframe");
       String heading=frameL.locator(".header-text h2").innerText();
        System.out.println("Heading"+heading);

    }
}
