package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.collector.BaseCollector;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;

public class NewCustomCollector extends BaseCollector {
    @Override
    public String getCollectorName() {
        return "Random collector";
    }

    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric_a")
                .withMetricHelp("Test metric A")
                .withMetricSample(() -> 100.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("test_metric_b")
                .withMetricHelp("Test metric B")
                .withMetricSample(() -> 200.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.HISTOGRAM)
                .withMetricName("test_metric_c")
                .withMetricHelp("Test metric C")
                .withMetricSample(() -> 300.0)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.SUMMARY)
                .withMetricName("test_metric_d")
                .withMetricHelp("Test metric D")
                .withMetricSample(() -> 400.0)
                .build());

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
        return metrics;
    }
}
