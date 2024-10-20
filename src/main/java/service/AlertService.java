package service;

import model.Alert;
import model.WeatherData;
import repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void checkAndTriggerAlerts(WeatherData weatherData) {
        List<Alert> activeAlerts = alertRepository.findByCity(weatherData.getCity());

        for (Alert alert : activeAlerts) {
            if (isAlertConditionMet(alert, weatherData)) {
                triggerAlert(alert, weatherData);
            }
        }
    }

    private boolean isAlertConditionMet(Alert alert, WeatherData weatherData) {
        switch (alert.getType()) {
            case TEMPERATURE_ABOVE:
                return weatherData.getTemperature() > alert.getThreshold();
            case TEMPERATURE_BELOW:
                return weatherData.getTemperature() < alert.getThreshold();
            case WEATHER_CONDITION:
                return weatherData.getMainCondition().equalsIgnoreCase(alert.getCondition());
            default:
                return false;
        }
    }

    private void triggerAlert(Alert alert, WeatherData weatherData) {
        // In a real application, this method would send notifications (e.g., email, push notification)
        System.out.println("ALERT: " + alert.getMessage() + " in " + weatherData.getCity());
    }

    public Alert createAlert(Alert alert) {
        return alertRepository.save(alert);
    }

    public List<Alert> getAlertsByCity(String city) {
        return alertRepository.findByCity(city);
    }
}