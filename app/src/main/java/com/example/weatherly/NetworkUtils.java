package com.example.weatherly;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    public static String getJSONData(String apiURL) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;

        try {

            Log.i("API URL: ", apiURL);

            URL mApiURL = new URL(apiURL);

            httpURLConnection = (HttpURLConnection) mApiURL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String JSONData = null;

            StringBuilder JSONDataStringBuilder = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                JSONDataStringBuilder.append(line);
            }

            JSONData = JSONDataStringBuilder.toString();

            Log.i("JSON data: ", JSONData);

            return JSONData;

        }
        catch(IOException IOE){
            IOE.printStackTrace();
            return null;
        }
        finally{
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


