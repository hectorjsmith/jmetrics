package org.hsmith.jmetrics.metrics;

import java.util.function.DoubleSupplier;

/**
 * A sample is the result of measuring a Metric a single time.
 * Samples are double values.
 * Samples can be grouped under labels.
 * @see org.hsmith.jmetrics.metrics.Metric
 */
public interface MetricSample {
    /**
     * Get the name of the parent label, if any.
     */
    String getMetricLabelName();

    /**
     * Get the value of the parent label, if any.
     */
    String getMetricLabelValue();

    /**
     * Get the function that generates values for this sample.
     */
    DoubleSupplier getSampleValueFunction();

    /**
     * Get the value of this sample. This will invoke the function defined as the source of this metric sample
     * and return its result.
     */
    Double getSampleValue();
}
