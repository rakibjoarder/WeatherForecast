package com.aust.rakib.weatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aust.rakib.weatherforecast.Activity.MainActivity;
import com.aust.rakib.weatherforecast.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Personal on 7/12/2017.
 */

public class forecastListviewAdapter extends BaseAdapter {
    MainActivity mainActivity =new MainActivity();

    Context context;

    public forecastListviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return mainActivity.foreCastLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.custom_list_row,parent,false);
        TextView max= (TextView) convertView.findViewById(R.id.maxtextView);
        TextView min= (TextView) convertView.findViewById(R.id.mintextView2);
        TextView wind= (TextView) convertView.findViewById(R.id.windspeedtextView4);
        TextView  pressure= (TextView) convertView.findViewById(R.id.pressuretextView6);
        TextView humidity= (TextView) convertView.findViewById(R.id.humiditytextView5);
        TextView dateTV= (TextView) convertView.findViewById(R.id.dateTextview7);
        TextView description= (TextView) convertView.findViewById(R.id.descriptiontextView3);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.iconImageView);


        long l= Long.valueOf(mainActivity.foreCastLists.get(position).getDt())*1000;
        Date d = new Date(Long.valueOf(l));
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
        String date = dateFormat2.format(d);  // formatted date in string
       String unit=" ";
        if(mainActivity.unitFlag.equals("metric"))
        {
            unit="°C";
        }else if(mainActivity.unitFlag.equals("imperial"))
        {
            unit="°F";
        }

        Picasso.with(context).load(String.format("http://openweathermap.org/img/w/%s.png",mainActivity.foreCastLists.get(position).getWeather().get(0).getIcon())).into(imageView);
        min.setText(String.format(" ↓%.1f%s",mainActivity.foreCastLists.get(position).getTemp().getMin(),unit));
        max.setText(String.format(" ↑%.1f%s",mainActivity.foreCastLists.get(position).getTemp().getMax(),unit));
        description.setText(mainActivity.foreCastLists.get(position).getWeather().get(0).getDescription());
        pressure.setText(String.format("%.2f hpa", mainActivity.foreCastLists.get(position).getPressure()));
        humidity.setText(String.format("clouds: %d%% ", mainActivity.foreCastLists.get(position).getClouds()));
        wind.setText(String.format("%.2fm/s  ", mainActivity.foreCastLists.get(position).getSpeed()));
        dateTV.setText(String.format("%s",date));




        return  convertView;

    }
}
