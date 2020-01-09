package org.hsmith.jmetrics.config;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;
import org.hsmith.jmetrics.general.Builder;

/**
 * Object to facilitate building configuration objects for the Metrics server.
 */
public interface MetricServerConfigBuilder extends Builder<MetricServerConfig> {

    /**
     * Server to run the server on. Must be a valid port. Server startup will fail if the port is already in use.
     */
    MetricServerConfigBuilder withServerHttpPort(int serverHttpPort);

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
     * Enable collection of metrics from the provided jetty server.
     */
    MetricServerConfigBuilder collectJettyMetrics(Server jettyServer);

    /**
     * Enable collection of hibernate metrics from the provided session factory
     */
    MetricServerConfigBuilder collectHibernateMetrics(SessionFactory sessionFactory);
}
