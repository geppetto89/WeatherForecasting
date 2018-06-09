package android.mobile.micmen.nicedayforecasting.core;

import android.app.Application;
import android.mobile.micmen.nicedayforecasting.BuildConfig;
import android.mobile.micmen.nicedayforecasting.factory.RepositoryFactory;
import android.mobile.micmen.nicedayforecasting.factory.impl.RepositoryFactoryDefault;
import android.mobile.micmen.nicedayforecasting.factory.impl.RepositoryFactoryMock;

public class ForecastApplication extends Application {

    private RepositoryFactory repositoryFactory;

    private static ForecastApplication instance;

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }

    public static ForecastApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (BuildConfig.IS_MOCK) {
            repositoryFactory = new RepositoryFactoryMock(getAssets());
        } else {
            repositoryFactory = new RepositoryFactoryDefault();
        }
    }


}
