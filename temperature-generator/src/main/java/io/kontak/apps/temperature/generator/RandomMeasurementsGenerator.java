package io.kontak.apps.temperature.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomMeasurementsGenerator implements MeasurementsGenerator {

    private final Random random = new Random();
    private final double baseline;
    private final double anomalyThreshold;
    public RandomMeasurementsGenerator(@Value("${temperature-generator.measurement.baseline}") double baseline,
                                       @Value("${temperature-generator.measurement.anomaly.threshold}") double anomalyThreshold) {
        this.baseline = baseline;
        this.anomalyThreshold = anomalyThreshold;
    }

    @Override
    public double nextValue() {
        return random.nextGaussian(baseline, anomalyThreshold);
    }
}
