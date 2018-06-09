package android.mobile.micmen.nicedayforecasting.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private final SharedPreferences preferences;

    public PreferenceManager(Context context, String preferenceName) {
        preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public boolean containsKey(String key) {
        return preferences.contains(key);
    }

    public String getString(String key) throws RuntimeException {
        if (preferences.contains(key)) {
            String value = preferences.getString(key, "");
            return value;
        }
        return null;
    }

    public void putValue(String key, String value) throws RuntimeException {

        preferences.edit().putString(key, value).commit();
    }


}
