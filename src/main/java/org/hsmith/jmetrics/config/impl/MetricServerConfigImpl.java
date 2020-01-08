package org.hsmith.jmetrics.config.impl;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import org.hsmith.jmetrics.config.MetricServerConfig;

import java.util.Objects;

final class MetricServerConfigImpl implements MetricServerConfig {
    private final int serverHttpPort;
    private final boolean collectJvmMetrics;
    private final Server jettyServer;
    private final SessionFactory sessionFactory;

    MetricServerConfigImpl(
            final int serverHttpPort,
            final boolean collectJvmMetrics,
            final Server jettyServer,
            final SessionFactory sessionFactory) {

        this.serverHttpPort = serverHttpPort;
        this.collectJvmMetrics = collectJvmMetrics;
        this.jettyServer = jettyServer;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int getServerHttpPort() {
        return this.serverHttpPort;
    }

    @Override
    public boolean collectJvmMetrics() {
        return this.collectJvmMetrics;
    }

    @Override
    public boolean collectJettyMetrics() {
        return Objects.nonNull(this.jettyServer);
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
