package io.kontak.apps.temperature.analytics.api.repository;

import io.kontak.apps.temperature.analytics.api.db.TheremometerAnomaly;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ThermometerAnomalyRepository extends MongoRepository<TheremometerAnomaly, String> {

    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$thermometerId', 'count': { '$sum': 1 } } }",
            "{ '$match': { 'count': { '$gt': ?0 } } }",
            "{ '$project': { '_id': 0, 'thermometerId': '$_id' } }"
    })
    List<String> findThermometerIdsWithAnomaliesCountGreaterThan(int threshold);
}
