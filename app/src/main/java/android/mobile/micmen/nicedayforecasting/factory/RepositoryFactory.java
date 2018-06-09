package android.mobile.micmen.nicedayforecasting.factory;

import android.mobile.micmen.nicedayforecasting.repository.forecast.ForecastRepository;

/**
 * Provides a list of repository to build in the project
 *
 * @author Michele Meninno
 */
public interface RepositoryFactory {

    ForecastRepository makeForecastRepository();


}
