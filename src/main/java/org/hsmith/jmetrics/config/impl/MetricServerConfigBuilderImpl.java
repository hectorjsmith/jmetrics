package org.hsmith.jmetrics.config.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.MetricServerConfigBuilder;
import org.hsmith.jmetrics.general.impl.BuilderBase;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerConfigBuilderImpl extends BuilderBase implements MetricServerConfigBuilder {
    private static final int MIN_VALID_PORT = 1000;
    private static final int MAX_VALID_PORT = 65535;
    private static final int MIN_VALID_THREADS = 1;
    private static final int MAX_VALID_THREADS = 1000;
    private static final int MIN_VALID_TIMEOUT = 1;

    private int serverHttpPort;
    private int serverMaxThreads;
    private int serverMinThreads;
    private int serverIdleTimeout;
    private boolean collectJvmMetrics;
    private boolean collectJettyMetrics;
    private boolean collectQueuedThreadPoolMetrics;

    public MetricServerConfigBuilderImpl() {
        this.withServerHttpPort(MetricServerConfigDefaults.SERVER_PORT)
                .withServerMinThreads(MetricServerConfigDefaults.SERVER_MIN_THREADS)
                .withServerMaxThreads(MetricServerConfigDefaults.SERVER_MAX_THREADS)
                .withServerIdleTimeout(MetricServerConfigDefaults.SERVER_IDLE_TIMEOUT)
                .collectJvmMetrics(MetricServerConfigDefaults.COLLECT_JVM_METRICS)
                .collectJettyMetrics(MetricServerConfigDefaults.COLLECT_JETTY_METRICS)
                .collectQueuedThreadPoolMetrics(MetricServerConfigDefaults.COLLECT_QUEUED_THREADPOOL_METRICS);
    }

    @Override
    public MetricServerConfigBuilder withServerHttpPort(final int serverHttpPort) {
        assertNumberBetween(serverHttpPort, "serverHttpPort", MIN_VALID_PORT, MAX_VALID_PORT);
        this.serverHttpPort = serverHttpPort;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerMaxThreads(final int serverMaxThreads) {
        assertNumberBetween(serverMaxThreads, "serverMaxThreads", MIN_VALID_THREADS, MAX_VALID_THREADS);
        this.serverMaxThreads = serverMaxThreads;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerMinThreads(final int serverMinThreads) {
        assertNumberBetween(serverMinThreads, "serverMinThreads", MIN_VALID_THREADS, MAX_VALID_THREADS);
        this.serverMinThreads = serverMinThreads;
        return this;
    }

    @Override
    public MetricServerConfigBuilder withServerIdleTimeout(final int serverIdleTimeout) {
        assertNumberBetween(serverIdleTimeout, "serverIdleTimeout", MIN_VALID_TIMEOUT, Double.MAX_VALUE);
        this.serverIdleTimeout = serverIdleTimeout;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectJvmMetrics() {
        return collectJettyMetrics(true);
    }

    @Override
    public MetricServerConfigBuilder collectJvmMetrics(final boolean value) {
        this.collectJvmMetrics = value;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectJettyMetrics() {
        return this.collectJettyMetrics(true);
    }

    @Override
    public MetricServerConfigBuilder collectJettyMetrics(final boolean value) {
        this.collectJettyMetrics = value;
        return this;
    }

    @Override
    public MetricServerConfigBuilder collectQueuedThreadPoolMetrics() {
        return this.collectQueuedThreadPoolMetrics(true);
    }

    @Override
    public MetricServerConfigBuilder collectQueuedThreadPoolMetrics(final boolean value) {
        this.collectQueuedThreadPoolMetrics = value;
        return this;
    }

    @Override
    public MetricServerConfig build() {
        return new MetricServerConfigImpl(
                this.serverHttpPort,
                this.serverMaxThreads,
                this.serverMinThreads,
                this.serverIdleTimeout,
                this.collectJvmMetrics,
                this.collectJettyMetrics,
                this.collectQueuedThreadPoolMetrics
        );
    }
}

// CHECKSTYLE-ON: HiddenField
