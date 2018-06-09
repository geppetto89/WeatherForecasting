package android.mobile.micmen.nicedayforecasting;

import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastMapper;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepositoryAbstract;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepositoryDefault;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import io.reactivex.observers.TestObserver;

public class ForecastRepositoryTest {

    @Test
    public void testForecastCall() {
        ForecastRepositoryDefault forecastRepository = new ForecastRepositoryDefault();
        forecastRepository.getForecast("9.191383", "45.464211")
                .blockingSubscribe(Assert::assertNotNull,
                        throwable -> {

                        });
    }

    @Test
    public void testWeatherCall() {
        ForecastRepositoryAbstract forecastRepository = new ForecastRepositoryDefault();
        forecastRepository.getCurrentWeather("9.191383", "45.464211")
                .blockingSubscribe(Assert::assertNotNull,
                        throwable -> {

                        });
    }

    @Test
    public void testAddingCurrentWeatherToForecast() {
        ForecastRepositoryAbstract forecastRepository = new ForecastRepositoryDefault();
        String lon = "9.191383";
        String lat = "45.464211";
        ForecastModel[] models = new ForecastModel[1];
        forecastRepository.getCurrentWeather(lon, lat).doOnNext((ForecastModel forecastModel) -> models[0] = forecastModel).
                concatMap(forecastModel -> forecastRepository.getForecast(lon, lat))
                .doOnNext(forecastModels -> forecastModels.add(models[0]))
                .blockingSubscribe(forecastResponse ->
                                Assert.assertTrue(forecastResponse.get(0).equals(models[0])),
                        throwable ->
                        {

                        });
    }


}
