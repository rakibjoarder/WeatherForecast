package com.aust.rakib.weatherforecast.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aust.rakib.weatherforecast.Activity.MainActivity;
import com.aust.rakib.weatherforecast.R;

import java.util.ArrayList;

/**
 * Created by Personal on 7/12/2017.
 */

public class currentListviewAdapter extends BaseAdapter {
    MainActivity mainActivity =new MainActivity();

    Context context;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String>values=new ArrayList<>();
    public currentListviewAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return 8;
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
        convertView=layoutInflater.inflate(R.layout.currentweather_list_row,parent,false);

        TextView name= (TextView) convertView.findViewById(R.id.nameTextview);
        TextView value= (TextView) convertView.findViewById(R.id.valueTextview);
    String unit=" ";
   if(mainActivity.unitFlag.equals("metric"))
   {
          unit="°C";
   }else if(mainActivity.unitFlag.equals("imperial"))
   {
          unit="°F";
   }

        names.add("Humidity");
        values.add(String.format("%d %%",mainActivity.foreCastLists.get(position).getHumidity()));
        names.add("Pressure");
        values.add(String.format("%.2f hpa",mainActivity.foreCastLists.get(position).getPressure()));
        names.add("Wind speed");
        values.add(String.format("%.2f m/s ", mainActivity.foreCastLists.get(position).getSpeed()));
        names.add("Wind direction");
        values.add(String.format(" %d  ", mainActivity.foreCastLists.get(position).getDeg()));
        names.add("Cloudiness");
        values.add(String.format("%d%% ", mainActivity.foreCastLists.get(position).getClouds()));
        names.add("Morning temperature");
        values.add(String.format(" %.1f%s",mainActivity.foreCastLists.get(position).getTemp().getMorn(),unit));
        names.add("Evening temperature");
        values.add(String.format(" %.1f%s",mainActivity.foreCastLists.get(position).getTemp().getEve(),unit));
        names.add("Night temperature");
        values.add(String.format(" %.1f%s",mainActivity.foreCastLists.get(position).getTemp().getNight(),unit));



        name.setText(names.get(position));
        value.setText(values.get(position));




        return  convertView;

    }
}

