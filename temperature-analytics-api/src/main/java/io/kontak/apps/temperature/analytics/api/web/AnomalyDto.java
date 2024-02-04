package io.kontak.apps.temperature.analytics.api.web;

import java.time.Instant;

public record AnomalyDto(double temperature, String roomId, String thermometerId, Instant timestamp) {
}
