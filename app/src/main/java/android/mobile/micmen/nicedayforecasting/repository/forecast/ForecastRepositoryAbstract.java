package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.WeatherResponse;

import java.util.List;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public abstract class ForecastRepositoryAbstract implements ForecastRepository{

    private static final String METRIC = "metric";
    private static final String KEY = "f0ba2155bf9bcc4de6f1a270476a8409";
    private final ForecastMapper forecastMapper;
    private final WeatherMapper weatherMapper;

    public ForecastRepositoryAbstract() {
        forecastMapper = new ForecastMapper();
        weatherMapper = new WeatherMapper();
    }

    @Override
    public Observable<List<ForecastModel>> getForecast(String lon, String lat) {
        return getForecastCall(lon, lat, METRIC, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(forecastMapper::map);
    }

    @Override
    public Observable<ForecastModel> getCurrentWeather(String lon, String lat) {
        return getCurrentWeatherCall(lon, lat, METRIC, KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(weatherMapper::map);
    }

    protected abstract Observable<ForecastResponse> getForecastCall(String lon, String lat, String units, String KEY);

    protected abstract Observable<WeatherResponse> getCurrentWeatherCall(String lon, String lat, String units, String KEY);

}
