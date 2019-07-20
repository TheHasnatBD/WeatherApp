package xyz.hasnat.weather.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.hasnat.weather.R;
import xyz.hasnat.weather.adapter.SearchForecastRVAdapter;
import xyz.hasnat.weather.api.RetrofitClient;
import xyz.hasnat.weather.api.WeatherServiceAPI;
import xyz.hasnat.weather.extras.Constant;
import xyz.hasnat.weather.extras.TimeAndDateConverter;
import xyz.hasnat.weather.model.CurrentWeather.CurrentWeatherResponse;
import xyz.hasnat.weather.model.ForecastWeather.ForecastWeatherResponse;
import xyz.hasnat.weather.model.ForecastWeather.List;


public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText searchInput;
    private ImageView backButton;
    private ImageButton searchBtn;

    private TextView searchDetailsTV;

    //APIs declaration
    private TextView temp, city, temp_des, date, sunrise, sunset,
            humidityTV, pressureTV, minTempTV, maxTempTV,
            cloudsTV, windTV, forecastTVSearch;
    private Switch unit_switch;
    private ImageView temp_icon;
    private String weather_url, forecast_weather_url;
    private String switch_unit_status = "metric";
    private String searchString;
    private SearchForecastRVAdapter adapter;
    private RecyclerView weatherListRV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // appbar / toolbar
        toolbar = findViewById(R.id.search_appbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater)
                this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.appbar_search, null);
        actionBar.setCustomView(view);

        searchInput = findViewById(R.id.serachInput);
        backButton = findViewById(R.id.backButton);
        searchBtn = findViewById(R.id.searchBtn);


        // For API View
        temp = findViewById(R.id.tempTVSearch);
        city = findViewById(R.id.cityTVSearch);
        date = findViewById(R.id.dateTVSearch);
        temp_des = findViewById(R.id.descriptionWeatherSearch);
        temp_icon = findViewById(R.id.weatherImageSearch);
        sunrise = findViewById(R.id.sunriseTVSearch);
        sunset = findViewById(R.id.sunsetTVSearch);
        humidityTV = findViewById(R.id.humidityTVSearch);
        pressureTV = findViewById(R.id.pressureTVSearch);
        cloudsTV = findViewById(R.id.cloudsTVSearch);
        windTV = findViewById(R.id.windsTVSearch);
        maxTempTV = findViewById(R.id.tempMaxTVSearch);
        minTempTV = findViewById(R.id.tempMinTVSearch);
        unit_switch = findViewById(R.id.unit_switchSearch);
        forecastTVSearch = findViewById(R.id.forecastTVSearch);
        unit_switch.setChecked(false);

        weatherListRV = findViewById(R.id.weatherListSearch);


        // APP BAR BACK BUTTON
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        searchDetailsTV = findViewById(R.id.searchDetailsTV);
        findViewById(R.id.relLaySearch).setVisibility(View.GONE);



        // SEARCH BUTTON
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = searchInput.getText().toString();
                if (TextUtils.isEmpty(searchString)){
                    findViewById(R.id.relLaySearch).setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "Type a city", Toast.LENGTH_SHORT).show();
                }
                // after inputting in edit text, Switching Temp unit
                unit_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b){
                            switch_unit_status = "imperial";
                            getWeatherByCityNUnit(searchString.toLowerCase(), switch_unit_status);
                        } else {
                            switch_unit_status = "metric";
                            getWeatherByCityNUnit(searchString.toLowerCase(), switch_unit_status);
                        }

                    }
                });
                getWeatherByCityNUnit(searchString.toLowerCase(), switch_unit_status);
                findViewById(R.id.relLaySearch).setVisibility(View.GONE);
            }
        });

    } // ending onCreate

    WeatherServiceAPI weatherServiceAPI = RetrofitClient.getClient(Constant.baseUrl.WEATHER_BASE_URL).create(WeatherServiceAPI.class);

    private void getWeatherByCityNUnit(final String searchString, final String temp_unit) {
        findViewById(R.id.relLaySearch).setVisibility(View.GONE);

        //******************* Current Weather for searching city ***************************
        weather_url = String.format("weather?q=%s&units=%s&appid=%s", searchString, temp_unit, Constant.apiKeys.WEATHER_API);
        weatherServiceAPI.getCurrentWeatherResponse(weather_url)
                .enqueue(new Callback<CurrentWeatherResponse>() {
                    @Override
                    public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                        if (response.isSuccessful()){
                            CurrentWeatherResponse currentWeatherResponse = response.body();

                            String cityName = currentWeatherResponse.getName();
                            if (cityName.toLowerCase().contains(searchString)){
                                searchDetailsTV.setVisibility(View.GONE);
                                findViewById(R.id.relLaySearch).setVisibility(View.VISIBLE);

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
                                String userCountry = currentWeatherResponse.getSys().getCountry();
                                if (cityName.contains("Dhaka")){
                                    String city_country = cityName + ", BD";
                                    city.setText(city_country);
                                } else {
                                    String city_country = cityName + ", " + userCountry;
                                    city.setText(city_country);
                                }

                                //City, 5 Days / 3 Hour Forecast TV
                                String s = cityName + ", 5 Days / 3 Hour Forecast";
                                forecastTVSearch.setText(s);


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

                        } else {
                            searchDetailsTV.setVisibility(View.VISIBLE);
                            searchDetailsTV.setText(Html.fromHtml("Oops!! <font color='blue'>'"+searchString+"'</font> Not Found"));
                            findViewById(R.id.relLaySearch).setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                        Log.e("onFailure", "SearchWeatherByCity: "+t.getLocalizedMessage()+"\n"+
                        t.getMessage());
                        Toast.makeText(SearchActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //******************* Forecast List for searching city ***************************
        forecast_weather_url = String.format("forecast?q=%s&units=%s&appid=%s", searchString, temp_unit, Constant.apiKeys.WEATHER_API);
        weatherServiceAPI.getForecastWeatherResponse(forecast_weather_url)
                .enqueue(new Callback<ForecastWeatherResponse>() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onResponse(Call<ForecastWeatherResponse> call, Response<ForecastWeatherResponse> response) {
                        if (response.isSuccessful()){
                            ForecastWeatherResponse forecastWeatherResponse = response.body();
                            java.util.List<List> weatherLists = forecastWeatherResponse.getList();
                            //Toast.makeText(SearchActivity.this, "size : " + weatherLists.size(), Toast.LENGTH_SHORT).show();

                            adapter = new SearchForecastRVAdapter(weatherLists, SearchActivity.this, temp_unit);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                            linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
                            weatherListRV.setLayoutManager(linearLayoutManager);
                            //weatherListRV.setAdapter(adapter);

                            if (adapter!=null){
                                weatherListRV.setAdapter(adapter);
                            }
/*                            if (adapter == null){
                                adapter = new SearchForecastRVAdapter(weatherLists, SearchActivity.this, temp_unit);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                weatherListRV.setLayoutManager(linearLayoutManager);
                                weatherListRV.setAdapter(adapter);

                            } else {
                                adapter.updateCollection(weatherLists);
                            } */
                        }
                    }
                    @Override
                    public void onFailure(Call<ForecastWeatherResponse> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, " Failed to load"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ForecastFailed: ", t.getMessage() + "\n"+t.getLocalizedMessage());
                    }
                });
    }

}
