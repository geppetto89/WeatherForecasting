package android.mobile.micmen.nicedayforecasting.features.forecast;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.mobile.micmen.nicedayforecasting.core.ForecastApplication;
import android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepository;
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil;
import android.mobile.micmen.nicedayforecasting.util.PreferenceManager;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * retrieves weather's forecast data
 */
public class ForecastViewModel extends ViewModel {

    //today's weather forecast
    private java.util.List<ForecastModel> forecastModelList;
    private ForecastModel currentWeather;
    private ForecastRepository forecastRepository = ForecastApplication.getInstance().getRepositoryFactory().makeForecastRepository();

    /**
     * read the user's preferences
     * retrieve the data from the network (storing them in the view model)
     * returns the user's weather appreciation based on his preferences
     *
     * @param lon
     * @param lat
     * @param context
     * @return
     */
    public Observable<WeatherPreferenceActivity.WeatherAppreciation> getForecastOfToday(String lon, String lat, Context context) {

        PreferenceManager preferenceManager = new PreferenceManager(context, WeatherPreferenceActivity.WEATHER_PREFERENCES);
        String rain = preferenceManager.getString(WeatherPreferenceActivity.RAIN_PREF);
        String wind = preferenceManager.getString(WeatherPreferenceActivity.WIND_PREF);
        String humidity = preferenceManager.getString(WeatherPreferenceActivity.HUMIDITY_PREF);
        String max_temp = preferenceManager.getString(WeatherPreferenceActivity.MAX_TEMP_PREF);
        String min_temp = preferenceManager.getString(WeatherPreferenceActivity.MIN_TEMP_PREF);
        String cloud = preferenceManager.getString(WeatherPreferenceActivity.CLOUDS_PREF);

        //it makes two network calls (current weather and the today's weather forecast
        return forecastRepository.getCurrentWeather(lon, lat)
                .concatMap(currentWeather -> {
                    this.currentWeather = currentWeather;
                    return forecastRepository.getForecast(lon, lat);
                })
                //once you got the weather forecast put the current in the general list
                .doOnNext(forecastModels -> {
                    if (forecastModels != null && forecastModels.size() > 1) {
                        Date firstDate = forecastModels.get(0).getForecastDate();
                        Date currentWeatherDate = currentWeather.getForecastDate();
                        if(currentWeatherDate!=null && firstDate!=null && firstDate.compareTo(currentWeather.getForecastDate())<0){
                            forecastModels.add(1, currentWeather);
                        }
                        else {
                            forecastModels.add(0, currentWeather);
                        }
                    }
                    else {
                        if(currentWeather!=null) {
                            forecastModels.add(currentWeather);
                        }
                    }
                    this.forecastModelList = forecastModels;
                })
                //this evaluation must be done in background using a specific scheduler (meant for heavy work)
                .observeOn(Schedulers.computation())
                .map(forecastModels -> NiceDayUtil
                        .isANiceDay(forecastModels, Integer.parseInt(min_temp), Integer.parseInt(max_temp), Double.parseDouble(wind), Double.parseDouble(rain), Integer.parseInt(cloud), Integer.parseInt(humidity)));
    }

    public java.util.List<ForecastModel> getForecastModelList() {
        return forecastModelList;
    }

    public ForecastModel getCurrentWeather() {
        return currentWeather;
    }

}
