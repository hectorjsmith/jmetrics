package org.hsmith.jmetrics.config.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.MetricServerConfigBuilder;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerConfigBuilderImpl implements MetricServerConfigBuilder {
    private int serverHttpPort;
    private int serverMaxThreads;
    private int serverMinThreads;
    private int serverIdleTimeout;

    @Override
    public MetricServerConfigBuilder withServerHttpPort(final int serverHttpPort) {
        this.serverHttpPort = serverHttpPort;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerMaxThreads(final int serverMaxThreads) {
        this.serverMaxThreads = serverMaxThreads;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerMinThreads(final int serverMinThreads) {
        this.serverMinThreads = serverMinThreads;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerIdleTimout(final int serverIdleTimout) {
        this.serverIdleTimeout = serverIdleTimeout;
        return this;
    }

    @Override
    public MetricServerConfig build() {
        return new MetricServerConfigImpl(
                this.serverHttpPort,
                this.serverMaxThreads,
                this.serverMinThreads,
                this.serverIdleTimeout
        );
    }
}

// CHECKSTYLE-ON: HiddenField
