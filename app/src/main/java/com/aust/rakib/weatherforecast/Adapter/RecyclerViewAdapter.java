package com.aust.rakib.weatherforecast.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aust.rakib.weatherforecast.Api.WeatherForecastResponse;
import com.aust.rakib.weatherforecast.Activity.MainActivity;
import com.aust.rakib.weatherforecast.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Personal on 7/25/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewsHolder> {

    Context context;
    ArrayList<String> names=new ArrayList<>();
    ArrayList<String>values=new ArrayList<>();
    MainActivity mainActivity=new MainActivity();
    public static List<WeatherForecastResponse.ForeCastList> foreCastLists;

    public RecyclerViewAdapter(Context context,List<WeatherForecastResponse.ForeCastList>foreCastLists) {
        this.context = context;

        this.foreCastLists=foreCastLists;
    }

    public class ViewsHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView value;
        public ViewsHolder(View itemView) {
            super(itemView);
            name= (TextView)  itemView.findViewById(R.id.nameTextview);
            value= (TextView) itemView.findViewById(R.id.valueTextview);
        }
    }

    @Override
    public ViewsHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.currentweather_list_row,parent,false);
        return new ViewsHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewsHolder holder, int position) {
        Log.e("AAA", "asdsa");
        String unit=" ";
        if(mainActivity.unitFlag.equals("metric"))
        {
            unit="°C";
        }else if(mainActivity.unitFlag.equals("imperial"))
        {
            unit="°F";
        }

        names.add("Humidity");
        values.add(String.format("%d %%",foreCastLists.get(position).getHumidity()));
        names.add("Pressure");
        values.add(String.format("%.2f hpa",foreCastLists.get(position).getPressure()));
        names.add("Wind speed");
        values.add(String.format("%.2f m/s ",foreCastLists.get(position).getSpeed()));
        names.add("Wind direction");
        values.add(String.format(" %d  ",foreCastLists.get(position).getDeg()));
        names.add("Cloudiness");
        values.add(String.format("%d%% ",foreCastLists.get(position).getClouds()));
        names.add("Morning temperature");
        values.add(String.format(" %.1f%s",foreCastLists.get(position).getTemp().getMorn(),unit));
        names.add("Evening temperature");
        values.add(String.format(" %.1f%s",foreCastLists.get(position).getTemp().getEve(),unit));
        names.add("Night temperature");
        values.add(String.format(" %.1f%s",foreCastLists.get(position).getTemp().getNight(),unit));

        holder.name.setText(names.get(position));
        holder.value.setText(values.get(position));

    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
