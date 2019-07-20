package xyz.hasnat.weather.view;

import android.content.Intent;
import android.graphics.Color;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import cn.zhaiyifan.rememberedittext.RememberEditText;
import xyz.hasnat.weather.R;
import xyz.hasnat.weather.adapter.TabPagerAdapter;

public class MainActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private xyz.hasnat.weather.adapter.TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Set Home Activity Toolbar Name
         */
        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab2));
        tabLayout.setSelectedTabIndicatorColor(Color.GRAY);

        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    } // ending onCreate


    /** options Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.menu_clear_history:
                RememberEditText.clearCache(this);
                break;
            case R.id.menu_about:
                // Custom Alert Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.about_dailog, null);

                //TextView instructor = view.findViewById(R.id.title);

                builder.setCancelable(true);
                builder.setView(view);
                builder.show();

                break;
        }
        return true;
    }


    /** FragmentPagerAdapter  */ /*
    private class TabPagerAdapter extends FragmentPagerAdapter {
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
    } */

}
