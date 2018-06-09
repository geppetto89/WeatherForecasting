package android.mobile.micmen.nicedayforecasting.util;

import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * load a json file from the assets
 *
 * @author michele meninno
 */
public class AssetUtil {

    public static final String TAG = AssetUtil.class.getSimpleName();

    private AssetUtil() {
    }

    public static <T> T loadJson(String fileName, Class<T> tClass, AssetManager assetManager, Gson gson) {
        T t = null;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(fileName);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }
        if(inputStreamReader!=null) {
            t = gson.fromJson(inputStreamReader, tClass);
        }
        return t;
    }
}
