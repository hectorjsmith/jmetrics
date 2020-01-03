package org.hsmith.jmetrics.config.impl;

import org.eclipse.jetty.server.Server;
import org.hsmith.jmetrics.config.MetricServerConfig;

final class MetricServerConfigImpl implements MetricServerConfig {
    private final int serverHttpPort;
    private final int serverMaxThreads;
    private final int serverMinThreads;
    private final int serverIdleTimeout;
    private final boolean collectJvmMetrics;
    private final boolean collectJettyMetrics;
    private final Server jettyServer;

    MetricServerConfigImpl(
            final int serverHttpPort,
            final int serverMaxThreads,
            final int serverMinThreads,
            final int serverIdleTimeout,
            final boolean collectJvmMetrics,
            final boolean collectJettyMetrics,
            final Server jettyServer) {

        this.serverHttpPort = serverHttpPort;
        this.serverMaxThreads = serverMaxThreads;
        this.serverMinThreads = serverMinThreads;
        this.serverIdleTimeout = serverIdleTimeout;
        this.collectJvmMetrics = collectJvmMetrics;
        this.collectJettyMetrics = collectJettyMetrics;
        this.jettyServer = jettyServer;
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
    public Server getJettyServer() {
        return this.jettyServer;
    }
}
