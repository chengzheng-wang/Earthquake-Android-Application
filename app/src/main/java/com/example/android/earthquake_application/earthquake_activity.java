package com.example.android.earthquake_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        EarthquakeArrayAdapter adapter = new EarthquakeArrayAdapter(earthquake_activity.this,
                                                                    earthQuake);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);

    }
}
