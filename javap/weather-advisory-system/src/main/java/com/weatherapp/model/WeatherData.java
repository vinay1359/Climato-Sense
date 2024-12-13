package com.weatherapp.model;

/**
 * Represents weather data for a specific location.
 */
public final class WeatherData {
    private final double temperature;
    private final double humidity;
    private final double windSpeed;
    private final String condition;
    private final String iconCode;
    private final String cityName;
    private final double pressure;
    private final int visibility;
    private final long sunrise;
    private final long sunset;

    private WeatherData(Builder builder) {
        this.temperature = builder.temperature; // Convert Kelvin to Celsius
        this.humidity = builder.humidity;
        this.windSpeed = builder.windSpeed;
        this.condition = builder.condition;
        this.iconCode = builder.iconCode;
        this.cityName = builder.cityName;
        this.pressure = builder.pressure;
        this.visibility = builder.visibility;
        this.sunrise = builder.sunrise;
        this.sunset = builder.sunset;
    }
    public WeatherData(double temperature, double humidity, double windSpeed,
                   String condition, String iconCode, String cityName,
                   double pressure, int visibility, long sunrise, long sunset) {
    this.temperature = temperature ; // Convert Kelvin to Celsius
    this.humidity = humidity;
    this.windSpeed = windSpeed;
    this.condition = condition;
    this.iconCode = iconCode;
    this.cityName = cityName;
    this.pressure = pressure;
    this.visibility = visibility;
    this.sunrise = sunrise;
    this.sunset = sunset;
}


    // Getters
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getCondition() { return condition; }
    public String getIconCode() { return iconCode; }
    public String getCityName() { return cityName; }
    public double getPressure() { return pressure; }
    public int getVisibility() { return visibility; }
    public long getSunrise() { return sunrise; }
    public long getSunset() { return sunset; }

    /**
     * Builder class for creating instances of WeatherData.
     */
    public static class Builder {
        private double temperature;
        private double humidity;
        private double windSpeed;
        private String condition;
        private String iconCode;
        private String cityName;
        private double pressure;
        private int visibility;
        private long sunrise;
        private long sunset;

        public Builder setTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder setHumidity(double humidity) {
            this.humidity = humidity;
            return this;
        }

        public Builder setWindSpeed(double windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public Builder setIconCode(String iconCode) {
            this.iconCode = iconCode;
            return this;
        }

        public Builder setCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder setPressure(double pressure) {
            this.pressure = pressure;
            return this;
        }

        public Builder setVisibility(int visibility) {
            this.visibility = visibility;
            return this;
        }

        public Builder setSunrise(long sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public Builder setSunset(long sunset) {
            this.sunset = sunset;
            return this;
        }

        public WeatherData build() {
            return new WeatherData(this);
        }
    }
}
