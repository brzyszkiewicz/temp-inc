package io.kontak.apps.temperature.analytics.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class TemperatureAnalyticsApi {
	public static void main(String[] args) {
		SpringApplication.run(TemperatureAnalyticsApi.class, args);
	}
}
