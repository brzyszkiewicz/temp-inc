package io.kontak.apps.anomaly.storage.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomAnomalyRepository extends MongoRepository<RoomAnomaly, String> {
}
