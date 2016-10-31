package com.example.android.earthquake_application;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class earthquake_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_activity);
        ArrayList<Earthquake_information> earthQuake = QueryUtils.extractEarthquakes();
        final EarthquakeArrayAdapter adapter = new EarthquakeArrayAdapter(earthquake_activity.this,
                                                                    earthQuake);
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
}
