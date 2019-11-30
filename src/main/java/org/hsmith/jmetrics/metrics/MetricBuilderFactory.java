package org.hsmith.jmetrics.metrics;

/**
 * Factory class to build new instances of MetricBuilder as needed.
 */
public interface MetricBuilderFactory {
    /**
     * Create a new instance of a MetricBuilder.
     */
    MetricBuilder newInstance();
}
