package vserzantovic.challenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vserzantovic.challenge.Device;
import vserzantovic.challenge.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import static vserzantovic.challenge.Constants.*;

@Service
public class DeviceEventService {

    public String uploadFile(MultipartFile file) {
        JSONParser parser = new JSONParser();
        List<Response> responseList = new ArrayList<>();

        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/message.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        String json = null;
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            ObjectMapper objectMapper = new ObjectMapper();
            Object object = parser.parse(new InputStreamReader(file.getInputStream(), "UTF-8"));

            JSONArray jsonArray = (JSONArray) object;
            List<Device> deviceList = new ArrayList<>();

            jsonArray.forEach(request -> {
                try {
                    checkJsonItem((JSONObject) request, objectMapper, deviceList, responseList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            int protectedDevices = deviceList.size();
            Predicate<Response> predicate = s -> s.getAction().equals(QUARANTINE.getText());
            int suspectedToBeHacked = (int) responseList.stream().filter(predicate).count();

            json = writeToJson(responseList, prop, protectedDevices, suspectedToBeHacked);




            responseList.forEach(System.out::println);
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
        
    }

    void checkJsonItem(JSONObject json, ObjectMapper objectMapper, List<Device> deviceList, List<Response> responseList) throws IOException {
        Device deviceEvent = objectMapper.readValue(json.toString(), Device.class);

        //type: profile_create
        if (deviceEvent.getType().equals(PROFILE_CREATE.getText())) {
            deviceList.add(deviceEvent);

            //type: profile_update
        } else if (deviceEvent.getType().equals(PROFILE_UPDATE.getText())) {
            deviceList.forEach(device -> {
                if (device.getModel_name().equals(deviceEvent.getModel_name())) {
                    if (deviceEvent.getBlackList() != null) {
                        device.setBlackList(deviceEvent.getBlackList());
                    }
                    if (deviceEvent.getWhiteList() != null) {
                        device.setWhiteList(deviceEvent.getWhiteList());
                    }
                }
            });
            //type: request
        } else {
            Response response = checkRequest(deviceEvent, deviceList);
            responseList.add(response);
        }


    }

    static Response checkRequest(Device deviceRequest, List<Device> deviceList) {
        Device relativeDevice = getRelativeDevice(deviceRequest, deviceList);

        if (relativeDevice != null) {
            String defaultPolicy = relativeDevice.getDefaultPolicy();
            if (defaultPolicy.equals(ALLOW.getText())) {
                Boolean isUrlInBlackList = relativeDevice.getBlackList().stream().anyMatch(url -> url.contains(deviceRequest.getUrl()));
                Response response = new Response(deviceRequest.getRequestId(), deviceRequest.getDeviceId(), isUrlInBlackList ? BLOCK.getText() : ALLOW.getText());
                return response;
            } else {
                boolean validUrl = !isValidURL(deviceRequest.getUrl());
                boolean isUrlInWhiteList = relativeDevice.getWhiteList().stream().anyMatch(url -> url.contains(deviceRequest.getUrl()));

                Response response = new Response(deviceRequest.getRequestId(), deviceRequest.getDeviceId(), validUrl ? (isUrlInWhiteList ? ALLOW.getText() : BLOCK.getText()) : QUARANTINE.getText());
                return response;
            }

        }
        Response response = new Response(deviceRequest.getRequestId(), deviceRequest.getDeviceId(), QUARANTINE.getText());
        return response;
    }

    static Device getRelativeDevice(Device deviceRequest, List<Device> deviceList) {
        for (Device device : deviceList) {
            if (device.getModel_name().equals(deviceRequest.getModel_name())) {
                return device;
            }
        }
        return null;
    }

    public static boolean isValidURL(String url) {
        try {
            double d = Double.parseDouble(url);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;

    }

    public static String writeToJson(List<Response> responseList, Properties prop, int protectedDevices, int suspectedToBeHacked) {

        String json = new Gson().toJson(responseList);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\output.json");
            file.write(json);
            file.write("\n");
            file.write(prop.getProperty("statistic.text.q1"));
            file.write("Answer: " + protectedDevices);
            file.write("\n");
            file.write(prop.getProperty("statistic.text.q2"));
            file.write("Answer: " + suspectedToBeHacked);
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }


}
