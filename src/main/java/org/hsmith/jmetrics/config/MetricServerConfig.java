package org.hsmith.jmetrics.config;

/**
 * Configuration for the metrics server.
 */
public interface MetricServerConfig {
    int getServerHttpPort();

    int getServerMaxThreads();

    int getServerMinThreads();

    int getServerIdleTimout();

    boolean collectJvmMetrics();

    boolean collectJettyMetrics();
}
