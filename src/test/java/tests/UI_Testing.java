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
       browser= playwright.chromium().launch();
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page= context.newPage();
        page.navigate("https://rahulshettyacademy.com/AutomationPractice/");


    }

    @AfterMethod (alwaysRun = true)
    public  void closeMethod(){
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }

    @Test
    public void TestUI(){
        page.locator("#show-textbox").click();
      Locator input= page.getByPlaceholder("Hide/Show Example", new Page.GetByPlaceholderOptions().setExact(true));
      assertThat(input).isVisible();
        input.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get("newscreenshot.png")));

    }

    @Test
    public void MosueHover(){
        page.getByText("Mouse Hover", new Page.GetByTextOptions().setExact(true)).hover();
        page.getByText("Top", new Page.GetByTextOptions().setExact(true)).click();
    }
    @Test
    public  void Frame(){
       FrameLocator frameL= page.frameLocator("#courses-iframe");
       String heading=frameL.locator(".header-text h2").innerText();
        System.out.println("Heading"+heading);

    }
}
