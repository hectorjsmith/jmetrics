package org.hsmith.jmetrics.collector;

import org.hibernate.SessionFactory;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;

public final class HibernateStatisticsCollector extends BaseCollector {
    private final SessionFactory sessionFactory;

    public HibernateStatisticsCollector(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String getCollectorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();

        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("hibernate_statistics_enabled")
                .withMetricHelp("Whether hibernate statistic collection is enabled")
                .withMetricSample(() -> sessionFactory.getStatistics().isStatisticsEnabled() ? 1 : 0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("hibernate_session_open_count_total")
                .withMetricHelp("Global number of sessions opened (getSessionOpenCount)")
                .withMetricSample(() -> sessionFactory.getStatistics().getSessionOpenCount())
                .build());

        return metrics;
    }
}
