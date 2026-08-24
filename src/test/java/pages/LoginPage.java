package pages;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    Page page;
    String url;
    private static  final String passwordLocator="Password";
    private  static final String emailLocator="Email";
    public LoginPage(Page page, String url) {
        this.page=page;
        this.url=url;
    }

    public void loginUser(){


        page.navigate(url);
        assertThat(page).hasURL("https://eventhub.rahulshettyacademy.com/login");
        assertThat(page).hasTitle("EventHub — Discover & Book Events");
        page.getByLabel(emailLocator).fill("abc1217@gmail.com");
        page.getByLabel(passwordLocator).fill("Playwright@2026");
        page.getByText("Sign In", new Page.GetByTextOptions().setExact(true)).click();
    }

}
