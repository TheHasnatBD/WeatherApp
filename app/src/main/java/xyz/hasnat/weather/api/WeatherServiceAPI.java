package xyz.hasnat.weather.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import xyz.hasnat.weather.model.CurrentWeather.CurrentWeatherResponse;
import xyz.hasnat.weather.model.ForecastWeather.ForecastWeatherResponse;

public interface WeatherServiceAPI {

    @GET
    Call<CurrentWeatherResponse> getCurrentWeatherResponse(@Url String endUrl);

    @GET
    Call<ForecastWeatherResponse> getForecastWeatherResponse(@Url String forecastEndUrl);


}
