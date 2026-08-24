package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import org.testng.Assert;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EventForm {
    Page page;
    public String FullNameLocator = "Full Name*";
    public String EmailLocator = "Email*";
    public String PhoneNumberLocator = "Phone Number*";
    public String ConfirmBookingLocator = "#confirm-booking";
    public EventForm(Page page){
       this.page=page;
    }

    public  String  FillForm(   String fullName,
                                String email,
                                String phoneNumber){
        page.getByLabel(FullNameLocator).fill(fullName);
        page.getByLabel(EmailLocator).fill(email);
        page.getByLabel(PhoneNumberLocator).fill(phoneNumber);
        page.locator(ConfirmBookingLocator).click();
        assertThat(page.getByText("Booking Confirmed! 🎉")).isVisible();
        String bookingreference=page.locator(".booking-ref").innerText();
        return bookingreference;

    }

    public  void validBooking(String fullName,
                              String email,
                              String phoneNumber){
         String bookingreference =FillForm(fullName,
                 email,
                  phoneNumber);
        page.getByText("View My Bookings", new Page.GetByTextOptions().setExact(true)).click();
        Locator selectedBookingCard= page.getByTestId("booking-card").filter(new Locator.FilterOptions().setHasText(bookingreference));
        String bookingrefenceInCard=selectedBookingCard.locator(".booking-ref").innerText();
        System.out.println(bookingrefenceInCard);
        Assert.assertEquals(bookingrefenceInCard,bookingreference);

    }



}
