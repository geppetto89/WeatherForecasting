package android.mobile.micmen.nicedayforecasting;

import android.content.Context;
import android.content.res.AssetManager;
import android.mobile.micmen.nicedayforecasting.features.forecast.ForecastModel;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepositoryFake;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class ForecastMockedRepository {


    private AssetManager assetManager;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        assetManager = context.getAssets();
    }

    @Test
    public void testForecastModel(){
        ForecastRepositoryFake forecastRepository = new ForecastRepositoryFake(assetManager);
        TestObserver testObserver = new TestObserver();
        TestObserver<List<ForecastModel>> test = forecastRepository.getForecast("9.191383", "45.464211").subscribeWith(testObserver);
        test.onComplete();
    }
}
