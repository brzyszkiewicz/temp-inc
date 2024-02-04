package io.kontak.apps.anomaly.storage;

import io.kontak.apps.event.Anomaly;

public interface AnomalyStorage {
    void save(Anomaly anomaly);

}
