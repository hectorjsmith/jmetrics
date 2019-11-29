package org.hsmith.jmetrics.server;

import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.general.Builder;

/**
 * Object to handle building a metrics server based on a configuration object.
 * Use a MetricServerConfigBuilder to generate a configuration object.
 * @see org.hsmith.jmetrics.config.MetricServerConfigBuilder
 */
public interface MetricServerBuilder extends Builder<MetricServer> {
    /**
     * Add custom collectors. Call this method as many times as needed to register all custom collectors.
     */
    MetricServerBuilder withCollector(Collector collector);
}
