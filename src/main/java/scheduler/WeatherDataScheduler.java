package scheduler;



import model.WeatherData;
import service.AlertService;
import service.DataProcessingService;
import service.WeatherApiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherApiService weatherApiService;
    private final DataProcessingService dataProcessingService;
    private final AlertService alertService;

    private final List<String> METROS = Arrays.asList("Delhi", "Mumbai", "Chennai", "Bangalore", "Kolkata", "Hyderabad");

    public WeatherDataScheduler(WeatherApiService weatherApiService, DataProcessingService dataProcessingService, AlertService alertService) {
        this.weatherApiService = weatherApiService;
        this.dataProcessingService = dataProcessingService;
        this.alertService = alertService;
    }

    @Scheduled(fixedRateString = "${weather.update.interval}")
    public void fetchAndProcessWeatherData() {
        for (String city : METROS) {
            WeatherData weatherData = weatherApiService.getWeatherData(city);
            if (weatherData != null) {
                dataProcessingService.processWeatherData(weatherData);
                alertService.checkAndTriggerAlerts(weatherData);
            }
        }
    }
}
