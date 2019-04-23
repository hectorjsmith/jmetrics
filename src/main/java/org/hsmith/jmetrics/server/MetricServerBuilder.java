package org.hsmith.jmetrics.server;

import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.general.Builder;

public interface MetricServerBuilder extends Builder<MetricServer> {
    MetricServerBuilder withServerConfig(MetricServerConfig config);

    MetricServerBuilder withCollector(Collector collector);
}
