package bd.com.infobox.weather.Services;

import bd.com.infobox.weather.Model.CurrentWeatherPick.CurrentWeatherResponse;
import bd.com.infobox.weather.Model.ForecastWeatherPick.ForecastWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherServiceAPI {

    @GET
    Call<CurrentWeatherResponse> getCurrentWeatherResponse(@Url String endUrl);

    @GET
    Call<ForecastWeatherResponse> getForecastWeatherResponse(@Url String forecastEndUrl);


}
