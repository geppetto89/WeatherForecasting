package android.mobile.micmen.nicedayforecasting.network;

import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.WeatherResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastService {

    @GET("forecast")
    Observable<ForecastResponse> getForecast(@Query("lon") String lon, @Query("lat") String lat, @Query("units") String units, @Query("APPID") String key);

    @GET("weather")
    Observable<WeatherResponse> getCurrentWeather(@Query("lon") String lon, @Query("lat") String lat, @Query("units") String units, @Query("APPID") String key);



}
