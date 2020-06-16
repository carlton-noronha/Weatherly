package com.example.weatherly;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class FetchWeatherDataLoader extends AsyncTaskLoader<String>{

    private String mApiURL;

    public FetchWeatherDataLoader(@NonNull Context context, String apiURL) {
        super(context);
        mApiURL = apiURL;
    }

    @Override
    protected void onStartLoading() {
        onForceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getJSONData(mApiURL);
    }
}
