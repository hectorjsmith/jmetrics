package org.hsmith.jmetrics.config;

import org.hsmith.jmetrics.general.Builder;

public interface MetricServerConfigBuilder extends Builder<MetricServerConfig> {

    MetricServerConfigBuilder withServerHttpPort(int serverHttpPort);

    MetricServerConfigBuilder withServerMaxThreads(int serverMaxThreads);

    MetricServerConfigBuilder withServerMinThreads(int serverMinThreads);

    MetricServerConfigBuilder withServerIdleTimeout(int serverIdleTimout);

    MetricServerConfigBuilder collectJvmMetrics();

    MetricServerConfigBuilder collectJvmMetrics(boolean value);

    MetricServerConfigBuilder collectJettyMetrics();

    MetricServerConfigBuilder collectJettyMetrics(boolean value);

    MetricServerConfigBuilder collectQueuedThreadPoolMetrics();

    MetricServerConfigBuilder collectQueuedThreadPoolMetrics(boolean value);
}
