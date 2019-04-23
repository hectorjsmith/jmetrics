package org.hsmith.jmetrics.collector;

import org.hsmith.jmetrics.metrics.MetricBuilderFactory;

public interface Collector {
    void initialize(MetricBuilderFactory metricBuilderFactory);
}
