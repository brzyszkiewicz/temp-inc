package io.kontak.apps.temperature.analytics.api.service;

import io.kontak.apps.temperature.analytics.api.web.AnomalyDto;

import java.util.List;

public interface AnomalyService {

    List<AnomalyDto> getAnomaliesForThermometer(String thermometerId);
    List<AnomalyDto> getAnomaliesForRoom(String roomId);
    List<String> getTheremometersWithAnomaliesCountAboveThreshold(int threshold);

}
