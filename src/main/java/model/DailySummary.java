package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "daily_summaries")
public class DailySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private LocalDate date;
    private double averageTemperature;
    private double maxTemperature;
    private double minTemperature;
    private String dominantWeatherCondition;

    public DailySummary(String city, LocalDate date) {
        this.city = city;
        this.date = date;
    }
}
