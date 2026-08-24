package tests;

import com.microsoft.playwright.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Mocking {
    Page page;
    Browser browser;
    Playwright playwright;

    @BeforeMethod
    public void openMethod() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch((new BrowserType.LaunchOptions().setHeadless(false)));
        page = browser.newPage();
        page.navigate("https://eventhub.rahulshettyacademy.com/login");
        System.out.println(page.title());
        assertThat(page).hasURL("https://eventhub.rahulshettyacademy.com/login");
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByLabel("Email").fill("abc1217@gmail.com");
        page.getByLabel("Password").fill("Playwright@2026");
        page.getByText("Sign In", new Page.GetByTextOptions().setExact(true)).click();
        assertThat(page.getByText("Browse Events →")).isVisible();
    }
      @Test
      public  void runMocking(){
        page.route("**/api/events/**",route -> route.fulfill(
                new Route.FulfillOptions().setPath(Paths.get("src/data/UserData.json"))
        ));

        page.navigate("https://eventhub.rahulshettyacademy.com/events");
        assertThat( page.locator(".mx-1").first()).isVisible();
        page.waitForTimeout(6000);
//
//          page.route("**//api/events/", route -> route.fulfill(
//                  new Route.FulfillOptions().setPath(Paths.get("mock_data.json"))));
      }
}
