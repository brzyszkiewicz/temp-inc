package io.kontak.apps.anomaly.detector.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.List;

public class TemperatureReadingSerializer implements Serializer<List<TemperatureReading>> {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Default constructor needed by Kafka
     */
    public TemperatureReadingSerializer() {
    }

    @Override
    public byte[] serialize(String topic, List<TemperatureReading> data) {
        if (data == null)
            return null;

        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    @Override
    public void close() {
    }

}
