package org.hsmith.jmetrics.collector;

import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricSample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseCollector extends io.prometheus.client.Collector implements Collector {
    private final List<Metric> metrics = new ArrayList<>();

    protected BaseCollector() {
    }

    @Override
    public final List<MetricFamilySamples> collect() {
        return metrics.stream().map(this::metricToMetricFamilySample).collect(Collectors.toList());
    }

    @Override
    public final void initialize(final MetricBuilderFactory metricBuilderFactory) {
        buildMetrics(metricBuilderFactory);
        this.register();
    }

    protected abstract void buildMetrics(MetricBuilderFactory metricBuilderFactory);

    protected final void addMetric(final Metric metric) {
        metrics.add(metric);
    }

    private MetricFamilySamples metricToMetricFamilySample(final Metric metric) {
        return new MetricFamilySamples(
                metric.getMetricName(),
                metric.getMetricType().getCollectorType(),
                metric.getMetricHelp(),
                metricToMetricFamilySampleSamples(metric)
        );
    }

    private List<MetricFamilySamples.Sample> metricToMetricFamilySampleSamples(final Metric metric) {
        List<MetricFamilySamples.Sample> sampleList = new ArrayList<>();

        for (MetricSample sample : metric.getMetricSamples()) {
            List<String> sampleLabelNames = new ArrayList<>();
            List<String> sampleLabelValues = new ArrayList<>();
            if (!sample.getMetricLabelName().isEmpty() && !sample.getMetricLabelValue().isEmpty()) {
                sampleLabelNames.add(sample.getMetricLabelName());
                sampleLabelValues.add(sample.getMetricLabelValue());
            }

            sampleList.add(new MetricFamilySamples.Sample(
                    metric.getMetricName(),
                    sampleLabelNames,
                    sampleLabelValues,
                    sample.getSampleValue()
            ));
        }
        return sampleList;
    }
}
