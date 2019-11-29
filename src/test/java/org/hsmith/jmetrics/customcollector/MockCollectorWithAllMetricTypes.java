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
