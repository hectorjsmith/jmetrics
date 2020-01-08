package org.hsmith.jmetrics.collector;

import org.hibernate.stat.Statistics;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;

public final class HibernateStatisticsCollector extends BaseCollector {
    private final Statistics hibernateStatistics;

    public HibernateStatisticsCollector(final Statistics hibernateStatistics) {
        this.hibernateStatistics = hibernateStatistics;
    }

    @Override
    public String getCollectorName() {
        return this.getClass().getSimpleName();
    }

    // CHECKSTYLE-OFF: MethodLength - It makes sense to keep all the metric definitions together, even if it results
    //                 in a massive method.

    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();

        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("hibernate_statistics_enabled")
                .withMetricHelp("Whether hibernate statistic collection is enabled")
                .withMetricSample(() -> hibernateStatistics.isStatisticsEnabled() ? 1 : 0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_session_opened_total")
                .withMetricHelp("Global number of sessions opened (getSessionOpenCount)")
                .withMetricSample(hibernateStatistics::getSessionOpenCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_session_closed_total")
                .withMetricHelp("Global number of sessions closed (getSessionCloseCount)")
                .withMetricSample(hibernateStatistics::getSessionCloseCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_flushed_total")
                .withMetricHelp("The global number of flushes executed by sessions (getFlushCount)")
                .withMetricSample(hibernateStatistics::getFlushCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_connect_total")
                .withMetricHelp("The global number of connections requested by the sessions (getConnectCount)")
                .withMetricSample(hibernateStatistics::getConnectCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_optimistic_failure_total")
                .withMetricHelp("The number of StaleObjectStateExceptions that occurred (getOptimisticFailureCount)")
                .withMetricSample(hibernateStatistics::getOptimisticFailureCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_statement_prepared_total")
                .withMetricHelp("The number of prepared statements that were acquired (getPrepareStatementCount)")
                .withMetricSample(hibernateStatistics::getPrepareStatementCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_statement_closed_total")
                .withMetricHelp("The number of prepared statements that were released (getCloseStatementCount)")
                .withMetricSample(hibernateStatistics::getCloseStatementCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_transaction_total")
                .withMetricHelp("The number of transactions we know to have completed (getTransactionCount)")
                .withMetricSample(hibernateStatistics::getTransactionCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_transaction_success_total")
                .withMetricHelp("The number of transactions we know to have been successful"
                        + " (getSuccessfulTransactionCount)")
                .withMetricSample(hibernateStatistics::getSuccessfulTransactionCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_second_level_cache_hit_total")
                .withMetricHelp("Global number of cacheable entities/collections successfully retrieved from the cache "
                        + "(getSecondLevelCacheHitCount)")
                .withMetricSample(hibernateStatistics::getSecondLevelCacheHitCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_second_level_cache_miss_total")
                .withMetricHelp("Global number of cacheable entities/collections not found in the cache and loaded "
                        + "from the database (getSecondLevelCacheMissCount)")
                .withMetricSample(hibernateStatistics::getSecondLevelCacheMissCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_second_level_cache_put_total")
                .withMetricHelp("Global number of cacheable entities/collections put in the cache "
                        + "(getSecondLevelCachePutCount)")
                .withMetricSample(hibernateStatistics::getSecondLevelCachePutCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_query_cache_hit_total")
                .withMetricHelp("The global number of cached queries successfully retrieved from cache "
                        + "(getQueryCacheHitCount)")
                .withMetricSample(hibernateStatistics::getQueryCacheHitCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_query_cache_miss_total")
                .withMetricHelp("The global number of cached queries not found in cache (getQueryCacheMissCount)")
                .withMetricSample(hibernateStatistics::getQueryCacheMissCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_query_cache_put_total")
                .withMetricHelp("The global number of cacheable queries put in cache (getQueryCachePutCount)")
                .withMetricSample(hibernateStatistics::getQueryCachePutCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_natural_id_cache_hit_total")
                .withMetricHelp("The global number of cached naturalId lookups successfully retrieved from cache "
                        + "(getNaturalIdCacheHitCount)")
                .withMetricSample(hibernateStatistics::getNaturalIdCacheHitCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_natural_id_cache_miss_total")
                .withMetricHelp("The global number of cached naturalId lookups not found in cache "
                        + "(getNaturalIdCacheMissCount)")
                .withMetricSample(hibernateStatistics::getNaturalIdCacheMissCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_natural_id_cache_put_total")
                .withMetricHelp("The global number of cacheable naturalId lookups put in cache "
                        + "(getNaturalIdCachePutCount)")
                .withMetricSample(hibernateStatistics::getNaturalIdCachePutCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_update_timestamps_cache_hit_total")
                .withMetricHelp("The global number of timestamps successfully retrieved from cache "
                        + "(getUpdateTimestampsCacheHitCount)")
                .withMetricSample(hibernateStatistics::getUpdateTimestampsCacheHitCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_update_timestamps_cache_miss_total")
                .withMetricHelp("The global number of tables for which no update timestamps was not found in cache "
                        + "(getUpdateTimestampsCacheMissCount)")
                .withMetricSample(hibernateStatistics::getUpdateTimestampsCacheMissCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_update_timestamps_cache_put_total")
                .withMetricHelp("The global number of timestamps put in cache (getUpdateTimestampsCachePutCount)")
                .withMetricSample(hibernateStatistics::getUpdateTimestampsCachePutCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_entity_delete_total")
                .withMetricHelp("Global number of entity deletes (getEntityDeleteCount)")
                .withMetricSample(hibernateStatistics::getEntityDeleteCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_entity_insert_total")
                .withMetricHelp("Global number of entity inserts (getEntityInsertCount)")
                .withMetricSample(hibernateStatistics::getEntityInsertCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_entity_load_total")
                .withMetricHelp("Global number of entity loads (getEntityLoadCount)")
                .withMetricSample(hibernateStatistics::getEntityLoadCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_entity_fetch_total")
                .withMetricHelp("Global number of entity fetches (getEntityFetchCount)")
                .withMetricSample(hibernateStatistics::getEntityFetchCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_entity_update_total")
                .withMetricHelp("Global number of entity updates (getEntityUpdateCount)")
                .withMetricSample(hibernateStatistics::getEntityUpdateCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_collection_load_total")
                .withMetricHelp("Global number of collections loaded (getCollectionLoadCount)")
                .withMetricSample(hibernateStatistics::getCollectionLoadCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_collection_fetch_total")
                .withMetricHelp("Global number of collections fetched (getCollectionFetchCount)")
                .withMetricSample(hibernateStatistics::getCollectionFetchCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_collection_update_total")
                .withMetricHelp("Global number of collections updated (getCollectionUpdateCount)")
                .withMetricSample(hibernateStatistics::getCollectionUpdateCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_collection_remove_total")
                .withMetricHelp("Global number of collections removed (getCollectionRemoveCount)")
                .withMetricSample(hibernateStatistics::getCollectionRemoveCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_collection_recreate_total")
                .withMetricHelp("Global number of collections recreated (getCollectionRecreateCount)")
                .withMetricSample(hibernateStatistics::getCollectionRecreateCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_query_execution_total")
                .withMetricHelp("Global number of executed queries (getQueryExecutionCount)")
                .withMetricSample(hibernateStatistics::getQueryExecutionCount)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_natural_id_query_execution_total")
                .withMetricHelp("The global number of naturalId queries executed against the database "
                        + "(getNaturalIdQueryExecutionCount)")
                .withMetricSample(hibernateStatistics::getNaturalIdQueryExecutionCount)
                .build());

        return metrics;
    }

    // CHECKSTYLE-ON: MethodLength
}
