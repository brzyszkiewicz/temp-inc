package io.kontak.apps.temperature.analytics.api.repository;

import io.kontak.apps.event.Anomaly;

import java.util.List;
import java.util.stream.Collectors;

public interface AnomalyStorage {
    List<Anomaly> findAnomaliesByThermometerId(String id);
    List<Anomaly> findAnomaliesByRoomId(String id);
    List<String> findThermometersWithAnomaliesCountAbove(int threshold);
}
