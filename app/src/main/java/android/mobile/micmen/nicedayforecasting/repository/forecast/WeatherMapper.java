package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.mobile.micmen.nicedayforecasting.core.Mapper;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.Main;
import android.mobile.micmen.nicedayforecasting.network.dto.Weather;
import android.mobile.micmen.nicedayforecasting.network.dto.WeatherResponse;
import android.mobile.micmen.nicedayforecasting.util.DateUtil;
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeatherMapper implements Mapper<WeatherResponse, ForecastModel> {

    public static final String TAG = WeatherMapper.class.getSimpleName();

    /**
     * transforms the weather dto into the model for the view
     *
     * @param element
     * @return
     */
    @Override
    public ForecastModel map(WeatherResponse element) {
        ForecastModel model = new ForecastModel();
        Date currenteDate = new Date();
        model.setTemperature(element.getMain().getTemp());
        Weather weather = element.getWeather().get(0);
        if (weather != null) {
            model.setWeatherType(NiceDayUtil.getWeatherType(weather.getId()));
        }
        model.setForecastDate(currenteDate);
        if (element.getMain().getHumidity() != null) {
            model.setHumidity(element.getMain().getHumidity());
        }
        if (element.getClouds() != null) {
            model.setClouds(element.getClouds().getAll());
        }
        if (element.getMain().getTempMax() != null) {
            model.setMaxTemp(element.getMain().getTempMax());
        }
        if (element.getMain().getTempMin() != null) {
            model.setMinTemp(element.getMain().getTempMin());
        }
        if (element.getmRain()!=null && element.getmRain().getRain3h() != null) {
            model.setRain(element.getmRain().getRain3h());
        }
        if (element.getWind().getSpeed() != null) {
            model.setWindSpeed(element.getWind().getSpeed());
        }
        return model;
    }
}
