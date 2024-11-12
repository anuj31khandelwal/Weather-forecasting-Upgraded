package com.example.weather_monitoring.dto;

import com.example.weather_monitoring.model.DailySummary;

import java.util.List;


public class OpenWeatherMapForecastResponse {

    private List<ForecastItem> list;

    // Getter and Setter for 'list'
    public List<ForecastItem> getList() {
        return list;
    }

    public void setList(List<ForecastItem> list) {
        this.list = list;
    }

    // Inner class to map each forecast entry in the 'list'
    public static class ForecastItem {
        private Main main;
        private Weather[] weather;
        private long dt;

        // Getters and Setters
        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public Weather[] getWeather() {
            return weather;
        }

        public void setWeather(Weather[] weather) {
            this.weather = weather;
        }

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }
    }

    // Inner class for 'main' weather data (e.g., temperature, etc.)
    public static class Main {
        private double temp_max;
        private double temp_min;

        // Getters and Setters
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

    // Inner class for weather descriptions (e.g., "Clear", "Rain", etc.)
    public static class Weather {
        private String main;

        // Getter and Setter
        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }
    }
}