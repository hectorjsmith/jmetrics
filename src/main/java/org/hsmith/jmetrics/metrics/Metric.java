package org.hsmith.jmetrics.metrics;

import java.util.List;
import java.util.function.Function;

public interface Metric<T> {
    MetricType getMetricType();

    String getMetricName();

    String getMetricHelp();

    Function<T, Double> getMetricValueFunction();

    Double getMetricValue();

    List<String> getMetricLabelNames();

    List<String> getMetricLabelValues();
}
