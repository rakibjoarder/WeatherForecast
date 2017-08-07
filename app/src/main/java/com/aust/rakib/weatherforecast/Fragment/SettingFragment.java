package com.aust.rakib.weatherforecast.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.aust.rakib.weatherforecast.R;
import com.aust.rakib.weatherforecast.settingslistener;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    RadioButton metricradioButton;
    RadioButton imperialradioButton;
    static boolean flag=true;
    settingslistener settings;
    public SettingFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_setting, container, false);
        metricradioButton= (RadioButton) view.findViewById(R.id.metricRB);
        imperialradioButton= (RadioButton) view.findViewById(R.id.imperialRB);
        metricradioButton.setOnClickListener(this);
        imperialradioButton.setOnClickListener(this);
       if(flag==true)
       {
           metricradioButton.setChecked(true);
       }
       else {
           imperialradioButton.setChecked(true);
       }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings= (settingslistener) getActivity();
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.metricRB: {

                if(checked)
                {
                    settings.getCheck2Flag(10);
                    settings.getFlag("metric");
                    flag=true;
                }
                break;
            }
            case R.id.imperialRB: {

                if(checked)
                {
                    settings.getCheck2Flag(10);
                    settings.getFlag("imperial");
                    flag=false;
                }
                break;
            }

        }
    }
}
