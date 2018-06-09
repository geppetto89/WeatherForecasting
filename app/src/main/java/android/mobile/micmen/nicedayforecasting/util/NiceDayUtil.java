package android.mobile.micmen.nicedayforecasting.util;

import android.mobile.micmen.nicedayforecasting.R;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity.WeatherAppreciation;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity.WeatherAppreciation.*;

public class NiceDayUtil {

    /**
     * takes in input the user's preferences and the forecast
     * it evalutes if the day is a nice day
     * bad for 0 matches
     * almost_good for 1/3 matches
     * good for something between 1/3 matches and 2/3 matches
     * nice for over 2/3 matche
     * perfect for a perfect score
     *
     * @param forecastModels
     * @param minTemp
     * @param maxTemp
     * @param windSpeed
     * @param rain
     * @param clouds
     * @param humidity
     * @return
     */
    public static WeatherAppreciation isANiceDay(List<ForecastModel> forecastModels, int minTemp, int maxTemp, double windSpeed, double rain, int clouds, int humidity) {
        int forecastSize = forecastModels.size();
        int userMatch = 0;
        for (ForecastModel model : forecastModels) {
            if (model.getClouds() <= clouds
                    && model.getMinTemp() >= minTemp
                    && model.getMaxTemp() <= maxTemp
                    && model.getHumidity() <= humidity
                    && model.getRain() <= rain
                    && model.getWindSpeed() <= windSpeed) {
                userMatch++;
            }
        }
        if (userMatch == 0) {
            return BAD;
        } else if (userMatch < forecastSize / 3) {
            return ALMOST_GOOD;
        } else if (userMatch >= forecastSize / 3 && (userMatch < (2 * forecastSize) / 3)) {
            return GOOD;
        } else if (userMatch >= (2 * forecastSize) / 3 && userMatch < forecastSize) {
            return NICE;
        } else if (userMatch == forecastSize) {
            return PERFECT;
        }
        return UNDEFINED;
    }


    public static ForecastModel.WeatherType getWeatherType(Long id) {
        if (id >= 200 && id <= 321) {
            return ForecastModel.WeatherType.THUNDERSTORM;
        } else if (id >= 300 && id <= 521) {
            return ForecastModel.WeatherType.DRIZZLE;
        } else if (id >= 522 && id <= 531) {
            return ForecastModel.WeatherType.RAIN;
        } else if (id >= 600 && id <= 622) {
            return ForecastModel.WeatherType.SNOW;
        } else if (id == 800) {
            return ForecastModel.WeatherType.CLEAR;
        } else if (id >= 801 && id <= 804) {
            return ForecastModel.WeatherType.CLOUDS;
        } else {
            return ForecastModel.WeatherType.UNDEFINED;
        }
    }

    public static int getWeatherType(ForecastModel.WeatherType type, boolean isNight) {
        switch (type) {
            case DRIZZLE:
                return R.drawable.water;
            case THUNDERSTORM:
                return R.drawable.storm;
            case RAIN:
                return R.drawable.umbrella;
            case SNOW:
                return R.drawable.snowflakes;
            case CLEAR:
                if (isNight) {
                    return R.drawable.moon;
                }
                return R.drawable.sun;
            case CLOUDS:
                return R.drawable.cloud;
            default:
                return -1;
        }
    }

    public static boolean isNight(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        return cal1.get(Calendar.HOUR_OF_DAY) > 20;
    }

    public static int getMoodIcon(WeatherAppreciation type) {
        switch (type) {
            case BAD:
                return R.drawable.sick;
            case ALMOST_GOOD:
                return R.drawable.muted;
            case GOOD:
                return R.drawable.happy;
            case NICE:
                return R.drawable.happyplus;
            case PERFECT:
                return R.drawable.kiss;
            default:
                return -1;
        }
    }


}
