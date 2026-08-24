package tests;

import com.jayway.jsonpath.JsonPath;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

public class API_Testing {

     Playwright playwright;
    APIRequestContext request;
    @BeforeMethod
    public  void beforeMe(){
       playwright=  Playwright.create();
        request = playwright.request().newContext();


     }

     @Test
    public  void api(){
        // Login to get token
         HashMap<String, String> hash = new HashMap<>();
         hash.put("email","abc1217@gmail.com");
         hash.put("password","Playwright@2026");
        APIResponse response =request.post("https://api.eventhub.rahulshettyacademy.com/api/auth/login",
                 RequestOptions.create().setData(hash)
         );
         //System.out.println(response.ok());

       String Logintoken= JsonPath.read(response.text(),"$.token");
         System.out.println(Logintoken);

         // Create a event
         HashMap<String, Object> data = new HashMap<>();
         data.put("title", "abbc");
         data.put("description", "xyz");
         data.put("category", "abc");
         data.put("venue", "abc");
         data.put("eventDate", "2026-08-20T16:49:00.000Z");
         data.put("city", "Shivajiadfad adfad  nagar");
         data.put("price", 21004);
         data.put("totalSeats", 40);

         APIResponse createEvent= request.post("https://api.eventhub.rahulshettyacademy.com/api/events",
                   RequestOptions.create()
                           .setHeader("Authorization","Bearer "+Logintoken)
                           .setData(data)

                   );

         //Assert.assertTrue(createEvent.ok(),"Event created successfully");
         int eventId=JsonPath.read(createEvent.text(),"$.data.id");
         System.out.println(eventId);

       // Get list of events
       APIResponse getAllEvent=   request.get("https://api.eventhub.rahulshettyacademy.com/api/events/",
                  RequestOptions.create().setHeader("Authorization","Bearer "+Logintoken)
                  );
         Assert.assertTrue(getAllEvent.ok());
         System.out.println(getAllEvent.text());
         List<Integer> eventsId = JsonPath.read(getAllEvent.text(), "$.data[*].id");
         System.out.println(eventsId);
       Assert.assertTrue(eventsId.contains(eventId));
         // Delete user
         APIResponse deleteEvent= request.delete("https://api.eventhub.rahulshettyacademy.com/api/events/"+eventId,
               RequestOptions.create().setHeader("Authorization","Bearer "+Logintoken)
           );
         System.out.println(deleteEvent.ok());
         System.out.println(deleteEvent.text());
        Assert.assertTrue(deleteEvent.ok(),"Event deleted successfully");
         List<Integer> eventsIdDelete = JsonPath.read(getAllEvent.text(), "$.data[*].id");

         //Assert.assertFalse(eventsIdDelete.contains(eventId));

     }
}
