package com.example.android.earthquake_application;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private QueryUtils() {
    }
    public static ArrayList<Earthquake_information> fetchData(String source){
        URL url = getURL(source);
        String jresponse = null;
        jresponse = getHttpRequest(url);
        return extractEarthquakes(jresponse);
    }
    public static URL getURL(String source) {
        URL url = null;
        try {
            url = new URL(source);
        } catch ( MalformedURLException e ) {
            Log.e(LOG_TAG,"fail to get URL",e);
        }
        return url;
    }
    public static String getHttpRequest(URL url){
        String jresponse = null;
        if(url == null){
            return jresponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputstream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputstream = urlConnection.getInputStream();
                jresponse = readInputString(inputstream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch ( ProtocolException e ) {
            Log.e(LOG_TAG,"fail to connect",e);
        } catch ( IOException e ) {
            Log.e(LOG_TAG,"problem receive Jresponse",e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputstream != null){
                try {
                    inputstream.close();
                } catch ( IOException e ) {
                    Log.e(LOG_TAG,"error cloing input stream",e);
                }
            }
        }
        return jresponse;
    }
    public static String readInputString(InputStream inputstream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputstream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder str = new StringBuilder();
        String line = bufferedReader.readLine();
        while(line != null){
            str.append(line);
            line = bufferedReader.readLine();
        }
        return str.toString();
    }
    public  static ArrayList<Earthquake_information> extractEarthquakes(String json) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        ArrayList<Earthquake_information> earthquakes = new ArrayList<>();
        try {

            JSONObject root = new JSONObject(json);
            JSONArray features = root.getJSONArray("features");
            Date date;
            for(int i = 0; i< features.length(); i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double magtitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                date = new Date(time);
                String url = properties.getString("url");
                earthquakes.add(new Earthquake_information(magtitude,place,date,url));
            }
        } catch ( JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}