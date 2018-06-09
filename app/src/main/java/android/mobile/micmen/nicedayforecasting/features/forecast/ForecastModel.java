package android.mobile.micmen.nicedayforecasting.features.forecast;

import java.util.Date;

/**
 * model to dispatch to the view
 */
public class ForecastModel {

    public enum WeatherType {
        DRIZZLE, THUNDERSTORM, RAIN, SNOW, CLEAR, CLOUDS, UNDEFINED
    }


    public ForecastModel() {
    }

    private WeatherType weatherType;
    private Date forecastDate;
    private double minTemp;
    private double maxTemp;
    private double temperature;
    private double windSpeed;
    private double rain;
    private long clouds;
    private long humidity;

    public ForecastModel(WeatherType weatherType, Date forecastDate, double minTemp, double maxTemp, double temperature, double windSpeed, double rain, long clouds, long humidity) {
        this.weatherType = weatherType;
        this.forecastDate = forecastDate;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.rain = rain;
        this.clouds = clouds;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }


    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public long getClouds() {
        return clouds;
    }

    public void setClouds(long clouds) {
        this.clouds = clouds;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }
}
