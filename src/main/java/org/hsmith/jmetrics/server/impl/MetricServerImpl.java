package org.hsmith.jmetrics.server.impl;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.server.MetricServer;

import java.io.IOException;

public final class MetricServerImpl implements MetricServer {
    private final Logger logger;
    private final MetricServerConfig config;
    private HTTPServer httpServer;

    MetricServerImpl(final MetricServerConfig config) {
        this.logger = LogManager.getLogger(this.getClass());
        this.config = config;
    }

    @Override
    public void startServer() throws IOException {
        // Setup base metrics
        StatisticsHandler statisticsHandler = new StatisticsHandler();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(
                config.getServerMaxThreads(),
                config.getServerMinThreads(),
                config.getServerIdleTimout());

        setupMetricCollectors(queuedThreadPool, statisticsHandler);

        // Setup server
        Server metricsServer = new Server(queuedThreadPool);
        metricsServer.setHandler(statisticsHandler);
        this.httpServer = new HTTPServer(config.getServerHttpPort());

        logger.info("Metrics server started on port " + config.getServerHttpPort());
    }

    @Override
    public void stopServer() {
        logger.info("Stopping metrics server");
        this.httpServer.stop();
    }

    private void setupMetricCollectors(final QueuedThreadPool queuedThreadPool,
                                       final StatisticsHandler statisticsHandler) {
        // Default JVM metrics
        DefaultExports.initialize();

        // Initializers
//        StatisticsHandlerCollector.initialize(statisticsHandler);
//        QueuedThreadPoolCollector.initialize(queuedThreadPool);
    }
}
