package com.example.finalproject;

import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.SQLOutput;

public final class MainActivity extends AppCompatActivity {

    private static final String Tag = "FinalProject:Main";
    private static RequestQueue requestQueue;
    private String request;

    public boolean parseHelp(String request, String country, int date) {
        if (request != null) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(request).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).getAsJsonObject().getAsJsonPrimitive("countryCode").getAsString().equals(country)) {
                    String[] first = array.get(i).getAsJsonObject().getAsJsonPrimitive("date").getAsString().trim().split("-");
                    for (int j = 0; j < first.length; j++) {
                        if (Integer.parseInt(first[i]) == date) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            final EditText inputCountry = findViewById(R.id.CountryInput);
            final EditText inputDate = findViewById(R.id.DateInput);
            String inputCountryString = inputCountry.getText().toString();
            String inputDateString = inputDate.getText().toString();
            int dateInt = Integer.parseInt(inputDateString);
            StartAPICall(inputCountryString, inputDateString);
            parseHelp(request, inputCountryString, dateInt);
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    void StartAPICall(final String inputCode, final String inputDate) {
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    "https://date.nager.at/api/v2/publicholidays/" + inputDate + "/" + inputCode,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            apiCallDone(response);
                            request = response.toString();
                            Log.i(Tag, request);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(Tag, error.toString());
                }
            });
            jsonArrayRequest.setShouldCache(false);
            requestQueue.add(jsonArrayRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String[][] getInfo(final JSONArray inputArray) {
        String[][] store = new String[inputArray.length()][2];
        for (int i = 0; i < inputArray.length(); i++) {
            try {
                store[i][0] = inputArray.getJSONObject(i).getString("name");
                store[i][1] = inputArray.getJSONObject(i).getString("date");
            } catch (Exception e) {
            }
        }
        return store;
    }




    void apiCallDone(final JSONArray COUNTRYJson) {
        TextView Output = findViewById(R.id.Output);
        String[][] array = getInfo(COUNTRYJson);
        for (int i = 0; i < COUNTRYJson.length(); i++) {
            Output.setText(Output.getText() + "Holiday Name: " + array[i][0] +  ", " + "\nDate: " + array[i][1] + "\n");
        }
    }
}
