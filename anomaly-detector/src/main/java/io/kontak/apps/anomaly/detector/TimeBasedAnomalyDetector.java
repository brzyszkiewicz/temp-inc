package io.kontak.apps.anomaly.detector;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TimeBasedAnomalyDetector implements AnomalyDetector {

    private final int threshold;

    public TimeBasedAnomalyDetector(@Value("${anomaly-detector.threshold}") int threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<Anomaly> apply(List<TemperatureReading> temperatureReadings) {
        return temperatureReadings.stream()
                .map(reading -> detectAnomaly(temperatureReadings, reading))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Anomaly> detectAnomaly(List<TemperatureReading> temperatureReadings, TemperatureReading reading) {
        if (Math.abs(reading.temperature() - calculateAverage(temperatureReadings)) > threshold) {
            return Optional.of(new Anomaly(reading.temperature(), reading.roomId(), reading.thermometerId(), reading.timestamp()));
        }
        return Optional.empty();
    }

    private double calculateAverage(List<TemperatureReading> temperatureReadings) {
        double sum = 0.0;
        for (TemperatureReading reading : temperatureReadings) {
            sum += reading.temperature();
        }
        return sum / temperatureReadings.size();
    }
}
