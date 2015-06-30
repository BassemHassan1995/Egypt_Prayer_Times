package com.bassem.prayertimes.server;

import android.net.Uri;
import android.util.Log;

import com.bassem.prayertimes.model.TaskParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Bassem on 6/25/2015.
 */
public class ServerImplementation {

    TaskParameters taskParameters;

    public ServerImplementation(TaskParameters taskParameters){
        this.taskParameters = taskParameters;
    }
    public String getJsonStr(TaskParameters taskParameters) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String prayertimeJsonStr = null;

        String format = "json";

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            final String PRAYERS_BASE_URL =
                    "http://api.xhanch.com/islamic-get-prayer-time.php?";
            final String LONGITUDE_PARAM = "lng";
            final String LATITUDE_PARAM = "lat";
            final String YEAR_PARAM = "yy";
            final String MONTH_PARAM = "mm";
            final String GMT_PARAM = "gmt";
            final String FORMAT_PARAM = "m";

            Uri builtUri = Uri.parse(PRAYERS_BASE_URL).buildUpon()
                    .appendQueryParameter(LONGITUDE_PARAM, String.valueOf(taskParameters.city.longitude))
                    .appendQueryParameter(LATITUDE_PARAM, String.valueOf(taskParameters.city.latitude))
                    .appendQueryParameter(YEAR_PARAM, String.valueOf(taskParameters.year))
                    .appendQueryParameter(MONTH_PARAM, String.valueOf(taskParameters.month))
                    .appendQueryParameter(GMT_PARAM, String.valueOf(taskParameters.gmt))
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.d("JSON" , builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();


            // Nothing to do.
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            /*if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }*/

            prayertimeJsonStr = buffer.toString();
            Log.d("JSON" , prayertimeJsonStr);

        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivity", "Error closing stream", e);
                }
            }
        }
        return prayertimeJsonStr;
    }
}

