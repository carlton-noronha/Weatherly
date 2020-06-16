package com.example.weatherly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private final static int SUCCESS_CODE = 200;
    private final static double KELVIN_CONSTANT = 273.15;

    private TextView cityNotFound;
    private TextView tvWeatherStatus;
    private TextView tvWeatherDescription;
    private TextView tvWeatherTemperature;
    private ImageView ivWeatherImage;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityNotFound = findViewById(R.id.city_not_found);
        tvWeatherStatus = findViewById(R.id.weather_status);
        tvWeatherDescription = findViewById(R.id.weather_description);
        tvWeatherTemperature = findViewById(R.id.weather_temperature);
        ivWeatherImage = findViewById(R.id.weather_image);
        mProgressBar = findViewById(R.id.progressBar);

        String mApiURL;

        mApiURL = getIntent().getStringExtra("API_URL");

        Bundle link = new Bundle();
        link.putString("mAPIURL", mApiURL);

        setVisibilityOfViews(View.GONE, View.GONE, View.GONE, View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        if(getSupportLoaderManager().getLoader(0) == null) {
            getSupportLoaderManager().restartLoader(0, link,this);
        } else {
            getSupportLoaderManager().initLoader(0, null,this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String mApiURL = "";
        if(args != null){
            mApiURL = args.getString("mAPIURL");
        }
        return new FetchWeatherDataLoader(this, mApiURL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mProgressBar.setVisibility(View.GONE);
        try {
            if(data == null){
                cityNotFound.setVisibility(View.VISIBLE);
                return;
            }
            JSONObject jsonObject = new JSONObject(data);
            final String RESPONSE_CODE = jsonObject.getString("cod");
            if(SUCCESS_CODE == Integer.parseInt(RESPONSE_CODE)) {
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                String weatherStatus = weatherArray.getJSONObject(0).getString("main");
                String weatherStatusFinal = "Status: " + weatherStatus;
                String weatherDescription = "Description: " +
                        weatherArray.getJSONObject(0).getString("description");
                JSONObject mainObject = jsonObject.getJSONObject("main");
                String weatherTemperature = "Temperature: " +
                        Math.round(Double.parseDouble(mainObject.getString("temp")) -
                                KELVIN_CONSTANT);
                tvWeatherStatus.setText(weatherStatusFinal);
                tvWeatherDescription.setText(weatherDescription);
                tvWeatherTemperature.setText(weatherTemperature);

                switch (weatherStatus) {
                    case "Thunderstorm":
                        ivWeatherImage.setImageResource(R.drawable.thunderstorm_weather);
                        break;
                    case "Clouds":
                        ivWeatherImage.setImageResource(R.drawable.clouds_weather);
                        break;
                    case "Clear":
                    case "Sunny":
                        ivWeatherImage.setImageResource(R.drawable.sunny_weather);
                        break;
                    case "Windy":
                    case "Wind":
                        ivWeatherImage.setImageResource(R.drawable.windy_weather);
                        break;
                    case "Rain":
                        ivWeatherImage.setImageResource(R.drawable.rainy_weather);
                        break;
                    case "Haze":
                        ivWeatherImage.setImageResource(R.drawable.haze_weather);
                        break;

                }
                setVisibilityOfViews(View.VISIBLE, View.VISIBLE, View.VISIBLE, View.VISIBLE);
            }
            else {
                cityNotFound.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            cityNotFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private void setVisibilityOfViews(int weatherImageView, int weatherStatus,
                                      int weatherDescription, int weatherTemperature) {
        ivWeatherImage.setVisibility(weatherImageView);
        tvWeatherStatus.setVisibility(weatherStatus);
        tvWeatherDescription.setVisibility(weatherDescription);
        tvWeatherTemperature.setVisibility(weatherTemperature);

    }

}