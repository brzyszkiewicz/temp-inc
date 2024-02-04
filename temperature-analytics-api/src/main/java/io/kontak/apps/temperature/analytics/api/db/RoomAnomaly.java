package io.kontak.apps.temperature.analytics.api.db;

import io.kontak.apps.anomaly.storage.mongodb.AnomalyDoc;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public record RoomAnomaly(@Id String roomId, List<AnomalyDoc> anomalies) {
}
