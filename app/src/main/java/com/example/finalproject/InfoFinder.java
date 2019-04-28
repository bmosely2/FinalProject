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
    public static int getDate(String json) {
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            JsonObject paths = result.getAsJsonObject("paths");
            JsonArray parameters = paths.getAsJsonArray("parameters");
            return parameters.get(1).getAsJsonObject().getAsJsonPrimitive("name").getAsInt();
        }
        return 0;
    }
    public static String getCode(String json) {
        if (json != null) {
            JsonParser parser = new JsonParser();
            JsonObject result = parser.parse(json).getAsJsonObject();
            JsonObject paths = result.getAsJsonObject("paths");
            JsonArray parameters = paths.getAsJsonArray("parameters");
            return parameters.get(0).getAsJsonObject().getAsJsonPrimitive("name").getAsString();
        }
        return null;
    }

}
