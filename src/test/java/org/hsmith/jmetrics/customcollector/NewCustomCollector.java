package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.collector.BaseCollector;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

public class NewCustomCollector extends BaseCollector {
    @Override
    public void initialize(final MetricBuilderFactory metricBuilderFactory) {
        buildMetrics(metricBuilderFactory);
        super.register();
    }

    @Override
    public String getCollectorName() {
        return "Random collector";
    }

    private void buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric_a")
                .withMetricHelp("Test metric A")
                .withMetricSample(() -> 100.0)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("test_metric_b")
                .withMetricHelp("Test metric B")
                .withMetricSample(() -> 200.0)
                .build());
    }
}
