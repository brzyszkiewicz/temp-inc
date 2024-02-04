package io.kontak.apps.anomaly.storage.mongodb;

import io.kontak.apps.anomaly.storage.AnomalyStorage;
import io.kontak.apps.event.Anomaly;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
class AnomalyStorageMongo implements AnomalyStorage {

    private final RoomAnomalyRepository roomAnomalyRepository;
    private ThermometerAnomalyRepository thermometerAnomalyRepository;

    public AnomalyStorageMongo(RoomAnomalyRepository roomAnomalyRepository, ThermometerAnomalyRepository thermometerAnomallyRepository) {
        this.roomAnomalyRepository = roomAnomalyRepository;
        this.thermometerAnomalyRepository = thermometerAnomallyRepository;
    }

    @Override
    public void save(Anomaly anomaly) {
        saveThermometerAnomaly(anomaly);
        saveRoomAnomaly(anomaly);
    }

    private void saveThermometerAnomaly(Anomaly anomaly) {
        Optional<TheremometerAnomaly> existingDocument = thermometerAnomalyRepository.findById(anomaly.thermometerId());
        existingDocument.map(existing -> {
            var anomalies = existing.anomalies();
            anomalies.add(AnomalyDoc.ofAnomaly(anomaly));
            return thermometerAnomalyRepository.save(new TheremometerAnomaly(anomaly.thermometerId(), anomalies));
        }).orElse(thermometerAnomalyRepository.save(new TheremometerAnomaly(anomaly.thermometerId(), List.of(AnomalyDoc.ofAnomaly(anomaly)))));
    }

    private void saveRoomAnomaly(Anomaly anomaly) {
        Optional<RoomAnomaly> existingDocument = roomAnomalyRepository.findById(anomaly.thermometerId());
        existingDocument.map(existing -> {
            var anomalies = existing.anomalies();
            anomalies.add(AnomalyDoc.ofAnomaly(anomaly));
            return roomAnomalyRepository.save(new RoomAnomaly(anomaly.thermometerId(), anomalies));
        }).orElse(roomAnomalyRepository.save(new RoomAnomaly(anomaly.thermometerId(), List.of(AnomalyDoc.ofAnomaly(anomaly)))));
    }
}
