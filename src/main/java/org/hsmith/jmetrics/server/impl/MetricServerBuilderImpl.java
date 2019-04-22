package org.hsmith.jmetrics.server.impl;

import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.MetricServerBuilder;

import java.util.HashSet;
import java.util.Set;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerBuilderImpl implements MetricServerBuilder {
    private MetricServerConfig config;
    private Set<Collector> collectorSet;

    public MetricServerBuilderImpl() {
        this.collectorSet = new HashSet<>();
    }

    @Override
    public MetricServerBuilder withServerConfig(final MetricServerConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public MetricServerBuilder withCollector(final Collector collector) {
        this.collectorSet.add(collector);
        return this;
    }

    @Override
    public MetricServer build() {
        return new MetricServerImpl(this.config, this.collectorSet);
    }
}

// CHECKSTYLE-OFF: HiddenField
