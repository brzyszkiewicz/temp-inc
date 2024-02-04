package io.kontak.apps.temperature.analytics.api.repository;

import io.kontak.apps.temperature.analytics.api.db.RoomAnomaly;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
interface RoomAnomalyRepository extends MongoRepository<RoomAnomaly, String> {
}
