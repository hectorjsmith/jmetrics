package org.hsmith.jmetrics.server.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.MetricServerBuilder;

public final class MetricServerBuilderImpl implements MetricServerBuilder {
    private MetricServerConfig config;

    @Override
    public MetricServerBuilder withServerConfig(MetricServerConfig config) {
        this.config = config;
        return this;
    }

    @Override
    public MetricServer build() {
        return new MetricServerImpl(this.config);
    }
}
