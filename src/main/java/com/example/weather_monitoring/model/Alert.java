package com.example.weather_monitoring.model;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    @Enumerated(EnumType.STRING)
    private AlertType type;

    private double threshold;

    @Column(name = "`condition`")
    private String condition;

    private String message;

    public enum AlertType {
        TEMPERATURE_ABOVE,
        TEMPERATURE_BELOW,
        WEATHER_CONDITION
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
