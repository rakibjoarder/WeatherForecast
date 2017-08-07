package com.aust.rakib.weatherforecast.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aust.rakib.weatherforecast.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationSetFragment extends Fragment {


    public LocationSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_set, container, false);
    }

}
