package org.hsmith.jmetrics.config.impl;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.MetricServerConfigBuilder;
import org.hsmith.jmetrics.general.impl.BuilderBase;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerConfigBuilderImpl extends BuilderBase implements MetricServerConfigBuilder {
    private static final int MIN_VALID_PORT = 1000;
    private static final int MAX_VALID_PORT = 65535;

    private int serverHttpPort;
    private boolean collectJvmMetrics;
    private Server jettyServer;
    private SessionFactory sessionFactory;

    public MetricServerConfigBuilderImpl() {
        this.withServerHttpPort(MetricServerConfigDefaults.SERVER_PORT)
                .collectJvmMetrics(MetricServerConfigDefaults.COLLECT_JVM_METRICS);
    }

    @Override
    public MetricServerConfigBuilder withServerHttpPort(final int serverHttpPort) {
        assertNumberBetween(serverHttpPort, "serverHttpPort", MIN_VALID_PORT, MAX_VALID_PORT);
        this.serverHttpPort = serverHttpPort;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectJvmMetrics() {
        return collectJvmMetrics(true);
    }

    @Override
    public MetricServerConfigBuilder collectJvmMetrics(final boolean value) {
        this.collectJvmMetrics = value;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectJettyMetrics(final Server jettyServer) {
        this.jettyServer = jettyServer;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectHibernateMetrics(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        return this;
    }

    @Override
    public MetricServerConfig build() {
        if (serverHttpPort > MAX_VALID_PORT || serverHttpPort < MIN_VALID_PORT) {
            throw new IllegalArgumentException(String.format(
                    "Invalid server port: %d. Port must be between %d and %d",
                    serverHttpPort, MIN_VALID_PORT, MAX_VALID_PORT));
        }

        return new MetricServerConfigImpl(
                this.serverHttpPort,
                this.collectJvmMetrics,
                this.jettyServer,
                this.sessionFactory
        );
    }
}

// CHECKSTYLE-ON: HiddenField
