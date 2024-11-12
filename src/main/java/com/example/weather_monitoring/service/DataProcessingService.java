package com.example.weather_monitoring.service;

import com.example.weather_monitoring.model.DailySummary;
import com.example.weather_monitoring.model.WeatherData;
import com.example.weather_monitoring.repository.DailySummaryRepository;
import com.example.weather_monitoring.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {

    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;

    public DataProcessingService(WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    public void processWeatherData(WeatherData weatherData) {
        weatherDataRepository.save(weatherData);
        updateDailySummary(weatherData);
    }

    private void updateDailySummary(WeatherData weatherData) {
        LocalDate date = weatherData.getTimestamp().toLocalDate();
        DailySummary summary = dailySummaryRepository.findByCityAndDate(weatherData.getCity(), date)
                .orElse(new DailySummary(weatherData.getCity(), date));

        List<WeatherData> dailyData = weatherDataRepository.findByCityAndTimestampBetween(
                weatherData.getCity(),
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay()
        );

        summary.setAverageTemperature(calculateAverage(dailyData, WeatherData::getTemperature));
        summary.setMaxTemperature(calculateMax(dailyData, WeatherData::getTemperature));
        summary.setMinTemperature(calculateMin(dailyData, WeatherData::getTemperature));
        summary.setDominantWeatherCondition(calculateDominantCondition(dailyData));

        dailySummaryRepository.save(summary);
    }

    private double calculateAverage(List<WeatherData> data, java.util.function.ToDoubleFunction<WeatherData> mapper) {
        return data.stream().mapToDouble(mapper).average().orElse(0.0);
    }

    private double calculateMax(List<WeatherData> data, java.util.function.ToDoubleFunction<WeatherData> mapper) {
        return data.stream().mapToDouble(mapper).max().orElse(0.0);
    }

    private double calculateMin(List<WeatherData> data, java.util.function.ToDoubleFunction<WeatherData> mapper) {
        return data.stream().mapToDouble(mapper).min().orElse(0.0);
    }

    private String calculateDominantCondition(List<WeatherData> data) {
        Map<String, Long> conditionCounts = data.stream()
                .collect(Collectors.groupingBy(WeatherData::getMainCondition, Collectors.counting()));

        return conditionCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Unknown");
    }
}
