package org.hsmith.jmetrics.config;

import org.hsmith.jmetrics.general.Builder;

public interface MetricServerConfigBuilder extends Builder<MetricServerConfig> {

    MetricServerConfigBuilder withServerHttpPort(int serverHttpPort);

    MetricServerConfigBuilder withServerMaxThreads(int serverMaxThreads);

    MetricServerConfigBuilder withServerMinThreads(int serverMinThreads);

    MetricServerConfigBuilder withServerIdleTimout(int serverIdleTimout);
}
