package org.hsmith.jmetrics.metrics;

import java.util.List;

public interface Metric {
    MetricType getMetricType();

    String getMetricName();

    String getMetricHelp();

    List<MetricSample> getMetricSamples();
}
