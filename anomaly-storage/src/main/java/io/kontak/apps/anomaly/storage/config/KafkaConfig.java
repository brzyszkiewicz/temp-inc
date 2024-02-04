package io.kontak.apps.anomaly.storage.config;

import io.kontak.apps.anomaly.storage.AnomalyConsumer;
import io.kontak.apps.anomaly.storage.AnomalyStorage;
import io.kontak.apps.event.Anomaly;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Consumer;


@Configuration
public class KafkaConfig {

    @Bean
    public Consumer<KStream<String, List<Anomaly>>> anomalyStorageProcessor(AnomalyStorage anomalyStorage) {
        return new AnomalyConsumer(anomalyStorage);
    }

}