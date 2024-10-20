package controller;

import model.Alert;
import model.DailySummary;
import model.WeatherData;
import repository.DailySummaryRepository;
import repository.WeatherDataRepository;
import service.AlertService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherDataRepository weatherDataRepository;
    private final DailySummaryRepository dailySummaryRepository;
    private final AlertService alertService;

    public WeatherController(WeatherDataRepository weatherDataRepository, DailySummaryRepository dailySummaryRepository, AlertService alertService) {
        this.weatherDataRepository = weatherDataRepository;
        this.dailySummaryRepository = dailySummaryRepository;
        this.alertService = alertService;
    }

    @GetMapping("/current/{city}")
    public WeatherData getCurrentWeather(@PathVariable String city) {
        return weatherDataRepository.findTopByCityOrderByTimestampDesc(city);
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
}
