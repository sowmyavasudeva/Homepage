package com.example.megha.homepage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class EventActivity extends AppCompatActivity {

    private String TAG = EventActivity.class.getSimpleName();

    private ProgressBar pDialog;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=jjkUFF0igYhuCqPDEjSq7DDP3zhGRS3b&postalCode=98109&unit=miles&size=2";

    ArrayList<HashMap<String, String>> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        eventList = new ArrayList<>();

        lv = findViewById(R.id.list);

        new GetEvents().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetEvents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Object _embedded node
                    JSONObject embedded = jsonObj.getJSONObject("_embedded");
                    // Getting JSON Array events node
                    JSONArray eventDetails = embedded.getJSONArray("events");
                    // looping through All Contacts
                    for (int i = 0; i < eventDetails.length(); i++) {
                        JSONObject getEventDetails = eventDetails.getJSONObject(i);

                        String name = getEventDetails.getString("name");
                        String id = getEventDetails.getString("id");

                        // tmp hash map for single contact
                        HashMap<String, String> event = new HashMap<>();

                        // adding each child node to HashMap key => value
                        event.put("name", name);
                        event.put("id", id);

                        // adding contact to contact list
                        eventList.add(event);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    EventActivity.this, eventList,
                    R.layout.list_item, new String[]{"name", "id"},
                    new int[]{R.id.name, R.id.id});

            lv.setAdapter(adapter);
        }

    }
}