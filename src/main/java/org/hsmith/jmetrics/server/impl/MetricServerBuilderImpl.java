package org.hsmith.jmetrics.server.impl;

import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.general.impl.BuilderBase;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.MetricServerBuilder;

import java.util.HashSet;
import java.util.Set;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerBuilderImpl extends BuilderBase implements MetricServerBuilder {
    private MetricServerConfig config;
    private Set<Collector> collectorSet;

    public MetricServerBuilderImpl(final MetricServerConfig config) {
        assertNotNull(config, "config");
        this.collectorSet = new HashSet<>();
        this.config = config;
    }

    @Override
    public MetricServerBuilder withCollector(final Collector collector) {
        assertNotNull(collector, "collector");
        this.collectorSet.add(collector);
        return this;
    }

    @Override
    public MetricServer build() {
        return new MetricServerImpl(this.config, this.collectorSet);
    }
}

// CHECKSTYLE-OFF: HiddenField
