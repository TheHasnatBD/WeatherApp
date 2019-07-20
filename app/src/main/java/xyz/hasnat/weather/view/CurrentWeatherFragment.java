package xyz.hasnat.weather.view;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.weather.R;
import xyz.hasnat.weather.api.RetrofitClient;
import xyz.hasnat.weather.api.WeatherServiceAPI;
import xyz.hasnat.weather.extras.Constant;
import xyz.hasnat.weather.extras.TimeAndDateConverter;
import xyz.hasnat.weather.model.CurrentWeather.CurrentWeatherResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentWeatherFragment extends Fragment{

    private TextView temp, city, temp_des, date, sunrise, sunset,
                     humidityTV, pressureTV, minTempTV, maxTempTV,
                     cloudsTV, windTV;
    private Switch unit_switch;
    private ImageView temp_icon;
    private Context context;
    private String weather_url;
    private String switch_unit_status = "metric";
    private FusedLocationProviderClient locationProviderClient;
    private Location lastLocation;
    //WeatherServiceAPI weatherServiceAPI;

    //private Double latitude, longitude;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        temp = view.findViewById(R.id.tempTV);
        city = view.findViewById(R.id.cityTV);
        date = view.findViewById(R.id.dateTV);
        temp_des = view.findViewById(R.id.descriptionWeather);
        temp_icon = view.findViewById(R.id.weatherImage);
        sunrise = view.findViewById(R.id.sunriseTV);
        sunset = view.findViewById(R.id.sunsetTV);
        humidityTV = view.findViewById(R.id.humidityTV);
        pressureTV = view.findViewById(R.id.pressureTV);
        cloudsTV = view.findViewById(R.id.cloudsTV);
        windTV = view.findViewById(R.id.windsTV);
        maxTempTV = view.findViewById(R.id.tempMaxTV);
        minTempTV = view.findViewById(R.id.tempMinTV);
        unit_switch = view.findViewById(R.id.unit_switch);
        unit_switch.setChecked(false);
        unit_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    switch_unit_status = "imperial";
                    getWeatherByUnit(switch_unit_status);
               } else {
                    switch_unit_status = "metric";
                    getWeatherByUnit(switch_unit_status);
                }
            }
        });
        getWeatherByUnit(switch_unit_status);


        return view;
    } // ending onCreateView


    private void getWeatherByUnit( String switch_unit_status) {
        WeatherServiceAPI weatherServiceAPI = RetrofitClient.getClient(Constant.baseUrl.WEATHER_BASE_URL).create(WeatherServiceAPI.class);

        double latitude = getArguments().getDouble("lat");
        double longitude = getArguments().getDouble("lng");

        weather_url = String.format("weather?lat=%f&lon=%f&units=%s&appid=%s", latitude, longitude, switch_unit_status, Constant.apiKeys.WEATHER_API);

        //String weather_url = String.format("weather?q=dhaka&units=metric&appid=%s", apiKey);
        weatherServiceAPI.getCurrentWeatherResponse(weather_url)
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        if (response.isSuccessful()){
                            CurrentWeatherResponse currentWeatherResponse = response.body();

                            // weather icon
                            String weatherIcon = currentWeatherResponse.getWeather().get(0).getIcon();
                            String iconUrl = Constant.baseUrl.WEATHER_IMAGE_BASE_URL+weatherIcon+".png";
                            Picasso.get()
                                    .load(iconUrl)
                                    .into(temp_icon);

                            // weather in celsius and Fahrenheit
                            // winds in sec and hour
                            int Temp = currentWeatherResponse.getMain().getTemp().intValue();
                            String winds = getString(R.string.winds) + "\n" +currentWeatherResponse.getWind().getSpeed() ;
                            if (unit_switch.isChecked()){
                                String tempInF = Temp + " " + getString(R.string.unit_f);
                                temp.setText(tempInF);

                                String windsKInF = winds + " m/h";
                                windTV.setText(windsKInF);
                            } else {
                                String tempInC = Temp + " " + getString(R.string.unit_c);
                                temp.setText(tempInC);

                                String windsMInF = winds + " m/s";
                                windTV.setText(windsMInF);
                            }

                            // weather description
                            temp_des.setText(currentWeatherResponse.getWeather().get(0).getDescription());

                            // City name and Country CODE
                            String userCity = currentWeatherResponse.getName() + ", ";
                            String userCountry = currentWeatherResponse.getSys().getCountry();
                            if (userCity.contains(getString(R.string.bangshal))){
                                String bangshal_country = getString(R.string.dhaka) + ", " + userCountry;
                                city.setText(bangshal_country);
                            } else {
                                String city_country = userCity + userCountry;
                                city.setText(city_country);
                            }

                            // today date
                            String dateString = getString(R.string.today) + " " + TimeAndDateConverter.getDate(currentWeatherResponse.getDt());
                            date.setText(dateString);

                            //sunrise time
                            String sunriseString = getString(R.string.sunrise) + " " + TimeAndDateConverter.getTime(currentWeatherResponse.getSys().getSunrise()) +", ";
                            sunrise.setText(sunriseString);

                            //sunset time
                            String sunsetString = getString(R.string.senset) + " " + TimeAndDateConverter.getTime(currentWeatherResponse.getSys().getSunset());
                            sunset.setText(sunsetString);

                            // humidity
                            String humidity = getString(R.string.humidity) + "\n" + currentWeatherResponse.getMain().getHumidity() + " %";
                            humidityTV.setText(humidity);

                            //pressure
                            String pressure = getString(R.string.pressure) + "\n" +currentWeatherResponse.getMain().getPressure().intValue() + " hpa";
                            pressureTV.setText(pressure);

                            // clouds
                            String clouds = getString(R.string.clouds) + "\n" +currentWeatherResponse.getClouds().getAll() + " %";
                            cloudsTV.setText(clouds);


                            //MAX TEMP
                            String maxTemp = getString(R.string.max_temp) + "\n" +currentWeatherResponse.getMain().getTempMax().intValue();
                            maxTempTV.setText(maxTemp);

                            //MIN TEMP
                            String minTemp = getString(R.string.min_temp) + "\n" +currentWeatherResponse.getMain().getTempMin().intValue();
                            minTempTV.setText(minTemp);
                        }
                    }
                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    }
                });

    }

}
