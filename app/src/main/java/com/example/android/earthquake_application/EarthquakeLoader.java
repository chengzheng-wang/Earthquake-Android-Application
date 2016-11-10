package com.example.android.earthquake_application;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/**
 * This Loader extends from AsyncTaskLoader, it receives a string as a url, and return a list of Earthquake_information,
 * by calling the static function fetchData in class QueryUtils
 *
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
