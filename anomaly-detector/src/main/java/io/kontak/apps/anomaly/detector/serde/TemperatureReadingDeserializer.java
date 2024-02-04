package io.kontak.apps.anomaly.detector.serde;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.kontak.apps.event.TemperatureReading;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.List;

public class TemperatureReadingDeserializer implements Deserializer<List<TemperatureReading>> {
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    /**
     * Default constructor needed by Kafka
     */
    public TemperatureReadingDeserializer() {
    }

    @Override
    public List<TemperatureReading> deserialize(String topic, byte[] bytes) {
        if (bytes == null)
            return null;

        List<TemperatureReading> data;
        try {
            data = objectMapper.readValue(bytes, new TypeReference<List<TemperatureReading>>() {});
        } catch (Exception e) {
            throw new SerializationException(e);
        }

        return data;
    }

    @Override
    public void close() {

    }
}