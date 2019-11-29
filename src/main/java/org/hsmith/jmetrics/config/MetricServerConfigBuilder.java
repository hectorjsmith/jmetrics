package org.hsmith.jmetrics.config;

import org.hsmith.jmetrics.general.Builder;

/**
 * Object to facilitate building configuration objects for the Metrics server.
 */
public interface MetricServerConfigBuilder extends Builder<MetricServerConfig> {

    /**
     * Server to run the server on. Must be a valid port. Server startup will fail if the port is already in use.
     */
    MetricServerConfigBuilder withServerHttpPort(int serverHttpPort);

    MetricServerConfigBuilder withServerMaxThreads(int serverMaxThreads);

    MetricServerConfigBuilder withServerMinThreads(int serverMinThreads);

    MetricServerConfigBuilder withServerIdleTimeout(int serverIdleTimout);

    MetricServerConfigBuilder collectJvmMetrics();

    MetricServerConfigBuilder collectJvmMetrics(boolean value);

    MetricServerConfigBuilder collectJettyMetrics();

    MetricServerConfigBuilder collectJettyMetrics(boolean value);
}
