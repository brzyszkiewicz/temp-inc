package io.kontak.apps.temperature.generator;

import io.kontak.apps.event.TemperatureReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SimpleTemperatureGenerator implements TemperatureGenerator {

    private final int numberOfRooms;
    private final int numberOfThermometersPerRoom;
    private final MeasurementsGenerator measurementsGenerator;

    public SimpleTemperatureGenerator(MeasurementsGenerator measurementsGenerator,
                                      @Value("${temperature-generator.rooms.quantity}") int numberOfRooms,
                                      @Value("${temperature-generator.thermometers.quantity.per.room}") int numberOfThermometersPerRoom) {
        this.measurementsGenerator = measurementsGenerator;
        this.numberOfRooms = numberOfRooms;
        this.numberOfThermometersPerRoom = numberOfThermometersPerRoom;
    }

    @Override
    public List<TemperatureReading> generate() {
        var readings = generateReadings();
        System.out.println(readings);
        return readings;
    }

    private List<TemperatureReading> generateReadings() {
        return IntStream.range(0, numberOfRooms)
                .mapToObj(i -> "roomId-" + i)
                .flatMap(roomId -> generateReadingsPerRoom(roomId).stream())
                .collect(Collectors.toList());
    }

    private List<TemperatureReading> generateReadingsPerRoom(String roomId) {
        return IntStream.range(0, numberOfThermometersPerRoom)
                .mapToObj(i -> "thermometerId-" + i)
                .map(thermometerId -> generateSingleReading(thermometerId, roomId))
                .collect(Collectors.toList());
    }


    private TemperatureReading generateSingleReading(String thermometerId, String roomId) {
        return new TemperatureReading(
                measurementsGenerator.nextValue(),
                roomId,
                thermometerId,
                Instant.now()
        );
    }
}
