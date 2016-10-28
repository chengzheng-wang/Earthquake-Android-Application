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
        ArrayList<Earthquake_information> earthQuake = new ArrayList<>();
        earthQuake.add(new Earthquake_information(7.2,"San Fransico",new Date(20160216)));
        earthQuake.add(new Earthquake_information(7.3,"San Jose",new Date(116,03,18)));
        earthQuake.add(new Earthquake_information(7.4,"New York",new Date(116,05,16)));
        earthQuake.add(new Earthquake_information(7.2,"San Fransico",new Date(116,02,16)));
        earthQuake.add(new Earthquake_information(7.3,"San Jose",new Date(116,03,18)));
        earthQuake.add(new Earthquake_information(7.4,"New York",new Date(116,05,16)));
        earthQuake.add(new Earthquake_information(7.2,"San Fransico",new Date(2016,02,16)));
        earthQuake.add(new Earthquake_information(7.3,"San Jose",new Date(116,03,18)));
        earthQuake.add(new Earthquake_information(7.4,"New York",new Date(116,05,16)));
        earthQuake.add(new Earthquake_information(7.2,"San Fransico",new Date(2016,02,16)));
        earthQuake.add(new Earthquake_information(7.3,"San Jose",new Date(116,03,18)));
        earthQuake.add(new Earthquake_information(7.4,"New York",new Date(116,05,16)));



        EarthquakeArrayAdapter adapter = new EarthquakeArrayAdapter(earthquake_activity.this,
                                                                    earthQuake);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);

    }
}
