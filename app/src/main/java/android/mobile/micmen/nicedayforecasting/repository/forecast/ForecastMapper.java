package android.mobile.micmen.nicedayforecasting.repository.forecast;

import android.mobile.micmen.nicedayforecasting.core.Mapper;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.network.dto.ForecastResponse;
import android.mobile.micmen.nicedayforecasting.network.dto.Weather;
import android.mobile.micmen.nicedayforecasting.util.DateUtil;
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ForecastMapper implements Mapper<ForecastResponse, List<ForecastModel>> {

    public static final String TAG = ForecastMapper.class.getSimpleName();

    /**
     * transforms the dto into the model for the view
     * it stops the parsing when it reaches the midnight of the next day
     *
     * @param response
     * @return
     */
    @Override
    public List<ForecastModel> map(ForecastResponse response) {

        ArrayList<ForecastModel> models = new ArrayList<>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        boolean midnightInserted = false;
        for (android.mobile.micmen.nicedayforecasting.network.dto.List element : response.getList()) {
            ForecastModel model = new ForecastModel();
            Date today = new Date();
            Date dateParsed;
            model.setTemperature(element.getMain().getTemp());
            Weather weather = element.getWeather().get(0);
            if (weather != null) {
                model.setWeatherType(NiceDayUtil.getWeatherType(weather.getId()));
            }
            try {
                dateParsed = DateUtil.foreCastDateFormate.parse(element.getDtTxt());
                model.setForecastDate(dateParsed);
                cal1.setTime(dateParsed);
                cal2.setTime(today);
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                if (midnightInserted) {
                    break;
                }
                if (!sameDay) {
                    midnightInserted = true;
                }
            } catch (ParseException e) {
                Log.d(TAG, "error while parsing the date caused by :" + e.getMessage());
            }
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
            if (element.getRain() != null && element.getRain().getRain3h() != null) {
                model.setRain(element.getRain().getRain3h());
            }
            if (element.getWind().getSpeed() != null) {
                model.setWindSpeed(element.getWind().getSpeed());
            }
            models.add(model);
        }
        return models;
    }
}
