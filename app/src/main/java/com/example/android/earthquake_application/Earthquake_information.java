package com.example.android.earthquake_application;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This is a Class for each earthquake information.
 * It contains information: magtitude, the address, the date, and the original url from USGS
 */

public class Earthquake_information {
    private Double magtitude;
    private String city;
    private DateFormat dateFormat = new SimpleDateFormat("MMM-dd,yyyy", Locale.ENGLISH);
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm a", Locale.ENGLISH);
    private Date date = new Date();
    private String url;
    private int beginIndex ;

    public Earthquake_information(double magtitude, String city, Date date, String url) {
        this.magtitude = magtitude;
        this.city = city;
        this.date = date;
        this.url = url;
    }
    public void getIndex(){
        this.beginIndex = city.indexOf("of");
        if(beginIndex == -1){
            beginIndex = 0;
        }
        else{
            beginIndex += 3;
        }
    }
    public double getMagnitude(){
        return magtitude;
    }

    public String getMagnitudeString() {
        DecimalFormat format = new DecimalFormat("0.0");
        return format.format(magtitude);
    }

    public String getCity() {
        getIndex();
        String cityName = city.substring(0,beginIndex);
        return cityName;
    }
    public String getCountry(){
        getIndex();
        return city.substring(beginIndex);
    }

    public String getDate() {

        return dateFormat.format(date);
    }
    public String getTime() {

        return timeFormat.format(date);
    }
    public String getUrl(){
        return url;
    }

}
