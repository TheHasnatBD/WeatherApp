package bd.com.infobox.weather.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import bd.com.infobox.weather.Fragments.CurrentWeatherFragment;
import bd.com.infobox.weather.Fragments.ForecastWeatherFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new CurrentWeatherFragment();
            case 1:
                return new ForecastWeatherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


}