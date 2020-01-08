package org.hsmith.jmetrics.config;

import org.eclipse.jetty.server.Server;
import org.hibernate.SessionFactory;

/**
 * Configuration for the metrics server.
 */
public interface MetricServerConfig {
    int getServerHttpPort();

    boolean collectJvmMetrics();

    boolean collectJettyMetrics();

    Server getJettyServer();

    boolean collectHibernateMetrics();

    SessionFactory getSessionFactory();
}
