package com.its.config;

import de.codecentric.spring.boot.chaos.monkey.configuration.ChaosMonkeySettings;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChaosMetricsConfig {
    private final ChaosMonkeySettings chaosMonkeySettings;

    public ChaosMetricsConfig(ChaosMonkeySettings chaosMonkeySettings) {
        this.chaosMonkeySettings = chaosMonkeySettings;
    }

    @Bean
    Gauge chaosMonkeyStatusGauge(MeterRegistry registry) {
        log.info("Entering ChaosMetricsConfig : chaosMonkeyStatusGauge");
        Number chaosMonkeyStatus = 0;
        if (chaosMonkeySettings.getChaosMonkeyProperties().isEnabled()) {
            log.info("Setting chaosMonkeyStatus to 1 as chaosMonkeyProperties is enabled");
            chaosMonkeyStatus = 1;
        }
        log.info("Leaving ChaosMetricsConfig : chaosMonkeyStatusGauge after registering MeterRegistry");
        return Gauge
                .builder("chaos.monkey.status", chaosMonkeyStatus, this::convert)
                .register(registry);
    }

    private double convert(Number f) {
        Number chaosMonkeyStatus = 0;
        if (chaosMonkeySettings.getChaosMonkeyProperties().isEnabled()) {
            chaosMonkeyStatus = 1;
        }
        return chaosMonkeyStatus.doubleValue();
    }
}
