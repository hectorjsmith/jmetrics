package org.hsmith.jmetrics.config;

public interface MetricServerConfig {
    int getServerHttpPort();

    int getServerMaxThreads();

    int getServerMinThreads();

    int getServerIdleTimout();
}
