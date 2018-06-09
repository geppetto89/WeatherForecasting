package android.mobile.micmen.nicedayforecasting.factory.impl;

import android.mobile.micmen.nicedayforecasting.factory.RepositoryFactory;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepository;
import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepositoryDefault;

/**
 * <h1> Repository Factory Implementation<h1/>
 *
 * creates actual repositories
 *
 * @author Michele Meninno
 */

public class RepositoryFactoryDefault implements RepositoryFactory {

    @Override
    public ForecastRepository makeForecastRepository() {
        return new ForecastRepositoryDefault();
    }
}
