package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.collector.BaseCollector;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;

public class MockCollectorWithMetricLabels extends BaseCollector {
    @Override
    public String getCollectorName() {
        return getClass().getSimpleName();
    }

    @Override
    protected List<Metric> buildMetrics(MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName(MetricType.COUNTER.name())
                .withMetricSample("code", "one", () -> 1.0)
                .withMetricSample("code", "two", () -> 2.0)
                .withMetricSample("type", "x", () -> 3.0)
                .build());
        return metrics;
    }
}
