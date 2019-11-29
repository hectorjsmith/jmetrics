package org.hsmith.jmetrics.metrics;

import io.prometheus.client.Collector;

/**
 * Type of metric. There are four types of metrics supported by Prometheus.
 * More details on the provided metric types can be found at
 * <a href="https://prometheus.io/docs/concepts/metric_types/">https://prometheus.io/docs/concepts/metric_types/.</a>
 */
public enum MetricType {
    /**
     * A counter is a cumulative metric that represents a single monotonically increasing counter whose value
     * can only increase or be reset to zero on restart.
     */
    COUNTER(Collector.Type.COUNTER),

    /**
     * A gauge is a metric that represents a single numerical value that can arbitrarily go up and down.
     */
    GAUGE(Collector.Type.GAUGE),

    /**
     * A histogram samples observations (usually things like request durations or response sizes) and counts
     * them in configurable buckets. It also provides a sum of all observed values.
     */
    SUMMARY(Collector.Type.SUMMARY),

    /**
     * Similar to a histogram, a summary samples observations (usually things like request durations and
     * response sizes). While it also provides a total count of observations and a sum of all observed values,
     * it calculates configurable quantiles over a sliding time window.
     */
    HISTOGRAM(Collector.Type.HISTOGRAM);

    private final Collector.Type collectorType;

    MetricType(final Collector.Type collectorType) {
        this.collectorType = collectorType;
    }

    /**
     * Get the metric type as defined in the Promehteus client library.
     */
    public Collector.Type getCollectorType() {
        return this.collectorType;
    }
}
