package tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Context {
    Playwright playwright;
    Browser browser;
    BrowserContext context;
    Page page;

    @BeforeMethod
    public  void runeBefore(){
        playwright= Playwright.create();
        browser= playwright.chromium().launch((new BrowserType.LaunchOptions().setHeadless(false)));
        context= browser.newContext();
         page= context.newPage();
        page.navigate("https://rahulshettyacademy.com/loginpagePractise/");
    }

    @Test
    public void DemoTest(){

        Locator firstLink=page.locator(".blinkingText").first();
        Page page1=context.waitForPage(()->firstLink.click());
        page1.waitForLoadState();
        page1.navigate("https://rahulshettyacademy.com/documents-request");

        assertThat(page1.getByText("mentor@rahulshettyacademy.com", new Page.GetByTextOptions().setExact(true))).isVisible();
        String email=page1.getByText("mentor@rahulshettyacademy.com", new Page.GetByTextOptions().setExact(true)).innerText();
        //System.out.println(email);
        page.getByLabel("Username:").fill(email);
        String inputEmail=page.getByLabel("Username:").inputValue();
        System.out.println("Input email"+inputEmail);
        page.waitForTimeout(3000);
    }

    @Test (description = "Testing checkbox")
    public void Checkbox(){
        Locator UserBtn=   page.getByText("User", new Page.GetByTextOptions().setExact(true));
        UserBtn.check();
        page.locator("#okayBtn").click();
       Assert.assertTrue(UserBtn.isChecked());


        page.getByRole(AriaRole.COMBOBOX ).selectOption("Teacher");
       Locator checkBox= page.getByRole(AriaRole.CHECKBOX);
       checkBox.click();
       Assert.assertTrue(checkBox.isChecked());
    }
}
