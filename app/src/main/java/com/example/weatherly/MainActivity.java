package com.example.weatherly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    // Type of Query
    private final static String QUERY_TYPE = "weather";

    // Query Tag
    private final static String QUERY_TAG = "?q=";

    //API Key Tag
    private final static String API_KEY_TAG = "&appid=";

    private static final String API_KEY = "b5d5a5b64e02e3edd0d97a23ce032992";

    String mApiURL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;

        if(connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }

        if(activeNetwork != null && activeNetwork.isConnected()) {
            setContentView(R.layout.connected_layout);
            final EditText etCityName = findViewById(R.id.etCityName);
            Button btnFindWeather = findViewById(R.id.btnFindWeather);

            btnFindWeather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(etCityName.getText().toString().trim().isEmpty()){
                        Toast.makeText(MainActivity.this, getString(R.string.cityname), Toast.LENGTH_SHORT).show();
                    } else {
                        String cityName = etCityName.getText().toString().trim();
                        mApiURL = BASE_URL + QUERY_TYPE + QUERY_TAG + cityName + API_KEY_TAG + API_KEY;
                        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                        intent.putExtra("API_URL", mApiURL);
                        startActivity(intent);
                    }
                }
            });
        }
        else {
            setContentView(R.layout.not_connected_layout);
        }
    }
}