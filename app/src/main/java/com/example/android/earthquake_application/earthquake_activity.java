package com.example.android.earthquake_application;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class earthquake_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake_information>> {
    private static final String SOURCE = " http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-11-01&limit=20";
    private EarthquakeArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_activity);
        adapter = new EarthquakeArrayAdapter(earthquake_activity.this, new ArrayList<Earthquake_information>());
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake_information cur = adapter.getItem(position);
                Uri uri = Uri.parse(cur.getUrl());
                Intent website = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(website);
            }
        });
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
    }
    @Override
    public Loader<List<Earthquake_information>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this,SOURCE);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake_information>> loader, List<Earthquake_information> data) {
        adapter.clear();
        if(data != null && !data.isEmpty()){
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake_information>> loader) {
        adapter.clear();
    }


}
