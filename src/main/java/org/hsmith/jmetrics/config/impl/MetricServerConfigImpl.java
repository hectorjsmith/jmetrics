package org.hsmith.jmetrics.config.impl;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import org.hsmith.jmetrics.config.MetricServerConfig;

import java.util.Objects;

final class MetricServerConfigImpl implements MetricServerConfig {
    private final int serverHttpPort;
    private final int serverMaxThreads;
    private final int serverMinThreads;
    private final int serverIdleTimeout;
    private final boolean collectJvmMetrics;
    private final boolean collectJettyMetrics;
    private final Server jettyServer;
    private final SessionFactory sessionFactory;

    MetricServerConfigImpl(
            final int serverHttpPort,
            final int serverMaxThreads,
            final int serverMinThreads,
            final int serverIdleTimeout,
            final boolean collectJvmMetrics,
            final boolean collectJettyMetrics,
            final Server jettyServer,
            final SessionFactory sessionFactory) {

        this.serverHttpPort = serverHttpPort;
        this.serverMaxThreads = serverMaxThreads;
        this.serverMinThreads = serverMinThreads;
        this.serverIdleTimeout = serverIdleTimeout;
        this.collectJvmMetrics = collectJvmMetrics;
        this.collectJettyMetrics = collectJettyMetrics;
        this.jettyServer = jettyServer;
        this.sessionFactory = sessionFactory;
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

    @Override
    public boolean collectHibernateMetrics() {
        return Objects.nonNull(this.sessionFactory);
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
}
