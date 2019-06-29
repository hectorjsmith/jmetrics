package org.hsmith.jmetrics.server;

import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.general.Builder;

public interface MetricServerBuilder extends Builder<MetricServer> {
    MetricServerBuilder withCollector(Collector collector);
}
