package io.kontak.apps.anomaly.detector;


import io.kontak.apps.event.Anomaly;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.SlidingWindows;
import org.apache.kafka.streams.state.WindowStore;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class TemperatureMeasurementsListener implements Function<KStream<String, TemperatureReading>, KStream<String, List<Anomaly>>> {

    private final AnomalyDetector anomalyDetector;

    public TemperatureMeasurementsListener(AnomalyDetector anomalyDetector) {
        this.anomalyDetector = anomalyDetector;
    }

    @Override
    public KStream<String, List<Anomaly>> apply(KStream<String, TemperatureReading> events) {
        return events
                .selectKey((s, temperatureReading) -> "all-reads")
                .groupByKey()
                .windowedBy(SlidingWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10)))
                .aggregate(
                        LinkedList::new,
                        (key, temperatureReading, readingsList) -> {
                            readingsList.add(temperatureReading);
                            return readingsList;
                        },
                        Materialized.<String, List<TemperatureReading>, WindowStore<Bytes, byte[]>>as("readings-store")
                )
                .toStream()
                .mapValues(anomalyDetector::apply)
                .filter((s, anomaly) -> anomaly.isEmpty())
                .mapValues((s, anomaly) -> anomaly)
                .selectKey((s, anomaly) -> "all-anomalies");
    }
}
