package io.lassomarketing.repository;

import com.google.cloud.bigquery.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class NpiRepositoryImpl implements NpiRepository {

    private final BigQuery bigQuery;

    public NpiRepositoryImpl(BigQuery bigQuery) {
        this.bigQuery = bigQuery;
    }

    public String getByUserId(String userId) {
        try {
            String query =
                    "SELECT npi\n"
                            + "FROM `lasso-dev-264521.npi.user_id_npi_mapping`\n"
                            + "WHERE user_id_64 = @userId LIMIT 1";

            QueryJobConfiguration queryConfig =
                    QueryJobConfiguration.newBuilder(query)
                            .addNamedParameter("userId", QueryParameterValue.string(userId))
                            .build();

            TableResult results = bigQuery.query(queryConfig);

            if (results.getTotalRows() == 0L) return null;
            return results.iterateAll().iterator().next().get("npi").getStringValue();
        } catch (BigQueryException | InterruptedException e) {
            log.error("Query not performed \n" + e.toString());
            return null;
        }
    }

}
