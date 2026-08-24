package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.sun.tools.jconsole.JConsoleContext;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventsPage {
    Page page;
    public EventsPage(Page page
    ){
        this.page=page;
    }

    public int checkeventPresent(String EventTitle){
        page.locator("#nav-events").click();

        Locator events = page.getByTestId("event-card");
       // Locator heading = events.last().locator("h3");
        Locator targetCard= events.filter(new Locator.FilterOptions().setHasText(EventTitle));
        assertThat(targetCard).isVisible();
        System.out.println("Event Title is"+EventTitle);
      //  System.out.println("Heading is"+heading);
      //  assertThat(heading).hasText(EventTitle);
        String  NoOfSeatsBeforeBooking=targetCard.getByText("seats").innerText();

        int setBeforeBooking=Integer.parseInt(NoOfSeatsBeforeBooking.split(" ")[0]);
        targetCard.getByTestId("book-now-btn").click();
        return setBeforeBooking;
    }

    public void   voidCheckseats( int NoOfSeatsBeforeBooking, String EventTitle){
        page.locator("#nav-events").click();
        System.out.println("ticket count check");
 //       page.reload();   // force fresh fetch, bypass any SPA cache
       page.waitForLoadState(LoadState.NETWORKIDLE);
        Locator eventsafter = page.getByTestId("event-card");
        Locator targetCardAfter = eventsafter.filter(new Locator.FilterOptions().setHasText(EventTitle));

        Locator seatsAfter = targetCardAfter.getByText("seats");
      //   assertThat(seatsAfter).not().hasText(NoOfSeatsBeforeBooking); // will retry/wait until it actually differs
        String noOfSeatsAfterBooking = seatsAfter.innerText();
        System.out.println(noOfSeatsAfterBooking);;
        int setAfterBooking=Integer.parseInt(noOfSeatsAfterBooking.split(" ")[0]);
        System.out.println(setAfterBooking);
        System.out.println(NoOfSeatsBeforeBooking);
        Assert.assertTrue(NoOfSeatsBeforeBooking>setAfterBooking);
    }

}
