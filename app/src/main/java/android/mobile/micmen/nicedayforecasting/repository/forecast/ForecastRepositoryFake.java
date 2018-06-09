package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.content.res.AssetManager;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.WeatherResponse;
import android.mobile.micmen.nicedayforecasting.util.AssetUtil;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;

public class ForecastRepositoryFake extends ForecastRepositoryAbstract {

    public static final String FORECAST_MOCKED = "forecast.json";
    public static final String WEATHER_MOCKED = "weather.json";
    private Gson gson;
    private AssetManager assetManager;

    public ForecastRepositoryFake(AssetManager assetManager) {
        this.assetManager = assetManager;
        gson = new Gson();
    }

    @Override
    protected Observable<ForecastResponse> getForecastCall(String lon, String lat, String units, String KEY) {
        return Observable.defer(() -> Observable.just(createForecastResponse()));
    }

    @Override
    protected Observable<WeatherResponse> getCurrentWeatherCall(String lon, String lat, String units, String KEY) {
        return Observable.defer(() -> Observable.just(createWeatherResponse()));
    }


    private WeatherResponse createWeatherResponse() throws InterruptedException {
        Thread.sleep(2000);
        return AssetUtil.loadJson(WEATHER_MOCKED, WeatherResponse.class, assetManager, gson);
    }

    private ForecastResponse createForecastResponse() throws InterruptedException {
        Thread.sleep(2000);
        return AssetUtil.loadJson(FORECAST_MOCKED, ForecastResponse.class, assetManager, gson);
    }

}
