package io.kontak.apps.anomaly.detector;

import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@Primary
public class AverageBasedAnomalyDetector implements AnomalyDetector {

    private ConcurrentLinkedQueue<TemperatureReading> batch;
    private final AtomicBoolean reachedFullCapacity;
    private final int capacity;
    private double threshold;

    public AverageBasedAnomalyDetector(@Value("${anomaly-detector.batch.size}") int capacity,
                                       @Value("${anomaly-detector.threshold}") int threshold) {
        this.batch = new ConcurrentLinkedQueue<>();
        this.reachedFullCapacity = new AtomicBoolean(false);
        this.capacity = capacity;
        this.threshold = threshold;
    }

    @Override
    public List<Anomaly> apply(List<TemperatureReading> temperatureReadings) {
        return temperatureReadings.stream()
                .map(this::processSingleReading)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<Anomaly> processSingleReading(TemperatureReading temperatureReading) {
        if (reachedFullCapacity.get()) {
            removeFirst();
            batch.add(temperatureReading);
            return detectAnomaly(temperatureReading);
        } else {
            batch.add(temperatureReading);
            if (batch.size() == capacity) {
                reachedFullCapacity.compareAndExchange(false, true);
            }
            return Optional.empty();
        }
    }

    private Optional<Anomaly> detectAnomaly(TemperatureReading temperatureReading) {
        if (Math.abs(temperatureReading.temperature() - calculteBatchAverage()) > threshold) {
            return Optional.of(new Anomaly(temperatureReading.temperature(),
                    temperatureReading.roomId(),
                    temperatureReading.thermometerId(),
                    temperatureReading.timestamp()
            ));
        }
        return Optional.empty();
    }

    private double calculteBatchAverage() {
        double sum = 0.0;
        for (TemperatureReading reading : batch) {
            sum += reading.temperature();
        }
        return sum / capacity;
    }

    private void removeFirst() {
        batch.poll();
    }
}
