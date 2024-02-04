package io.kontak.apps.temperature.analytics.api.web;

import io.kontak.apps.temperature.analytics.api.service.AnomalyService;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/anomaly")
public class AnomalyRepository {

    private final AnomalyService anomalyService;

    public AnomalyRepository(AnomalyService anomalyService) {
        this.anomalyService = anomalyService;
    }

    @GetMapping({"/byThermometer"})
    public ResponseEntity<List<AnomalyDto>> getAnomaliesByThermometer(@RequestParam("id") String id) {
        return ResponseEntity.ok(anomalyService.getAnomaliesForThermometer(id));
    }

    @GetMapping({"/byRoom"})
    public ResponseEntity<List<AnomalyDto>> getAnomaliesByRoom(@RequestParam("id") String id) {
        return ResponseEntity.ok(anomalyService.getAnomaliesForRoom(id));
    }

    @GetMapping({"/thermometers"})
    public ResponseEntity<List<String>> getThermometersWithAnomaliesHigherThan(@RequestParam("threshold") int threshold) {
        return ResponseEntity.ok(anomalyService.getTheremometersWithAnomaliesCountAboveThreshold(threshold));
    }
}
