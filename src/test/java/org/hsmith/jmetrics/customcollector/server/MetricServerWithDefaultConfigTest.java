package org.hsmith.jmetrics.customcollector.server;

import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;

public class MetricServerWithDefaultConfigTest {
    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        TestUtil.resetCollectors();
    }

    @Test
    void testGivenDefaultConfigWhenStartServerThenMetricsExported() throws IOException {
        String content = startDefaultServerAndGetMetrics();

        assertNotNull("Content should not be null", content);
        assertTrue("Content length should be greater than 0", content.length() > 0);
    }

    @Test
    void testGivenDefaultConfigWhenStartServerThenJvmMetricsExported() throws IOException {
        String content = startDefaultServerAndGetMetrics();

        assertNotNull("Content should not be null", content);
        assertTrue("Content should contain JVM metrics", content.contains("jvm"));
    }

    @Test
    void testGivenDefaultConfigWhenStartServerThenJettyMetricsNotExported() throws IOException {
        String content = startDefaultServerAndGetMetrics();

        assertNotNull("Content should not be null", content);
        assertFalse("Default export should not contain Jetty metrics", content.contains("jetty_requests_total"));
    }

    @Test
    void testGivenDefaultConfigWhenStartServerThenThreadPoolMetricsNotExported() throws IOException {
        String content = startDefaultServerAndGetMetrics();

        assertNotNull("Content should not be null", content);
        assertFalse("Default export should not contain Thread Pool metrics", content.contains("jetty_queued_thread_pool_threads"));
    }

    private String startDefaultServerAndGetMetrics() throws IOException {
        int port = 9980;

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(port)
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent(TestUtil.HTTP_LOCALHOST + port);

        metricServer.stopServer();
        return content;
    }
}
