package org.fer.hr.simulator.forecast;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fer.hr.simulator.data.ForecastPojo;

import java.util.List;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ForecastGenerator {

    private final List<ForecastPojo> weatherForecastList;

    public static ForecastGenerator create(String forecastFilePath, String forecastFileName) {
        return new ForecastGenerator(ForecastFileLoader.load(forecastFilePath, forecastFileName));
    }

    public List<ForecastPojo> get72hWeatherForecast(String timeOfForecast) {
        List<ForecastPojo> forecast = weatherForecastList.stream()
                .filter(f -> f.tof().equals(timeOfForecast))
                .toList();

        if (forecast.size() != 72) {
            log.error("Forecast for {} is not complete. Expected 72 hours, got {}", timeOfForecast, forecast.size());
        }

        return forecast;
    }
}
