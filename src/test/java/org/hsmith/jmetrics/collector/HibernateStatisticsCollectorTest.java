package org.hsmith.jmetrics.collector;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class HibernateStatisticsCollectorTest {
    private static final String sessionOpenCountRegex = ".*hibernate_session_opened_total ([0-9]+).*";

    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        TestUtil.resetCollectors();
    }

    @Test
    void testGivenHibernateSessionFactoryWhenMetricsEanbledThenMetricsCorrectlyCollected() throws IOException {
        SessionFactory sessionFactory = setupH2Database();
        sessionFactory.getStatistics().setStatisticsEnabled(true);
        assertTrue(sessionFactory.getStatistics().isStatisticsEnabled(), "Statistics should be enabled");

        MetricServer server = startMetricsServerWithHibernate(sessionFactory);

        String metricsBefore = TestUtil.collectMetricsFromServer();

        Session session = sessionFactory.openSession();
        session.close();

        String metricsAfter = TestUtil.collectMetricsFromServer();

        server.stopServer();

        long countBefore = TestUtil.getMetricValue(metricsBefore, sessionOpenCountRegex);
        long countAfter = TestUtil.getMetricValue(metricsAfter, sessionOpenCountRegex);

        assertEquals(0, countBefore, "Session count should be 0 at start");
        assertEquals(1, countAfter, "Session count should be 1 after session opened");
    }

    private MetricServer startMetricsServerWithHibernate(SessionFactory sessionFactory) throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(TestUtil.TEST_PORT)
                .collectHibernateMetrics(sessionFactory)
                .build();

        return TestUtil.startMetricsServer(config);
    }

    private SessionFactory setupH2Database() {
        Properties prop = new Properties();
        prop.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        prop.setProperty("hibernate.connection.url", "jdbc:h2:./test_db");
        prop.setProperty("hibernate.connection.username", "sa");
        prop.setProperty("hibernate.connection.password", "sa");
        prop.setProperty("hibernate.hbm2ddl.auto", "create");
        //prop.setProperty("hibernate.generate_statistics", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .applySettings(prop)
                .build();

        return new MetadataSources(serviceRegistry)
                .buildMetadata()
                .buildSessionFactory();
    }
}
