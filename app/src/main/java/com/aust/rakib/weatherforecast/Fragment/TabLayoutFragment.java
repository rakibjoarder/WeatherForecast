package com.aust.rakib.weatherforecast.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aust.rakib.weatherforecast.R;
import com.aust.rakib.weatherforecast.Adapter.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabLayoutFragment extends Fragment {

    Toolbar toolbar;
    TabLayout tablayout;
    ViewPager viewpager;
    ViewPagerAdapter viewPagerAdapter;
    public TabLayoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_layout, container, false);



        tablayout= (TabLayout) view.findViewById(R.id.tablayout);
        viewpager= (ViewPager) view.findViewById(R.id.viewpager);
        viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragments(new CurrentWeatherFragment(),"Current Weather");
        viewPagerAdapter.addFragments(new ForecastWeatherFragment(),"Forecast Weather");
        viewpager.setAdapter(viewPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPagerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

}
