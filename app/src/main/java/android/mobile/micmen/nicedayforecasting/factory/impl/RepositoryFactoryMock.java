package android.mobile.micmen.nicedayforecasting.factory.impl;


import android.content.res.AssetManager;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepository;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepositoryFake;

public class RepositoryFactoryMock extends RepositoryFactoryDefault {

    private AssetManager assetManager;

    public RepositoryFactoryMock(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public ForecastRepository makeForecastRepository() {
        return new ForecastRepositoryFake(assetManager);
    }
}
