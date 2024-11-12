package com.example.weather_monitoring.service;

import com.example.weather_monitoring.dto.OpenWeatherMapForecastResponse;
import com.example.weather_monitoring.model.DailySummary;
import com.example.weather_monitoring.model.WeatherData;
import com.example.weather_monitoring.repository.DailySummaryRepository;  // Add missing import
import com.example.weather_monitoring.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class WeatherApiService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;  // Inject the missing repository

    public WeatherApiService(RestTemplate restTemplate, WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository) {
        this.restTemplate = restTemplate;
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;  // Initialize repository
    }

    public void saveWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
    }

    // Method to fetch and save current weather data
    public WeatherData getWeatherData(String city) {
        String url = String.format("%s?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        OpenWeatherMapResponse response = restTemplate.getForObject(url, OpenWeatherMapResponse.class);

        if (response != null) {
            WeatherData weatherData = new WeatherData();
            weatherData.setCity(city);
            weatherData.setMainCondition(response.getWeather()[0].getMain());
            weatherData.setTemperature(response.getMain().getTemp());
            weatherData.setFeelsLike(response.getMain().getFeels_like());
            weatherData.setTimestamp(LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getDt()), ZoneId.systemDefault()));

            // Save the current weather data
            saveWeatherData(weatherData);
            return weatherData;
        }

        return null;
    }

    // Method to fetch 5-day weather forecast and save daily summaries
    public void fetchAndSaveDailySummaries(String city) {
        String url = String.format("%s/forecast?q=%s&appid=%s&units=metric", apiUrl, city, apiKey);
        String responseBody = restTemplate.getForObject(url, String.class);
        System.out.println("API Response Body: " + responseBody);
        OpenWeatherMapForecastResponse response = restTemplate.getForObject(url, OpenWeatherMapForecastResponse.class);

        if (response != null) {
            // Iterate over the 5-day forecast data and create daily summaries
            for (OpenWeatherMapForecastResponse.ForecastItem item : response.getList()) {
                LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getDt()), ZoneId.systemDefault());

                // Create a new DailySummary with city and date
                DailySummary summary = new DailySummary(city, dateTime.toLocalDate());

                // Set max and min temperatures for the day
                summary.setMaxTemperature(item.getMain().getTemp_max()); // Correct setter
                summary.setMinTemperature(item.getMain().getTemp_min()); // Correct setter

                // Set the main weather condition
                summary.setDominantWeatherCondition(item.getWeather()[0].getMain()); // Correct setter for weather condition

                // Save the daily summary to the repository
                dailySummaryRepository.save(summary);
            }
        }
    }

    // Method to fetch daily summaries from the database
    public List<DailySummary> getDailySummaries(String city) {
        return dailySummaryRepository.findByCity(city);
    }
    // Inner classes to map the OpenWeatherMap API response
    private static class OpenWeatherMapResponse {
        private Weather[] weather;
        private Main main;
        private long dt;

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }
    }

    private static class Weather {
        private String main;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }

    private static class Main {
        private double temp;
        private double feels_like;
        private double temp_max;
        private double temp_min;

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }

        public double getFeels_like() {
            return feels_like;
        }

        public void setFeels_like(double feels_like) {
            this.feels_like = feels_like;
        }

        public double getTemp_max() {
            return temp_max;
        }

        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }

        public double getTemp_min() {
            return temp_min;
        }

        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }
    }
}
