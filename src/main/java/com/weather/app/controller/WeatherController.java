package com.weather.app.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.weather.app.exception.ResourceNotFoundException;
import com.weather.app.model.Weather;
import com.weather.app.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/api")
public class WeatherController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private WeatherRepository weatherRepository;


    @GetMapping("/weather/{latitude},{longitude}")
    public JsonNode getDetails(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude) throws ResourceNotFoundException {
        Weather weather = new Weather();
        JsonNode outputNode= null;
        if(latitude==null || longitude==null){
            throw new ResourceNotFoundException("Latitude or longitude cannot be empty");
        }
       try{
        ResponseEntity<String> resp= restTemplate.getForEntity("https://api.darksky.net/forecast/0b67f8f549800f7bdeccc85500ba9324/"+latitude+","+longitude, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode mapping= mapper.readTree(resp.getBody());
        JsonNode node= mapping.get("daily").get("data").get(0);
        String time= node.get("time").asText();
        //Instant instant = Instant.ofEpochSecond (Long.parseLong(time));
        String sunriseTime= node.get("sunriseTime").asText();
        String sunsetTime= node.get("sunsetTime").asText();
        String temperatureHighTime= node.get("temperatureHighTime").asText();
        String temperatureLowTime= node.get("temperatureLowTime").asText();
        double temperatureLow= node.get("temperatureLow").asDouble();
        double temperatureHigh= node.get("temperatureHigh").asDouble();
        double temperature= mapping.get("currently").get("temperature").asDouble();
        weather.setTime(time);
        weather.setSunriseTime(sunriseTime);
        weather.setSunsetTime(sunsetTime);
        weather.setTemperature(temperature);
        weather.setTemperatureHigh(temperatureHigh);
        weather.setTemperatureHighTime(temperatureHighTime);
        weather.setTemperatureLow(temperatureLow);
        weather.setTemperatureLowTime(temperatureLowTime);
        weather.setDate(convertTimetoDate(time));
        outputNode = mapper.convertValue(weather, JsonNode.class);

       } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        weatherRepository.save(weather);
       return outputNode;
    }
    @GetMapping("/weather/{latitude},{longitude},{timestamp}")
    public JsonNode getlastyearDetails(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("timestamp") String timestamp) throws ResourceNotFoundException {
        Weather lastYear= new Weather();
        JsonNode outputNode= null;
        if(latitude==null || longitude==null || timestamp==null){
            throw new ResourceNotFoundException("Latitude or longitude or timestamp cannot be empty");
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            ResponseEntity<String> resp = restTemplate.getForEntity("https://api.darksky.net/forecast/0b67f8f549800f7bdeccc85500ba9324/" + latitude + "," + longitude + "," + timestamp, String.class);
            JsonNode mapping = mapper.readTree(resp.getBody());
            JsonNode node = mapping.get("daily").get("data").get(0);
            String time= node.get("time").asText();
            //Instant instant = Instant.ofEpochSecond (Long.parseLong(time));
            String sunriseTime= node.get("sunriseTime").asText();
            String sunsetTime= node.get("sunsetTime").asText();
            String temperatureHighTime= node.get("temperatureHighTime").asText();
            String temperatureLowTime= node.get("temperatureLowTime").asText();
            double temperatureLow= node.get("temperatureLow").asDouble();
            double temperatureHigh= node.get("temperatureHigh").asDouble();
            double temperature= mapping.get("currently").get("temperature").asDouble();
            lastYear.setTime(time);
            lastYear.setTemperature(temperature);
            lastYear.setSunsetTime(sunsetTime);
            lastYear.setSunriseTime(sunriseTime);
            lastYear.setTemperatureHighTime(temperatureHighTime);
            lastYear.setTemperatureLowTime(temperatureLowTime);
            lastYear.setTemperatureLow(temperatureLow);
            lastYear.setTemperatureHigh(temperatureHigh);
            lastYear.setDate(convertTimetoDate(time));
            outputNode = mapper.convertValue(lastYear, JsonNode.class);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
      weatherRepository.save(lastYear);
        return  outputNode;
    }
    public String convertTimetoDate(String time){
        long epoch= Long.parseLong(time);
        Date date = new Date(epoch*1000);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formattedDate = format.format(date);
        format.setTimeZone(TimeZone.getTimeZone("America/Detroit"));
        formattedDate = format.format(date);
        return formattedDate;
    }
    @GetMapping("weather/output/{latitude},{longitude},{timestamp}")
    public ArrayNode combineData(@PathVariable("latitude") String latitude, @PathVariable("longitude") String longitude, @PathVariable("timestamp") String timestamp) throws ResourceNotFoundException {
        ObjectMapper mapper= new ObjectMapper();
        ArrayNode arr= mapper.createArrayNode();
        JsonNode current= getDetails(latitude,longitude);
        JsonNode last= getlastyearDetails(latitude,longitude,timestamp);
        arr.add(current);
        arr.add(last);
        return arr;
    }
}
