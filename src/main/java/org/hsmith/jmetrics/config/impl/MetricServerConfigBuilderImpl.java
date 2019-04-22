package org.hsmith.jmetrics.config.impl;

import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.MetricServerConfigBuilder;

// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

public final class MetricServerConfigBuilderImpl implements MetricServerConfigBuilder {
    private int serverHttpPort;
    private int serverMaxThreads;
    private int serverMinThreads;
    private int serverIdleTimeout;
    private boolean collectJvmMetrics;
    private boolean collectJettyMetrics;
    private boolean collectQueuedThreadPoolMetrics;

    public MetricServerConfigBuilderImpl() {
        this.collectJvmMetrics = true;
    }

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
    public MetricServerConfigBuilder withServerIdleTimeout(final int serverIdleTimeout) {
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
