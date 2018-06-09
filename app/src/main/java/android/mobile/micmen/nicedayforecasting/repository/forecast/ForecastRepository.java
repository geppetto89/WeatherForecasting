package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;

import java.util.List;

import io.reactivex.Observable;

public interface ForecastRepository {

    Observable<List<ForecastModel>> getForecast(String lon, String lat);

    Observable<ForecastModel> getCurrentWeather(String lon, String lat);

}
