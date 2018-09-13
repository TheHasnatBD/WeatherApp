package bd.com.infobox.weather.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bd.com.infobox.weather.MainActivity;
import bd.com.infobox.weather.Model.CurrentWeatherPick.CurrentWeatherResponse;
import bd.com.infobox.weather.Model.CurrentWeatherPick.Main;
import bd.com.infobox.weather.R;
import bd.com.infobox.weather.Services.CurrentWeatherPick.CurrentWeatherServiceAPI;
import bd.com.infobox.weather.Services.RetrofitClient;
import bd.com.infobox.weather.Utils.TimeAndDateConverter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment {

    private TextView temp, city, country, temp_des, sunrise, sunset;
    private ImageView temp_icon;
    private Context context;
    private TimeAndDateConverter timeAndDateConverter;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        temp = view.findViewById(R.id.tempTV);
        city = view.findViewById(R.id.cityTV);
        temp_des = view.findViewById(R.id.descriptionWeather);
        temp_icon = view.findViewById(R.id.weatherImage);
        sunrise = view.findViewById(R.id.sunriseTV);
        sunset = view.findViewById(R.id.sunsetTV);

        CurrentWeatherServiceAPI weatherServiceAPI = RetrofitClient.getClient(MainActivity.WEATHER_BASE_URL).create(CurrentWeatherServiceAPI.class);
        String apiKey = getString(R.string.weather_api_key);
        String weather_url = String.format("weather?q=dhaka&units=metric&appid=%s", apiKey);

        weatherServiceAPI.getCurrentWeatherResponse(weather_url)
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        if (response.isSuccessful()){
                            CurrentWeatherResponse currentWeatherResponse = response.body();

                            temp.setText(currentWeatherResponse.getMain().getTemp().toString()+" \u2103");
                            temp_des.setText(currentWeatherResponse.getWeather().get(0).getDescription());
                            city.setText(currentWeatherResponse.getName());
                            sunrise.setText("Sunrise: "+getTime(currentWeatherResponse.getSys().getSunrise()) +", ");
                            sunset.setText("Sunset: "+getTime(currentWeatherResponse.getSys().getSunset()));

                            Toast.makeText(getContext(), currentWeatherResponse.getCoord().getLat()+ " << Lat || Lon >> "+currentWeatherResponse.getCoord().getLon(), Toast.LENGTH_SHORT).show();


                            String weatherIcon = currentWeatherResponse.getWeather().get(0).getIcon();
                            String iconUrl = MainActivity.WEATHER_IMAGE_BASE_URL+weatherIcon+".png";

                            Picasso.get().load(iconUrl).into(temp_icon);
                        }

                    }

                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {

                    }
                });

        return view;
    }

    private String getTime(long timeInSeconds){
        Date date = new Date(timeInSeconds * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String timeString = dateFormat.format(date);

        return timeString;
    }



}
