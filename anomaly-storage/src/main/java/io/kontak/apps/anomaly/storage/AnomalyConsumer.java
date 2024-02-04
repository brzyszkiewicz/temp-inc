package io.kontak.apps.anomaly.storage;

import io.kontak.apps.event.Anomaly;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

@Component
public class AnomalyConsumer implements Consumer<KStream<String, List<Anomaly>>> {

    private final AnomalyStorage anomalyStorage;

    public AnomalyConsumer(AnomalyStorage anomalyStorage) {
        this.anomalyStorage = anomalyStorage;
    }

    @Override
    public void accept(KStream<String, List<Anomaly>> events) {
        events.foreach((key, anomalies) -> {
            anomalies.forEach(anomalyStorage::save);
        });
    }
}
