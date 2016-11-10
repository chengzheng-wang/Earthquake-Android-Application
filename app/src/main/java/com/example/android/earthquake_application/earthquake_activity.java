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

/* main activity*/

public class earthquake_activity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake_information>> {
    private static final String SOURCE = " http://earthquake.usgs.gov/fdsnws/event/1/query";
    private EarthquakeArrayAdapter adapter;  //Arrayadapter for the listview
    private TextView emptyTextView;   // textview to indicate no internet or no data found
    private View loadingIndicator;    //progress bar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Layout inside: A relativeview contains a Listview, a textview and a progress bar */
        setContentView(R.layout.activity_earthquake_activity);
        emptyTextView = (TextView)findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading_indicator);

        /** set the Arrayadapter. EarthquakeArrayAdapter is a class extends Arrayadapter
        * which contains list of objects of Class Earthquake_Information
        */
        adapter = new EarthquakeArrayAdapter(earthquake_activity.this, new ArrayList<Earthquake_information>());
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setEmptyView(emptyTextView);
        earthquakeListView.setAdapter(adapter);

        /* onclickListener will redirect to USGS website for more details */
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake_information cur = adapter.getItem(position);
                Uri uri = Uri.parse(cur.getUrl());
                Intent website = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(website);
            }
        });

        /* Internet connection checking */
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
    /** Methods override in  LoaderManager.LoaderCallbacks interface
     * Loader here is used to solve the asyntask(ie. interact with the internet on the backgroud)problem
     */

    @Override
    /** In onCreateLoaderfunction , it build the url for query based on the user preference on three aspects:
     * limit amounts of information to show;
     * minimum Magnitude
     * order by time or magnitude
     * It then fetch the Data by calling the function fethData in Class QueryUtils in the background
     */
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
                getString(R.string.settings_limit_key),
                getString(R.string.settings_limit_default)
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
    /* In onLoadFinished function, it push the data(the list) into the ArrayAdapter*/
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

    /**
     * Override methods: onCreateOptionsMenu,onOptionsItemSelected
     *  are used to the menus for preferences settings
     *  the menu main contains an icon for entering the SettingsActivity;
     *  The layout for SettingsActivity is a fragment which contains
     */
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
