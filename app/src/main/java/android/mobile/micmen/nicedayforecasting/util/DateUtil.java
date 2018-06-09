package android.mobile.micmen.nicedayforecasting.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtil {

    public static final SimpleDateFormat foreCastDateFormate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static final SimpleDateFormat foreCastDateHour = new SimpleDateFormat("HH:mm", Locale.getDefault());

}
