package com.example.finalproject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class InfoFinder {
    public InfoFinder() {
    }
    public static Date getDate(String json) {
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            if (result.has("date")) {
                try {
                    String date = result.getAsJsonObject("date").getAsString();
                    Date dateNew = new SimpleDateFormat("yyyy").parse(date);
                    return dateNew;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static String getCode(String json) {
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            if (result.has("countryCode")) {
                String code = result.getAsJsonObject("countryCode").getAsString();
                return code;
            }
        }
        return null;
    }

}
