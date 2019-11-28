package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.collector.BaseCollector;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;

public class MockCollectorWithAllMetricTypes extends BaseCollector {
    @Override
    public String getCollectorName() {
        return getClass().getSimpleName();
    }

    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName(MetricType.COUNTER.name())
                .withMetricSample(() -> 100.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName(MetricType.GAUGE.name())
                .withMetricSample(() -> 200.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.HISTOGRAM)
                .withMetricName(MetricType.HISTOGRAM.name())
                .withMetricSample(() -> 300.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.SUMMARY)
                .withMetricName(MetricType.SUMMARY.name())
                .withMetricSample(() -> 400.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.UNTYPED)
                .withMetricName(MetricType.UNTYPED.name())
                .withMetricSample(() -> 500.0)
                .build());
        return metrics;
    }
}

/*

        // With labels
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric_e")
                .withMetricHelp("Test metric E")
                .withMetricSample("type", "a", () -> 1_000.0)
                .withMetricSample("type", "b", () -> 1_100.0)
                .withMetricSample("type", "c", () -> 1_200.0)
                .withMetricSample("code", "2x", () -> 2_000.0)
                .withMetricSample("code", "3x", () -> 3_000.0)
                .build());
 */