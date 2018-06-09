package android.mobile.micmen.nicedayforecasting.features.preference;

import android.content.Intent;
import android.mobile.micmen.nicedayforecasting.R;
import android.mobile.micmen.nicedayforecasting.features.forecast.MainActivity;
import android.mobile.micmen.nicedayforecasting.util.PreferenceManager;
import android.mobile.micmen.nicedayforecasting.widget.HumidityCloudsEditText;
import android.mobile.micmen.nicedayforecasting.widget.MaxTempEditText;
import android.mobile.micmen.nicedayforecasting.widget.MinTempEditText;
import android.mobile.micmen.nicedayforecasting.widget.RainEditText;
import android.mobile.micmen.nicedayforecasting.widget.WindEditText;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class WeatherPreferenceActivity extends AppCompatActivity {

    public static final String TAG = WeatherPreferenceActivity.class.getSimpleName();
    public static final String WEATHER_PREFERENCES = "WEATHER_PREFERENCES";
    public static final String RAIN_PREF = "rain";
    public static final String WIND_PREF = "wind";
    public static final String HUMIDITY_PREF = "humidity";
    public static final String CLOUDS_PREF = "clouds";
    public static final String MIN_TEMP_PREF = "minTemp";
    public static final String MAX_TEMP_PREF = "maxTemp";

    public enum WeatherAppreciation {
        BAD, ALMOST_GOOD, GOOD, NICE, PERFECT, UNDEFINED
    }

    private MinTempEditText minTemp;
    private MaxTempEditText maxTemp;
    private HumidityCloudsEditText humidity;
    private HumidityCloudsEditText clouds;
    private WindEditText wind;
    private RainEditText rain;
    private View save;
    private Unregistrar mUnregistrar;


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            setSaveButtonStatus();
        }
    };
    private Disposable subscribe;

    private void assignViews() {

        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        humidity = findViewById(R.id.humidity);
        clouds = findViewById(R.id.clouds);
        rain = findViewById(R.id.rain);
        wind = findViewById(R.id.wind);
        save = findViewById(R.id.save);
        minTemp.addTextChangedListener(watcher);
        maxTemp.addTextChangedListener(watcher);
        humidity.addTextChangedListener(watcher);
        clouds.addTextChangedListener(watcher);
        rain.addTextChangedListener(watcher);
        wind.addTextChangedListener(watcher);

        PreferenceManager preferenceManager = new PreferenceManager(this, WeatherPreferenceActivity.WEATHER_PREFERENCES);
        String rainPref = preferenceManager.getString(WeatherPreferenceActivity.RAIN_PREF);
        String windPref = preferenceManager.getString(WeatherPreferenceActivity.WIND_PREF);
        String humidityPref = preferenceManager.getString(WeatherPreferenceActivity.HUMIDITY_PREF);
        String maxTempPref = preferenceManager.getString(WeatherPreferenceActivity.MAX_TEMP_PREF);
        String minTempPref = preferenceManager.getString(WeatherPreferenceActivity.MIN_TEMP_PREF);
        String cloudPref = preferenceManager.getString(WeatherPreferenceActivity.CLOUDS_PREF);
        if (rainPref != null && windPref != null && humidityPref != null && maxTempPref != null && minTempPref != null && cloudPref != null) {
            minTemp.setText(minTempPref);
            maxTemp.setText(maxTempPref);
            humidity.setText(humidityPref);
            clouds.setText(cloudPref);
            rain.setText(rainPref);
            wind.setText(windPref);
            save.setEnabled(true);
        }

        save.setOnClickListener(v -> {
            if (!maxTemp.isInError() && !minTemp.isInError() && !humidity.isInError() && !clouds.isInError() && !rain.isInError() && !wind.isInError()) {
                subscribe = Observable.fromCallable(() -> {
                    preferenceManager.putValue(RAIN_PREF, rain.getText().toString());
                    preferenceManager.putValue(WIND_PREF, wind.getText().toString());
                    preferenceManager.putValue(HUMIDITY_PREF, humidity.getText().toString());
                    preferenceManager.putValue(CLOUDS_PREF, clouds.getText().toString());
                    preferenceManager.putValue(MIN_TEMP_PREF, minTemp.getText().toString());
                    preferenceManager.putValue(MAX_TEMP_PREF, maxTemp.getText().toString());
                    return true;
                }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            Intent i = new Intent();
                            i.setClass(this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }, throwable -> {
                            Log.d(TAG, throwable.getMessage());
                        });
            }
        });

        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this, isOpen -> {
            save.setVisibility(isOpen ? View.GONE : View.VISIBLE);
        });
    }

    private void setSaveButtonStatus() {
        if (!maxTemp.isInError() && !minTemp.isInError() && !humidity.isInError() && !clouds.isInError() && !rain.isInError() && !wind.isInError()) {
            save.setEnabled(true);
        } else {
            save.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_preferences);
        assignViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
        mUnregistrar.unregister();

    }
}
