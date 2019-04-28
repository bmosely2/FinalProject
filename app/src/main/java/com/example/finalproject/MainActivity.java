package com.example.finalproject;

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


public final class MainActivity extends AppCompatActivity {

    private static final String Tag = "FinalProject:Main";
    private static RequestQueue requestQueue;
    private String request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            final EditText inputCountry = findViewById(R.id.CountryInput);
            final EditText inputDate = findViewById(R.id.DateInput);
            String inputCombined = inputCountry.getText().toString() + inputDate.getText().toString();
            StartAPICall();
        });



    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    void StartAPICall() {
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    "https://date.nager.at/api/v2/publicholidays/2017/US", //+ "year/" + "countryCode/" + "/json"//,
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
    void apiCallDone(final JSONArray COUNTRYJson) {
        try {
            Log.d(Tag, COUNTRYJson.toString(2));
           // Log.i(Tag, COUNTRYJson.get("paths").toString() + COUNTRYJson.get("summary").toString());
            TextView Output = findViewById(R.id. Output);
            Output.setText(COUNTRYJson.get(0).toString());

        } catch (JSONException ignored) {
        }
    }

}
