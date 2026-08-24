package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class DataDriven {

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

    @Test(dataProvider = "userData")
    public void printData(HashMap<String, String> h1) {

        System.out.println(h1.get("email"));
        System.out.println(h1.get("password"));
    }
}