package org.hsmith.jmetrics.server.impl;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.collector.QueuedThreadPoolCollector;
import org.hsmith.jmetrics.collector.JettyStatisticsCollector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.impl.MetricBuilderFactoryImpl;
import org.hsmith.jmetrics.server.MetricServer;

import java.io.IOException;
import java.util.Set;

public final class MetricServerImpl implements MetricServer {
    private final Logger logger;
    private final MetricServerConfig config;
    private final Set<Collector> collectorSet;
    private HTTPServer httpServer;

    MetricServerImpl(final MetricServerConfig config,
                     final Set<Collector> collectorSet) {
        this.logger = LogManager.getLogger(this.getClass());
        this.config = config;
        this.collectorSet = collectorSet;
    }

    @Override
    public void startServer() throws IOException {
        // Setup base metrics
        StatisticsHandler jettyStatistics = new StatisticsHandler();
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(
                config.getServerMaxThreads(),
                config.getServerMinThreads(),
                config.getServerIdleTimout());

        setupMetricCollectors(queuedThreadPool, jettyStatistics);

        // Setup server
        Server metricsServer = new Server(queuedThreadPool);
        metricsServer.setHandler(jettyStatistics);
        this.httpServer = new HTTPServer(config.getServerHttpPort());

        logger.info(String.format("Metrics server started on port: %d", config.getServerHttpPort()));
    }

    @Override
    public void stopServer() {
        logger.info("Stopping metrics server");
        this.httpServer.stop();
    }

    private void setupMetricCollectors(final QueuedThreadPool queuedThreadPool,
                                       final StatisticsHandler jettyStatistics) {

        MetricBuilderFactory metricBuilderFactory = new MetricBuilderFactoryImpl();

        // Default JVM metrics
        if (config.collectJvmMetrics()) {
            logger.debug("Initializing JVM metrics");
            DefaultExports.initialize();
        }
        if (config.collectJettyMetrics()) {
            logger.debug("Initializing Jetty metrics");
            new QueuedThreadPoolCollector(queuedThreadPool).initialize(metricBuilderFactory);
        }
        if (config.collectQueuedThreadPoolMetrics()) {
            logger.debug("Initializing queued thread pool metrics");
            new JettyStatisticsCollector(jettyStatistics).initialize(metricBuilderFactory);
        }

        for (Collector collector : collectorSet) {
            logger.debug("Initializing custom metric: " + collector.getCollectorName());
            collector.initialize(metricBuilderFactory);
        }
    }
}
