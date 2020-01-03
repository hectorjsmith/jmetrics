package org.hsmith.jmetrics.config;

import org.eclipse.jetty.server.Server;
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

    /**
     * Collect JVM metrics. This will export metrics related to the Java JVM this application is running on.
     */
    MetricServerConfigBuilder collectJvmMetrics();

    /**
     * Enable/disable collection of JBM metrics.
     * This will export metrics related the Java JVM this application is running on.
     */
    MetricServerConfigBuilder collectJvmMetrics(boolean value);

    /**
     * Collect Jetty metrics.
     * The metrics server will create a new Jetty server instance to collect metrics from.
     */
    MetricServerConfigBuilder collectJettyMetrics();

    /**
     * Enable/disable collection of Jetty metrics.
     * The metrics server will create a new Jetty server instance to collect metrics from.
     */
    MetricServerConfigBuilder collectJettyMetrics(boolean value);

    /**
     * Enable collection of metrics from the provided jetty server.
     */
    MetricServerConfigBuilder collectJettyMetrics(Server jettyServer);
}
