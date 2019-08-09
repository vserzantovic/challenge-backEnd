package vserzantovic.chalenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ChalengeApplication {
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

            jsonArray.forEach(request -> {

                try {
//                    klausimas ar reikia
//                    Device deviceRequest = objectMapper.readValue(request.toString(), Device.class);
                    checkJsonItem((JSONObject) request, objectMapper, deviceList, responseList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            responseList.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

//    static void checkJson(JSONArray jsonArray) {
//        for (int i = 0; i < jsonArray.size(); i++) {
//
//            if (((JSONObject) jsonArray.get(i)).get("timestamp") instanceof Long) {
//                System.out.println("good");
//            } else {
//                System.out.println("blogas json irasas");
//
//            }
//        }
//
//    }

    static void checkJsonItem(JSONObject json, ObjectMapper objectMapper, List<Device> deviceList, List<Response> responseList) throws IOException {
        Device deviceEvent = objectMapper.readValue(json.toString(), Device.class);

        //profile_create
        if (deviceEvent.getType().equals("profile_create")) {
            deviceList.add(deviceEvent);

            //profile_update
        } else if (deviceEvent.getType().equals("profile_update")) {

            for (int i = 0; i < deviceList.size(); i++) {

                if (deviceList.get(i).getModel_name().equals(deviceEvent.getModel_name())) {
                    deviceList.get(i).setBlackList(deviceEvent.getBlackList());
                    deviceList.get(i).setWhiteList(deviceEvent.getWhiteList());
                }
            }
            //request
        } else {
            Response response = checkRequest(deviceEvent, deviceList);
            responseList.add(response);
            System.out.println("sukurimas request objektas");
        }


    }

    //validateRequest
    static Response checkRequest(Device deviceRequest, List<Device> deviceList) {
        Device relativeDevice = getRelativeDevice(deviceRequest, deviceList);

        if (relativeDevice != null) {
//            tikrinti kai vyksta profile_update kad normaliia uzsetintu duomenys
            String defaultPolicy = relativeDevice.getDefaultPolicy();
            if (defaultPolicy.equals("allow")) {
                Boolean isUrlInBlackList = relativeDevice.getBlackList().stream().anyMatch(url -> url.contains(deviceRequest.getUrl()));
                Response response = new Response(deviceRequest.getRequestId(), deviceRequest.getDeviceId(), isUrlInBlackList ? "block" : "allow");
                return response;
            } else {
                boolean validUrl = isValidURL(deviceRequest.getUrl());
                boolean isUrlInWhiteList = relativeDevice.getWhiteList().stream().anyMatch(url -> url.contains(deviceRequest.getUrl()));
                Response response = new Response(deviceRequest.getRequestId(), deviceRequest.getDeviceId(), isUrlInWhiteList ? "allow" : "block");
                return response;
            }

        }
        System.out.println(relativeDevice.getModel_name());

        return null;
    }

    static Device getRelativeDevice(Device deviceRequest, List<Device> deviceList) {
        for (Device device : deviceList) {
            if (device.getModel_name().equals(deviceRequest.getModel_name())) {
                return device;
            }
        }
        return null;
    }
    public static boolean isValidURL(String url)
    {
        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
//    private static Boolean isValidURL(String pUrl) {
//
//        URL u = null;
//        try {
//            u = new URL(pUrl);
//        } catch (MalformedURLException e) {
//            return false;
//        }
//        try {
//            u.toURI();
//        } catch (URISyntaxException e) {
//            return false;
//        }
//        return true;
//    }


}
