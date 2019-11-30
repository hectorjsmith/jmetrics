package org.hsmith.jmetrics.metrics;

import java.util.List;

/**
 * A Metric defines a measurement to export to Prometheus. It has a type, a name, a help message, and a collection
 * of samples.
 * @see org.hsmith.jmetrics.metrics.MetricSample
 */
public interface Metric {
    /**
     * Type of metric.
     * @see org.hsmith.jmetrics.metrics.MetricType
     */
    MetricType getMetricType();

    /**
     * Name of the metric. By convention the name should be all lower case with underscores instead of spaces.
     */
    String getMetricName();

    /**
     * Get the help message for this metric. The help message should briefly describe the purpose of the metric
     * of what it us measuring.
     */
    String getMetricHelp();

    /**
     * Collection of samples produced by this metric.
     */
    List<MetricSample> getMetricSamples();
}
