package io.kontak.apps.temperature.analytics.api.repository;

import io.kontak.apps.event.Anomaly;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnomalyStorageFacade implements AnomalyStorage {

    private final ThermometerAnomalyRepository thermometerAnomalyRepository;
    private final RoomAnomalyRepository roomAnomalyRepository;

    public AnomalyStorageFacade(ThermometerAnomalyRepository thermometerAnomalyRepository, RoomAnomalyRepository roomAnomalyRepository) {
        this.thermometerAnomalyRepository = thermometerAnomalyRepository;
        this.roomAnomalyRepository = roomAnomalyRepository;
    }

    public List<Anomaly> findAnomaliesByThermometerId(String id) {
        return thermometerAnomalyRepository.findById(id)
                .map(theremometerAnomaly -> theremometerAnomaly
                        .anomalies()
                        .stream().
                        map(anomaly -> new Anomaly(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp()))
                        .collect(Collectors.toList())
                ).orElse(List.of());
    }
    public List<Anomaly> findAnomaliesByRoomId(String id) {
        return roomAnomalyRepository.findById(id)
                .map(roomAnomaly -> roomAnomaly
                        .anomalies()
                        .stream().
                        map(anomaly -> new Anomaly(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp()))
                        .collect(Collectors.toList())
                ).orElse(List.of());
    }
    public List<String> findThermometersWithAnomaliesCountAbove(int threshold) {
        return thermometerAnomalyRepository.findThermometerIdsWithAnomaliesCountGreaterThan(threshold);
    }

}
