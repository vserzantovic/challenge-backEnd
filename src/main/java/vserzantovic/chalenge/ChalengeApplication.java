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
        List<Response> responseList = new ArrayList<>();

        try {

            ObjectMapper objectMapper = new ObjectMapper();
//            URL resource = ChalengeApplication.class.getClassLoader().getResource("input.json");
            URL resource = ChalengeApplication.class.getClassLoader().getResource("input2.json");
            Object object = parser.parse(new FileReader(resource.getFile()));

            JSONArray jsonArray = (JSONArray) object;
            List<Device> deviceList = new ArrayList<>();
//
            Type listType = new TypeToken<ArrayList<Device>>() {
            }.getType();
            List<Device> target2 = new Gson().fromJson(String.valueOf(jsonArray), listType);

//            jsonArray.stream().forEach(s -> System.out.println("forEach: " + s));
//            veikia ir ne .stream
//            jsonArray.stream().forEach(request -> {
            jsonArray.forEach(request -> {
                try {
                    checkJsonItem((JSONObject) request, objectMapper, deviceList, responseList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
//
//            veikia su vienu
//            checkJson(jsonArray);
            Device device = objectMapper.readValue(jsonArray.get(0).toString(), Device.class);
//            List<Device> deviceList = Arrays.asList(objectMapper.readValue(jsonArray.toString(), Device[].class));

            System.out.println(device);

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

    static void checkJsonItem(JSONObject json, ObjectMapper objectMapper, List<Device> deviceList, List<Response> responseList) throws IOException {
        if (json.get("type").equals("profile_create")) {
            Device device = objectMapper.readValue(json.toString(), Device.class);
            deviceList.add(device);
        } else if (json.get("type").equals("profile_update")) {

            for (int i = 0; i < deviceList.size(); i++) {
                Device device = objectMapper.readValue(json.toString(), Device.class);
                if (deviceList.get(i).getModel_name().equals(json.get("model_name"))) {
                    deviceList.set(i, device);
                }
            }

        } else {
            if (checkRequest(json, deviceList)) {
                System.out.println("surasstas atitinkamas model_name");
            } else {
                System.out.println("nesurastas atitinkantis device'as pagal model_name");
            }
//                validating....
            Response response = new Response(json.get("request_id").toString(), json.get("device_id").toString(), "allow");
            responseList.add(response);
            System.out.println("sukurimas request objektas");
        }


    }

    static boolean checkRequest(JSONObject json, List<Device> deviceList) {
        for (Device device : deviceList) {
            if (device.getModel_name().equals(json.get("model_name"))) {
                return true;
            }
        }
        return false;
    }

}
