package io.kontak.apps.temperature.analytics.api.service;

import io.kontak.apps.temperature.analytics.api.repository.AnomalyStorageFacade;
import io.kontak.apps.temperature.analytics.api.web.AnomalyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnomalyServiceImpl implements AnomalyService{

    private final AnomalyStorageFacade anomalyStorageFacade;

    public AnomalyServiceImpl(AnomalyStorageFacade anomalyStorageFacade) {
        this.anomalyStorageFacade = anomalyStorageFacade;
    }


    @Override
    public List<AnomalyDto> getAnomaliesForThermometer(String thermometerId) {
        return anomalyStorageFacade.findAnomaliesByThermometerId(thermometerId)
                .stream()
                .map(anomaly -> new AnomalyDto(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnomalyDto> getAnomaliesForRoom(String roomId) {
        return anomalyStorageFacade.findAnomaliesByRoomId(roomId)
                .stream()
                .map(anomaly -> new AnomalyDto(anomaly.temperature(), anomaly.roomId(), anomaly.thermometerId(), anomaly.timestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getTheremometersWithAnomaliesCountAboveThreshold(int threshold) {
        return anomalyStorageFacade.findThermometersWithAnomaliesCountAbove(threshold);
    }
}
