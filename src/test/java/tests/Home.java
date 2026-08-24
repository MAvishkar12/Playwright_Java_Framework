package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.CreateEvent;
import pages.EventForm;
import pages.EventsPage;
import pages.LoginPage;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class Home extends BaseTemplate {

    @DataProvider(name = "userData")
    public Object[][] userData() {

        try {
            ObjectMapper mapper = new ObjectMapper();

            List<HashMap<String, String>> data = mapper.readValue(
                    new File("resources/userData.json"),
                    new TypeReference<List<HashMap<String, String>>>() {}
            );

            Object[][] result = new Object[data.size()][1];

            for (int i = 0; i < data.size(); i++) {
                result[i][0] = data.get(i);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data", e);
        }
    }


    private static final Logger log = LoggerFactory.getLogger(Home.class);

    @Test(dataProvider = "userData",description = "user end to end  test Flow")
    public void HomePage(HashMap<String,String> h1){



        page = browser.newPage();
        LoginPage login=new LoginPage(page,url);
        login.loginUser();
        CreateEvent event= new CreateEvent(page);
        event.eventDetails(
                h1.get("eventName"),
                h1.get("eventTitle"),
                h1.get("category"),
        h1.get("location"),
        h1.get("venue"),
        h1.get("dateTime"),
        h1.get("duration"),
        h1.get("capacity"));


         EventsPage eventP=new EventsPage(page);
         int NoOfSeatsBeforeBooking =  eventP.checkeventPresent( h1.get("eventName"));
        System.out.println("Home"+NoOfSeatsBeforeBooking);

        EventForm eform=new EventForm(page);
        eform.validBooking(   h1.get("fullName"),
                h1.get("email"),
                h1.get("phoneNumber"));
        eventP.voidCheckseats(NoOfSeatsBeforeBooking, h1.get("eventName"));


    }

}
