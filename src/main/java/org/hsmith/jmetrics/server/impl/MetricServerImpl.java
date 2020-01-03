package org.hsmith.jmetrics.server.impl;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.collector.JettyStatisticsCollector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.impl.MetricBuilderFactoryImpl;
import org.hsmith.jmetrics.server.MetricServer;

import java.io.IOException;
import java.util.Set;

final class MetricServerImpl implements MetricServer {
    private final Logger logger;
    private final MetricServerConfig config;
    private final Set<Collector> collectorSet;
    private Server jettyServer;
    private HTTPServer httpServer;

    MetricServerImpl(final MetricServerConfig config,
                     final Set<Collector> collectorSet) {
        this.logger = LogManager.getLogger(this.getClass());
        this.config = config;
        this.jettyServer = config.getJettyServer();
        this.collectorSet = collectorSet;
    }

    @Override
    public void startServer() throws IOException {
        // Setup server
        httpServer = new HTTPServer(config.getServerHttpPort());

        // Setup metric collectors
        MetricBuilderFactory metricBuilderFactory = new MetricBuilderFactoryImpl();
        setupJettyMetricCollectors(metricBuilderFactory);
        setupMetricCollectors(metricBuilderFactory);

        logger.info(String.format("Metrics server started on port: %d", config.getServerHttpPort()));
    }

    @Override
    public void stopServer() {
        logger.info("Stopping metrics server");
        this.httpServer.stop();
    }

    @Override
    public Server getJettyServer() {
        return jettyServer;
    }

    private void setupJettyMetricCollectors(final MetricBuilderFactory metricBuilderFactory) {
        if (config.collectJettyMetrics()) {
            // Setup new jetty server if none provided
            if (jettyServer == null) {
                QueuedThreadPool queuedThreadPool = new QueuedThreadPool(
                        config.getServerMaxThreads(),
                        config.getServerMinThreads(),
                        config.getServerIdleTimout());
                jettyServer = new Server(queuedThreadPool);
            }

            // Setup statistics handler
            StatisticsHandler jettyStatistics = new StatisticsHandler();
            jettyServer.setHandler(jettyStatistics);

            logger.debug("Initializing Jetty metrics");
            new JettyStatisticsCollector(jettyStatistics, jettyServer.getThreadPool()).initialize(metricBuilderFactory);
        }
    }

    private void setupMetricCollectors(final MetricBuilderFactory metricBuilderFactory) {
        // Default JVM metrics
        if (config.collectJvmMetrics()) {
            logger.debug("Initializing JVM metrics");
            DefaultExports.initialize();
        }

        // Custom metric collectors
        for (Collector collector : collectorSet) {
            logger.debug("Initializing custom metric: " + collector.getCollectorName());
            collector.initialize(metricBuilderFactory);
        }
    }
}
