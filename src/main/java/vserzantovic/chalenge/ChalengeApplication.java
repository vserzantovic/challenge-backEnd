package vserzantovic.chalenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ChalengeApplication {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {

        SpringApplication.run(ChalengeApplication.class, args);
        JSONParser parser = new JSONParser();

        try {
//            Gson gson = new Gson();
//            URL resource = ChalengeApplication.class.getClassLoader().getResource("input.json");
//            Reader reader = new FileReader(resource.getFile());
//            List<Device> device = gson.fromJson(reader, Device.class);
//
//            System.out.println(device);



            URL resource = ChalengeApplication.class.getClassLoader().getResource("input.json");
            Object object = parser.parse(new FileReader(resource.getFile()));

            JSONArray jsonArray = (JSONArray) object;
            List<Device> deviceList = new ArrayList<>();
            Object o = jsonArray.get(0);

            Type listType = new TypeToken<ArrayList<Device>>(){}.getType();
            List<Device> target2 = new Gson().fromJson(String.valueOf(jsonArray), listType);

            System.out.println(target2);
            System.out.println(jsonArray);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


}
