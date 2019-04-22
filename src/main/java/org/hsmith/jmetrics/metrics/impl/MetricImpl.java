package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricSample;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.List;

public final class MetricImpl implements Metric {
    private final MetricType metricType;
    private final String metricName;
    private final String metricHelp;
    private final List<MetricSample> metricSamples;

    MetricImpl(final MetricType metricType,
               final String metricName,
               final String metricHelp,
               final List<MetricSample> metricSamples) {

        this.metricType = metricType;
        this.metricName = metricName;
        this.metricHelp = metricHelp;
        this.metricSamples = metricSamples;
    }

    @Override
    public MetricType getMetricType() {
        return this.metricType;
    }

    @Override
    public String getMetricName() {
        return this.metricName;
    }

    @Override
    public String getMetricHelp() {
        return this.metricHelp;
    }

    @Override
    public List<MetricSample> getMetricSamples() {
        return this.metricSamples;
    }
}
