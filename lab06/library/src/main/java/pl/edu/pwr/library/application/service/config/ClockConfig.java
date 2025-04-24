package pl.edu.pwr.library.application.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.pwr.library.application.service.ClockService;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class ClockConfig {

    @Bean
    public Clock clock() {
        return new ClockService(
                Instant.now(),
                ZoneId.systemDefault(),
                1440.0
        );
    }
}
