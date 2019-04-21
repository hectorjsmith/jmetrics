package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilder;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.function.DoubleSupplier;

public final class MetricBuilderImpl implements MetricBuilder {
    @Override
    public MetricBuilder withMetricType(final MetricType metricType) {
        return null;
    }

    @Override
    public MetricBuilder withMetricName(final String metricName) {
        return null;
    }

    @Override
    public MetricBuilder withMetricHelp(final String metricHelp) {
        return null;
    }

    @Override
    public MetricBuilder withSampleValueFunction(final DoubleSupplier sampleValueFunction) {
        return null;
    }

    @Override
    public MetricBuilder withMetricSample(final DoubleSupplier sampleValueFunction) {
        return null;
    }

    @Override
    public MetricBuilder withMetricSample(final String sampleLabelName,
                                          final String sampleLabelValue,
                                          final DoubleSupplier sampleValueFunction) {
        return null;
    }

    @Override
    public Metric build() {
        return new MetricImpl();
    }
}
