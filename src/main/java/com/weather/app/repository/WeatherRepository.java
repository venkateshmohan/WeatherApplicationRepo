package com.weather.app.repository;

import com.weather.app.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface WeatherRepository extends JpaRepository<Weather, Instant> {
}
