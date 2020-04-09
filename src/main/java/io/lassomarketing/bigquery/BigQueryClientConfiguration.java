package io.lassomarketing.bigquery;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BigQueryClientConfiguration {

    @Bean
    BigQuery getClient() {
        return BigQueryOptions.getDefaultInstance().getService();
    }
}
