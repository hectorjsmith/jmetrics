package org.hsmith.jmetrics.config.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;

public final class MetricServerConfigImpl implements MetricServerConfig {
    private final int serverHttpPort;
    private final int serverMaxThreads;
    private final int serverMinThreads;
    private final int serverIdleTimeout;
    private final boolean collectJvmMetrics;
    private final boolean collectJettyMetrics;
    private final boolean collectQueuedThreadPoolMetrics;

    MetricServerConfigImpl(
            final int serverHttpPort,
            final int serverMaxThreads,
            final int serverMinThreads,
            final int serverIdleTimeout,
            final boolean collectJvmMetrics,
            final boolean collectJettyMetrics,
            final boolean collectQueuedThreadPoolMetrics) {

        this.serverHttpPort = serverHttpPort;
        this.serverMaxThreads = serverMaxThreads;
        this.serverMinThreads = serverMinThreads;
        this.serverIdleTimeout = serverIdleTimeout;
        this.collectJvmMetrics = collectJvmMetrics;
        this.collectJettyMetrics = collectJettyMetrics;
        this.collectQueuedThreadPoolMetrics = collectQueuedThreadPoolMetrics;
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

    @Override
    public boolean collectJvmMetrics() {
        return this.collectJvmMetrics;
    }

    @Override
    public boolean collectJettyMetrics() {
        return this.collectJettyMetrics;
    }

    @Override
    public boolean collectQueuedThreadPoolMetrics() {
        return this.collectQueuedThreadPoolMetrics;
    }
}
