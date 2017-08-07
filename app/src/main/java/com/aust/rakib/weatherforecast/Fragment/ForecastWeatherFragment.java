package com.aust.rakib.weatherforecast.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aust.rakib.weatherforecast.R;
import com.aust.rakib.weatherforecast.Adapter.forecastListviewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastWeatherFragment extends Fragment {

  ListView listView;
    public ForecastWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        listView= (ListView) view.findViewById(R.id.list_view);
        forecastListviewAdapter adapter=new forecastListviewAdapter(getActivity());
        listView.setAdapter(adapter);
        return view;
    }

}
