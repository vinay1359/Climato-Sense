package com.weatherapp.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.weatherapp.model.ForecastData;
import com.weatherapp.model.WeatherData;

public interface WeatherService {
    CompletableFuture<WeatherData> getCurrentWeather(String city);
    CompletableFuture<List<ForecastData>> getForecast(String city);
}
