package org.hsmith.jmetrics.metrics;

import io.prometheus.client.Collector;

public enum MetricType {
    COUNTER(Collector.Type.COUNTER),
    GAUGE(Collector.Type.GAUGE),
    SUMMARY(Collector.Type.SUMMARY),
    HISTOGRAM(Collector.Type.HISTOGRAM),
    UNTYPED(Collector.Type.UNTYPED);

    private final Collector.Type collectorType;

    MetricType(final Collector.Type collectorType) {
        this.collectorType = collectorType;
    }

    public Collector.Type getCollectorType() {
        return this.collectorType;
    }
}
