package org.hsmith.jmetrics.metrics;

import org.hsmith.jmetrics.general.Builder;

import java.util.function.DoubleSupplier;

/**
 * Builder class to facilitate the creation of new metrics.
 * @see org.hsmith.jmetrics.metrics.Metric
 */
public interface MetricBuilder extends Builder<Metric> {
    /**
     * Set the MetricType for the new Metric. Must be one of the supported metric types.
     */
    MetricBuilder withMetricType(MetricType metricType);

    /**
     * Set the name of the new Metric. The name will be used when querying the Metric in Prometheus.
     * By convention the metric name is all lower case with underscores instead of spaces.
     */
    MetricBuilder withMetricName(String metricName);

    /**
     * Set the help message for the new Metric. The help message is displayed in the metric export page
     * along with the Metric data. The goal is to give a brief description of the metric being exported.
     */
    MetricBuilder withMetricHelp(String metricHelp);

    /**
     * Define a function to be called to get the metric value when the metric server is queried. The function
     * will be invoked every time metrics are collected from the metrics server, so performance is an important
     * consideration.
     */
    MetricBuilder withMetricSample(DoubleSupplier sampleValueFunction);

    /**
     * Define a function to be called to get the metric value when the metric server is queried. The function
     * will be invoked every time metrics are collected from the metrics server, so performance is an important
     * consideration.
     * By setting labels the metric can export multiple values. One value per sampleLabelValue. The sampleLabelName
     * is used to group the sampleLabelValues into groups.
     * It is possible to define a metric with multiple sampleLabelNames and multiple sampleLabelValues.
     * See the collector for Jetty metrics for an example.
     * @see org.hsmith.jmetrics.collector.JettyStatisticsCollector
     */
    MetricBuilder withMetricSample(String sampleLabelName,
                                   String sampleLabelValue,
                                   DoubleSupplier sampleValueFunction);
}
