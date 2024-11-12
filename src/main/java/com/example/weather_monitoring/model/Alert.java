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
}
