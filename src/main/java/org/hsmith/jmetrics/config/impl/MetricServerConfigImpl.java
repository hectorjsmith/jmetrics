package org.hsmith.jmetrics.config.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;

public final class MetricServerConfigImpl implements MetricServerConfig {
    private final int serverHttpPort;
    private final int serverMaxThreads;
    private final int serverMinThreads;
    private final int serverIdleTimeout;

    public MetricServerConfigImpl(
            final int serverHttpPort,
            final int serverMaxThreads,
            final int serverMinThreads,
            final int serverIdleTimeout) {

        this.serverHttpPort = serverHttpPort;
        this.serverMaxThreads = serverMaxThreads;
        this.serverMinThreads = serverMinThreads;
        this.serverIdleTimeout = serverIdleTimeout;
    }

    @Override
    public int getServerHttpPort() {
        return this.serverHttpPort;
    }

    @Override
    public int getServerMaxThreads() {
        return this.serverMaxThreads;
    }

    @Override
    public int getServerMinThreads() {
        return this.serverMinThreads;
    }

    @Override
    public int getServerIdleTimout() {
        return this.serverIdleTimeout;
    }
}
