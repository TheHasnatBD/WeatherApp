package bd.com.infobox.weather.Adapter;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bd.com.infobox.weather.Constants.Constant;
import bd.com.infobox.weather.Fragments.CurrentWeatherFragment;
import bd.com.infobox.weather.Fragments.ForecastWeatherFragment;
import bd.com.infobox.weather.MainActivity;

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        // passing lat, lng values through ADAPTER into Fragments
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", Constant.defaultLatLng.DEFAULT_LAT);
        bundle.putDouble("lng", Constant.defaultLatLng.DEFAULT_LNG);

        switch (i){
            case 0:
                CurrentWeatherFragment currentWeatherFragment = new CurrentWeatherFragment();
                currentWeatherFragment.setArguments(bundle);
                return currentWeatherFragment;

            case 1:
                ForecastWeatherFragment forecastWeatherFragment = new ForecastWeatherFragment();
                forecastWeatherFragment.setArguments(bundle);
                return forecastWeatherFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }



}