package org.hsmith.jmetrics.collector;

import org.hsmith.jmetrics.metrics.MetricBuilderFactory;

/**
 * Defines a class that exposes metrics to be collected. A collector can collect metrics from anywhere
 * and once registered on the metrics server will expose them in a format that the Prometheus library understands.
 * A base collector is provided to help the creating of new custom collectors.
 * @see org.hsmith.jmetrics.server.MetricServerBuilder
 * @see org.hsmith.jmetrics.collector.BaseCollector
 */
public interface Collector {
    void initialize(MetricBuilderFactory metricBuilderFactory);

    String getCollectorName();
}
