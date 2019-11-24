package org.hsmith.jmetrics.metrics;

import java.util.function.DoubleSupplier;

public interface MetricSample<T> {
    String getMetricLabelName();

    String getMetricLabelValue();

    DoubleSupplier getSampleValueFunction();

    Double getSampleValue();
}
