package com.aust.rakib.weatherforecast.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aust.rakib.weatherforecast.Adapter.RecyclerViewAdapter;
import com.aust.rakib.weatherforecast.Activity.MainActivity;
import com.aust.rakib.weatherforecast.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {
     TextView cityTexview,maxTextview,minTextview,tempTextView,dateTextview,descriptionTextview;
    ImageView imageView;
    ListView listView;
    MainActivity mainActivity =new MainActivity();
    RecyclerView recyclerView;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        cityTexview= (TextView) view.findViewById(R.id.CityNameTV);
        tempTextView= (TextView) view.findViewById(R.id.Temptextview);
        maxTextview= (TextView)  view.findViewById(R.id.MaxTemptextview);
        minTextview= (TextView)  view.findViewById(R.id.MinTemptextview);
        dateTextview= (TextView)  view.findViewById(R.id.dateTextview);
        descriptionTextview= (TextView)  view.findViewById(R.id.descriptiontextview);
        imageView= (ImageView)  view.findViewById(R.id.imageView);


        recyclerView= (RecyclerView) view.findViewById(R.id.list_view);


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


         try {
             long l= Long.valueOf(mainActivity.foreCastLists.get(0).getDt())*1000;
             Date d = new Date(Long.valueOf(l));
             SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
             SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
             String datetime = dateFormat.format(d);  // formatted date in string
             String date = dateFormat2.format(d);  // formatted date in string
             String unit="";


             if(mainActivity.unitFlag.equals("metric"))
             {
                 unit="°C";
             }else if(mainActivity.unitFlag.equals("imperial"))
             {
                 unit="°F";
             }

             cityTexview.setText(  String.format("%s,%s",mainActivity.cities.getName(),mainActivity.cities.getCountry()));
             Picasso.with(getActivity()).load(String.format("http://openweathermap.org/img/w/%s.png",mainActivity.foreCastLists.get(0).getWeather().get(0).getIcon())).into(imageView);
             tempTextView.setText(String.format("%.1f%s",mainActivity.foreCastLists.get(0).getTemp().getDay(),unit));
             maxTextview.setText(String.format(" ↓%.1f%s",mainActivity.foreCastLists.get(0).getTemp().getMin(),unit));
             minTextview.setText(String.format(" ↑%.1f%s",mainActivity.foreCastLists.get(0).getTemp().getMax(),unit));
             descriptionTextview.setText(mainActivity.foreCastLists.get(0).getWeather().get(0).getDescription());
             dateTextview.setText(String.format("%s",date));
         }catch (Exception e){

         }
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getActivity(),MainActivity.foreCastLists);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}
