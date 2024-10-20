package service;

import model.WeatherData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class WeatherApiService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
            return weatherData;
        }

        return null;
    }

    // Inner classes to map the OpenWeatherMap API response
    private static class OpenWeatherMapResponse {
        private Weather[] weather;
        private Main main;
        private long dt;

        // Getters and setters

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

        // Getter and setter

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

        // Getters and setters

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
    }
}
