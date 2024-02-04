package io.kontak.apps.anomaly.storage.mongodb;

import io.kontak.apps.event.Anomaly;

import java.time.Instant;

public record AnomalyDoc(double temperature, String roomId, String thermometerId, Instant timestamp) {
    public static AnomalyDoc ofAnomaly(Anomaly anomaly) {
        return new AnomalyDoc(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp());
    }
}
