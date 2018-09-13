package bd.com.infobox.weather.Services.CurrentWeatherPick;

import bd.com.infobox.weather.Model.CurrentWeatherPick.CurrentWeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CurrentWeatherServiceAPI {

    @GET
    Call<CurrentWeatherResponse> getCurrentWeatherResponse(@Url String endUrl);


}
