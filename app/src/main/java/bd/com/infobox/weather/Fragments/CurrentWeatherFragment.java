package bd.com.infobox.weather.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bd.com.infobox.weather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {


    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

}
