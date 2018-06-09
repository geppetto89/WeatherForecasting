package android.mobile.micmen.nicedayforecasting.features;

import android.content.Intent;
import android.mobile.micmen.nicedayforecasting.features.forecast.MainActivity;
import android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity;
import android.mobile.micmen.nicedayforecasting.util.PreferenceManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity doesn't have a layout
 * it just dispatch the user to the main activity if has already set the weather's preferences
 *
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager preferenceManager = new PreferenceManager(this, WeatherPreferenceActivity.WEATHER_PREFERENCES);
        String rain = preferenceManager.getString(WeatherPreferenceActivity.RAIN_PREF);
        String wind = preferenceManager.getString(WeatherPreferenceActivity.WIND_PREF);
        String humidity = preferenceManager.getString(WeatherPreferenceActivity.HUMIDITY_PREF);
        String max_temp = preferenceManager.getString(WeatherPreferenceActivity.MAX_TEMP_PREF);
        String min_temp = preferenceManager.getString(WeatherPreferenceActivity.MIN_TEMP_PREF);
        String cloud = preferenceManager.getString(WeatherPreferenceActivity.CLOUDS_PREF);
        if (rain != null && wind != null && humidity != null && min_temp !=null  && max_temp != null && cloud != null) {
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        else {
            Intent intent = new Intent();
            intent.setClass(this, WeatherPreferenceActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }
}
