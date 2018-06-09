package android.mobile.micmen.nicedayforecasting;

import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.features.preference.WeatherPreferenceActivity;
import android.mobile.micmen.nicedayforecasting.util.NiceDayUtil;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NiceDayTest {

    @Test
    public void testPerfectScore(){
        List<ForecastModel> models = new ArrayList<>();
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14.0, 0, 20.0, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        Assert.assertTrue(NiceDayUtil.isANiceDay(models,10, 14,20.0, 10.4, 90, 90) == WeatherPreferenceActivity.WeatherAppreciation.PERFECT);
    }

    @Test
    public void testGoodScore(){
        List<ForecastModel> models = new ArrayList<>();
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14.0, 0, 20.0, 10.4, 90, 90));

        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        Assert.assertTrue(NiceDayUtil.isANiceDay(models,11, 14,20.0, 10.4, 90, 90) == WeatherPreferenceActivity.WeatherAppreciation.ALMOST_GOOD);
    }

    @Test
    public void testAlmostGoodScore(){
        List<ForecastModel> models = new ArrayList<>();
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14.0, 0, 20.0, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14, 0, 20, 10.4, 90, 90));

        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        Assert.assertTrue(NiceDayUtil.isANiceDay(models,11, 14,20.0, 10.4, 90, 90) == WeatherPreferenceActivity.WeatherAppreciation.GOOD);
    }

    @Test
    public void testNiceScore(){
        List<ForecastModel> models = new ArrayList<>();
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14.0, 0, 20.0, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 11.0, 14, 0, 20, 10.4, 90, 90));

        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        models.add(new ForecastModel(ForecastModel.WeatherType.CLEAR, new Date(), 10.0, 14, 0, 20, 10.4, 90, 90));
        Assert.assertTrue(NiceDayUtil.isANiceDay(models,11, 14,20.0, 10.4, 90, 90) == WeatherPreferenceActivity.WeatherAppreciation.NICE);
    }



}
