package io.lassomarketing.config;

import io.lassomarketing.bigquery.BigQueryClientConfiguration;
import io.lassomarketing.repository.NpiRepository;
import io.lassomarketing.service.NpiService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({BigQueryClientConfiguration.class, NpiService.class})
public class TestConfig {
    public static final String TEST_NPI = "test-npi";
    @Bean
    public NpiRepository npiRepository() {
        return userId -> TEST_NPI;
    }
}
