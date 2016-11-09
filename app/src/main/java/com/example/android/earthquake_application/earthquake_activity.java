package com.example.android.earthquake_application;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.id.empty;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class earthquake_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake_information>> {
    private static final String SOURCE = " http://earthquake.usgs.gov/fdsnws/event/1/query";
    private EarthquakeArrayAdapter adapter;
    private TextView emptyTextView;
    private View loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_activity);
        emptyTextView = (TextView)findViewById(R.id.empty_view);
        adapter = new EarthquakeArrayAdapter(earthquake_activity.this, new ArrayList<Earthquake_information>());
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(emptyTextView);
        loadingIndicator = findViewById(R.id.loading_indicator);
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
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1,null,this);
        }else{
            emptyTextView.setText("No Internet Connection.");
            loadingIndicator.setVisibility(View.GONE);
        }
    }
    @Override
    public Loader<List<Earthquake_information>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        String limit = sharedPrefs.getString(
                "limit",
                "20"
        );

        Uri baseUri = Uri.parse(SOURCE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", limit);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake_information>> loader, List<Earthquake_information> data) {
        emptyTextView.setText("No Earthquake Found.");
        loadingIndicator.setVisibility(View.GONE);
        adapter.clear();
        if(data != null && !data.isEmpty()){
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake_information>> loader) {
        adapter.clear();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
