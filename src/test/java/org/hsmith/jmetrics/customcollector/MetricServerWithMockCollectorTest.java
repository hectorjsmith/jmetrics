package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class MetricServerWithMockCollectorTest {
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
        String content = startServerWithMockCollectorAndGetMetrics();

        assertNotNull("Content should not be null", content);
        assertTrue("Content should include test metric", content.contains("test_metric_a 100.0"));
    }

    private String startServerWithMockCollectorAndGetMetrics() throws IOException {
        int port = 9981;

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(port)
                .collectJvmMetrics(false)
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .withCollector(new MockCollector())
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent(TestUtil.HTTP_LOCALHOST + port);

        metricServer.stopServer();
        return content;
    }
}
