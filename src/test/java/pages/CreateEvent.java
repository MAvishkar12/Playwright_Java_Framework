package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CreateEvent {
    Page page;
    public  String TitleLocator="Title*";
    public   String  EventdescLocator="Describe the event…";
    public String CategoryLocator = "Category*";
    public String CityLocator = "City*";
    public String VenueLocator = "Venue*";
    public String DateTimeLocator = "input[type='datetime-local']";
    public String PriceLocator = "0.00";
    public String TotalSeatsLocator = "Total Seats*";
    public String AddEventLocator = "+ Add Event";
    public String EventCreatedLocator = "Event created!";
    public CreateEvent(Page page){
        this.page=page;


    }

    public  void eventDetails( String title,
                               String eventDescription,
                               String category,
                               String city,
                               String venue,
                               String dateTime,
                               String price,
                               String totalSeats){
        page.getByText("Admin", new Page.GetByTextOptions().setExact(true)).click();
        page.locator("a").filter(new Locator.FilterOptions().setHasText("Manage Events")).first().click();
        page.getByLabel(TitleLocator).fill(title);
        page.getByPlaceholder(EventdescLocator).fill(eventDescription);
        page.getByLabel(CategoryLocator).selectOption(category);
        page.getByLabel(CityLocator).fill(city);
        page.getByLabel(VenueLocator).fill(venue);
        page.locator(DateTimeLocator).fill(dateTime);
        page.getByPlaceholder(PriceLocator, new Page.GetByPlaceholderOptions().setExact(true)).fill(price);
        page.getByLabel(TotalSeatsLocator ).fill(totalSeats);
        page.getByText(AddEventLocator, new Page.GetByTextOptions().setExact(true)).click();
        assertThat(page.getByText(EventCreatedLocator)).isVisible();
    }


}
