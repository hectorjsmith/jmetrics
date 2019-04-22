package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.metrics.MetricSample;

import java.util.function.DoubleSupplier;

public final class MetricSampleImpl implements MetricSample {
    private final String metricLabelName;
    private final String metricLabelValue;
    private final DoubleSupplier sampleValueFunction;

    MetricSampleImpl(final String metricLabelName,
                            final String metricLabelValue,
                            final DoubleSupplier sampleValueFunction) {

        this.metricLabelName = metricLabelName;
        this.metricLabelValue = metricLabelValue;
        this.sampleValueFunction = sampleValueFunction;
    }

    @Override
    public String getMetricLabelName() {
        return this.metricLabelName;
    }

    @Override
    public String getMetricLabelValue() {
        return this.metricLabelValue;
    }

    @Override
    public DoubleSupplier getSampleValueFunction() {
        return this.sampleValueFunction;
    }

    @Override
    public Double getSampleValue() {
        return this.sampleValueFunction.getAsDouble();
    }
}
