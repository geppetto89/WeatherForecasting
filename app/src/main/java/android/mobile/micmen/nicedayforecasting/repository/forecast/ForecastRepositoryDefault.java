package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.mobile.micmen.nicedayforecasting.network.ForecastService;
import android.mobile.micmen.nicedayforecasting.network.RetrofitClient;
import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.WeatherResponse;

import io.reactivex.Observable;

public class ForecastRepositoryDefault extends ForecastRepositoryAbstract {

    private ForecastService service;

    public ForecastRepositoryDefault() {
        this.service = RetrofitClient.getClient().create(ForecastService.class);
    }

    @Override
    protected Observable<ForecastResponse> getForecastCall(String lon, String lat, String units, String KEY) {
        return service.getForecast(lon, lat, units, KEY);
    }

    @Override
    protected Observable<WeatherResponse> getCurrentWeatherCall(String lon, String lat, String units, String KEY) {
        return service.getCurrentWeather(lon, lat, units, KEY);
    }

}
