package com.aust.rakib.weatherforecast.Activity;


import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;
import com.aust.rakib.weatherforecast.Api.WeatherForecastResponse;
import com.aust.rakib.weatherforecast.Fragment.AboutUsFragment;
import com.aust.rakib.weatherforecast.Fragment.LocationSetFragment;
import com.aust.rakib.weatherforecast.Fragment.SettingFragment;
import com.aust.rakib.weatherforecast.R;
import com.aust.rakib.weatherforecast.settingslistener;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.aust.rakib.weatherforecast.Api.WeatherApiService;
import com.aust.rakib.weatherforecast.Fragment.TabLayoutFragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,settingslistener {


    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    Geocoder geocoder;
    private List<Address> getAddressList;
    private WeatherApiService weatherApiService;
    private static final String BASE_URL="http://api.openweathermap.org/data/2.5/";
    private static final String APPID="ce7853506472e8e0eed01ab669fa5b9f";

    private static final int CNT=17;
    public static  List<WeatherForecastResponse.ForeCastList>foreCastLists;
    public static   WeatherForecastResponse.City cities;
    boolean flag=true;
    public static String unitFlag="metric";
    public int check1=0;
    public int check2=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Weather Forecast");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        weatherApiService=retrofit.create(WeatherApiService.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder=new Geocoder(this);

        locationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {

                for(Location location : locationResult.getLocations())
                {
                    requestForecastResponse(location.getLatitude(),location.getLongitude(), unitFlag);
                }
            }


        };
        getLocationUpdate();

    }
    private void requestForecastResponse(double latitude, double longitude,String UNIT) {
        String urlString=String.format("http://api.openweathermap.org/data/2.5/forecast/daily?lat=%f&lon=%f&cnt=%d&units=%s&appid=%s",latitude,longitude,CNT,UNIT,APPID);
        final Call<WeatherForecastResponse> weatherForecastResponseCall=weatherApiService.weatherForecastResponseCall(urlString);
        weatherForecastResponseCall.enqueue(new Callback<WeatherForecastResponse>() {
            @Override
            public void onResponse(Call<WeatherForecastResponse> call, Response<WeatherForecastResponse> response) {
                if(response.code()==200)
                {
                    WeatherForecastResponse weatherForecastResponse=response.body();
                    foreCastLists=weatherForecastResponse.getList();
                    cities=weatherForecastResponse.getCity();

                    if(flag==true)
                    {   Fragment fragment=new TabLayoutFragment();
                        FragmentManager fm =getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.replace(R.id.container,fragment);
                        ft.commit();
                        flag=false;
                    }

                    if(check1==check2)
                    {
                        Fragment fragment=new TabLayoutFragment();
                        FragmentManager fm =getSupportFragmentManager();
                        FragmentTransaction ft=fm.beginTransaction();
                        ft.replace(R.id.container,fragment);
                        ft.commit();
                        check1=0;
                    }


                }
                stopLocationUpdates();
            }

            @Override
            public void onFailure(Call<WeatherForecastResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Please Check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    private void  startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    private void getLocationUpdate() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {


                startLocationUpdates();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(MainActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException sendEx) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        String errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in settingslistener.";
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }



    private void getlastLocation() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                Log.e("Latitude :",location.getLatitude()+"");
                Log.e("Longitude :",location.getLongitude()+"");

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Fragment fragment=null;

        if (id == R.id.nav_weather_report) {
            startLocationUpdates();
            fragment=new TabLayoutFragment();
            check1=10;
        }
        if (id == R.id.nav_location) {

            fragment=new LocationSetFragment();
        }
        else if (id == R.id.nav_setting) {
            fragment=new SettingFragment();


        }
        else if(id == R.id.nav_about_us) {
            fragment=new AboutUsFragment();

        }
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.container,fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void getFlag(String flag) {
        unitFlag=flag;
    }

    @Override
    public void getCheck2Flag(int flag) {
        check2=flag;
    }
}
