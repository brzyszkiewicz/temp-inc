package io.kontak.apps.temperature.analytics.api.db;

import io.kontak.apps.anomaly.storage.mongodb.AnomalyDoc;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public record TheremometerAnomaly(@Id String thermometerId, List<AnomalyDoc> anomalies) {
}
