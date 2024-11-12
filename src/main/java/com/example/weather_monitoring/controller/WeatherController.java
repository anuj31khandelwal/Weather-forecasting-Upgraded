package com.example.weather_monitoring.controller;

import com.example.weather_monitoring.model.Alert;
import com.example.weather_monitoring.model.DailySummary;
import com.example.weather_monitoring.model.WeatherData;
import com.example.weather_monitoring.repository.DailySummaryRepository;
import com.example.weather_monitoring.repository.WeatherDataRepository;
import com.example.weather_monitoring.service.AlertService;
import com.example.weather_monitoring.service.WeatherApiService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherApiService weatherApiService;

    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;
    private final AlertService alertService;

    public WeatherController(WeatherApiService weatherApiService, WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository, AlertService alertService) {
        this.weatherApiService = weatherApiService;
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.alertService = alertService;
    }

    @GetMapping("/summary/{city}")
    public List<DailySummary> getDailySummaries(
            @PathVariable String city,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return dailySummaryRepository.findByCityAndDateBetween(city, from, to);
    }

    @PostMapping("/alerts")
    public Alert createAlert(@RequestBody Alert alert) {
        return alertService.createAlert(alert);
    }

    @GetMapping("/alerts/{city}")
    public List<Alert> getAlerts(@PathVariable String city) {
        return alertService.getAlertsByCity(city);
    }
    // Endpoint to fetch current weather for a dynamic city
    @GetMapping("/current/{city}")
    public WeatherData getCurrentWeather(@PathVariable String city) {
        // Fetch and return weather data for the city passed by the user
        return weatherApiService.getWeatherData(city);
    }

    // Endpoint to manually trigger the fetch and save of weather data for any city
    @PostMapping("/fetch/{city}")
    public String fetchWeatherData(@PathVariable String city) {
        WeatherData weatherData = weatherApiService.getWeatherData(city);

        if (weatherData != null) {
            weatherApiService.saveWeatherData(weatherData); // Save data to the database
            return "Weather data fetched and saved for city: " + city;
        }

        return "Failed to fetch weather data for city: " + city;
    }
    // Endpoint to get 5-day forecast weather data for a city
    @GetMapping("/forecast/{city}")
    public List<DailySummary> get5DayForecast(@PathVariable String city) {
        // Fetch and save daily summaries using the service
        weatherApiService.fetchAndSaveDailySummaries(city);
        // Return the saved daily summaries from the database
        return weatherApiService.getDailySummaries(city);
    }
}
