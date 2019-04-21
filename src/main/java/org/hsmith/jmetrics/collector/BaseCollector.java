package org.hsmith.jmetrics.collector;

import org.hsmith.jmetrics.metrics.Metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseCollector extends io.prometheus.client.Collector implements Collector {

    private final List<MetricFamilySamples> metricFamilySamples;

    protected BaseCollector() {
        this.metricFamilySamples = new ArrayList<>();
        this.register();
    }

    @Override
    public final List<MetricFamilySamples> collect() {
        return this.metricFamilySamples;
    }

    protected final void addMetric(final Metric metric) {
        this.metricFamilySamples.add(metricToMetricFamilySample(metric));
    }

    private MetricFamilySamples metricToMetricFamilySample(final Metric<?> metric) {
        return new MetricFamilySamples(
                metric.getMetricName(),
                metric.getMetricType().getCollectorType(),
                metric.getMetricHelp(),
                Collections.singletonList(
                        new MetricFamilySamples.Sample(
                                metric.getMetricName(),
                                metric.getMetricLabelNames(),
                                metric.getMetricLabelValues(),
                                metric.getMetricValue())
                )
        );
    }
}
