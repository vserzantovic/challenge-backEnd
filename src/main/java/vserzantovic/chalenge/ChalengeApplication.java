package vserzantovic.chalenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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



//            URL resource = ChalengeApplication.class.getClassLoader().getResource("input.json");
            URL resource = ChalengeApplication.class.getClassLoader().getResource("input2.json");
            Object object = parser.parse(new FileReader(resource.getFile()));

            JSONArray jsonArray = (JSONArray) object;
//            List<Device> deviceList = new ArrayList<>();
//            Object o = jsonArray.get(0);
//
            Type listType = new TypeToken<ArrayList<Device>>(){}.getType();
//            List<Device> target2 = new Gson().fromJson(String.valueOf(jsonArray), listType);
            List<Device> target2 = new Gson().fromJson(String.valueOf(jsonArray), listType);

            String json = "{ \"model_name\" : \"iphone\", \"type\" : \"BMW\", \"default\" : \"allow\",  \"whiteList\" : [\"test\",\"test1\"] }";
            ObjectMapper objectMapper = new ObjectMapper();
//            Device device = objectMapper.readValue(object.toString(), Device.class);
//            Device device = objectMapper.readValue(json, Device.class);
//            veikia su vienu
            checkJson(jsonArray);
            Device device = objectMapper.readValue(jsonArray.get(0).toString(), Device.class);
            List<Device> deviceList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), Device[].class));


//            Stream<Device> ss = jsonArray.stream().map(json1 -> json1.toString());
//            List<Device> list = ss.collect(Collectors.toList());
//            List<Device> deviceList = objectMapper.readValue(jsonArray.toString(), listType);

            System.out.println(device);
//            System.out.println(target2);
//            System.out.println(jsonArray);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    static void checkJson(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {

            if (((JSONObject) jsonArray.get(i)).get("timestamp") instanceof Long) {
                System.out.println("good");
            } else {
                System.out.println("blogas json irasas");

            }
        }

    }


}
