package bd.com.infobox.weather.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import bd.com.infobox.weather.Constants.Constant;
import bd.com.infobox.weather.Model.CurrentWeatherPick.CurrentWeatherResponse;
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

    private TextView temp, city, temp_des, date, sunrise, sunset,
                     humidityTV, pressureTV, minTempTV, maxTempTV,
                     cloudsTV, windTV;
    private ImageView temp_icon;
    private Context context;
    private static String apiKey;
    private String weather_url;
    //CurrentWeatherServiceAPI weatherServiceAPI;

    private FusedLocationProviderClient locationProviderClient;
    //private Double latitude, longitude;



    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

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




        CurrentWeatherServiceAPI weatherServiceAPI = RetrofitClient.getClient(Constant.baseUrl.WEATHER_BASE_URL).create(CurrentWeatherServiceAPI.class);
        apiKey = getString(R.string.weather_api_key);

        double latitude = getArguments().getDouble("lat");
        double longitude = getArguments().getDouble("lng");

        weather_url = String.format("weather?lat=%f&lon=%f&units=metric&appid=%s", latitude, longitude, apiKey);



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

                            // weather in degree celsius
                            int tempInC = currentWeatherResponse.getMain().getTemp().intValue();
                            temp.setText(tempInC+" \u2103");

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

                            // winds
                            String winds = getString(R.string.winds) + "\n" +currentWeatherResponse.getWind().getSpeed() + " m/s";
                            windTV.setText(winds);

                            //MAX TEMP
                            String maxTemp = getString(R.string.max_temp) + "\n" +currentWeatherResponse.getMain().getTempMax().intValue() + " \u2103";
                            maxTempTV.setText(maxTemp);

                            //MIN TEMP
                            String minTemp = getString(R.string.min_temp) + "\n" +currentWeatherResponse.getMain().getTempMin().intValue() + " \u2103";
                            minTempTV.setText(minTemp);




                            Toast.makeText(getContext(), currentWeatherResponse.getCoord().getLat()+ " << Lat || Lon >> "+currentWeatherResponse.getCoord().getLon(), Toast.LENGTH_SHORT).show();


                        }
                    }
                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    }
                });

        return view;
    }


    /** ,gdfgfdg **/


}
