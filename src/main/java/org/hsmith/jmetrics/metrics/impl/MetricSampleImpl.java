package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.metrics.MetricSample;

import java.util.function.DoubleSupplier;

public final class MetricSampleImpl implements MetricSample {
    @Override
    public String getMetricLabelName() {
        return null;
    }

    @Override
    public String getMetricLabelValue() {
        return null;
    }

    @Override
    public DoubleSupplier getSampleValueFunction() {
        return null;
    }

    @Override
    public Double getSampleValue() {
        return null;
    }
}
