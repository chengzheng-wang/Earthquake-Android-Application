package com.example.android.earthquake_application;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengzheng on 2016/11/7.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake_information>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake_information> loadInBackground() {
        if(url == null){
            return null;
        }
        ArrayList<Earthquake_information> earthQuake = QueryUtils.fetchData(url);
        return earthQuake;
    }
}
