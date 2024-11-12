package com.example.weather_monitoring.repository;

import com.example.weather_monitoring.model.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    Optional<DailySummary> findByCityAndDate(String city, LocalDate date);

    List<DailySummary> findByCityAndDateBetween(String city, LocalDate startDate, LocalDate endDate);

    List<DailySummary> findByCity(String city);
}
