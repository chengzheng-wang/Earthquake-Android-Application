package com.example.android.earthquake_application;

import android.content.Intent;
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

public class earthquake_activity extends AppCompatActivity {
    private static final String SOURCE = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    private EarthquakeArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_activity);

        earthquakeAsync task = new earthquakeAsync();
        task.execute(SOURCE);
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
    }
    private class earthquakeAsync extends AsyncTask<String, Void, List<Earthquake_information>>{

        @Override
        protected List<Earthquake_information> doInBackground(String... params) {
            if(params.length <= 0 || params[0] == null){
                return null;
            }
            ArrayList<Earthquake_information> earthQuake = QueryUtils.extractEarthquakes(params[0]);
            return earthQuake;
        }

        @Override
        protected void onPostExecute(List<Earthquake_information> data) {
            adapter.clear();
            if(data != null && !data.isEmpty()){
                adapter.addAll(data);
            }

        }
    }
}
