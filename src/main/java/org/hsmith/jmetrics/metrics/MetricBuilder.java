package org.hsmith.jmetrics.metrics;

import org.hsmith.jmetrics.general.Builder;

import java.util.function.DoubleSupplier;

public interface MetricBuilder extends Builder<Metric> {

    MetricBuilder withMetricType(MetricType metricType);

    MetricBuilder withMetricName(String metricName);

    MetricBuilder withMetricHelp(String metricHelp);

    MetricBuilder withMetricSample(DoubleSupplier sampleValueFunction);

    MetricBuilder withMetricSample(String sampleLabelName,
                                   String sampleLabelValue,
                                   DoubleSupplier sampleValueFunction);
}
