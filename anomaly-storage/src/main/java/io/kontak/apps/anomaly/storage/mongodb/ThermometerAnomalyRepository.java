package io.kontak.apps.anomaly.storage.mongodb;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThermometerAnomalyRepository extends MongoRepository<TheremometerAnomaly, String> {
}
