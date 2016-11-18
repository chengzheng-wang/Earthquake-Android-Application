package com.example.android.earthquake_application;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;
import static com.example.android.earthquake_application.R.id.magnitudeView;

/**
 * This class is an ArrayAdapter to contain the information for each list
 * each item is an object of class Earthquake_Information
 * The layout is list_item.xml, which is the main body of the whole apps.
 */

public class EarthquakeArrayAdapter extends ArrayAdapter<Earthquake_information> {
    public EarthquakeArrayAdapter(Context context, List<Earthquake_information> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        Earthquake_information cur = getItem(position);
        TextView magnitudeView = (TextView)listItemView.findViewById(R.id.magnitudeView);
        TextView cityView = (TextView)listItemView.findViewById(R.id.cityView);
        TextView countryView = (TextView)listItemView.findViewById(R.id.countryView);
        TextView dateView = (TextView)listItemView.findViewById(R.id.dateView);
        TextView timeView = (TextView)listItemView.findViewById(R.id.timeView);
        magnitudeView.setText(cur.getMagnitudeString());
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(cur.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        countryView.setText(cur.getCountry());
        cityView.setText(cur.getCity());
        dateView.setText(cur.getDate());
        timeView.setText(cur.getTime());
        return listItemView;
    }

    /* This function is to set the backgroud color of the magnitude circle based on the level */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
