package org.hsmith.jmetrics.config;

import org.eclipse.jetty.server.Server;

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

    Server getJettyServer();
}
