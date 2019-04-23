package org.hsmith.jmetrics.server;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;

import static junit.framework.TestCase.*;

class MetricServerTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        // Clearing all collectors
        CollectorRegistry.defaultRegistry.clear();

        // Using reflection to set the initialized flag on the DefaultExports back to false
        // Otherwise the default exports never get re-enabled
        Field field = DefaultExports.class.getDeclaredField("initialized");
        field.setAccessible(true);
        field.set(null, false);
    }

    @Test
    void startServerTestFullMetrics() throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(9977)
                .collectJvmMetrics()
                .collectJettyMetrics()
                .collectQueuedThreadPoolMetrics()
                .build();

        startServerTestGeneral(config, 9977, 194, 10_100, 500);
    }

    @Test
    void startServerTestDefaultMetrics() throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(9978)
                .build();

        startServerTestGeneral(config, 9978, 124, 6700, 100);
    }

    @Test
    void startServerTestJvmAndJettyMetrics() throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(9979)
                .collectJettyMetrics()
                .build();

        startServerTestGeneral(config, 9979, 136, 7310, 100);
    }

    private void startServerTestGeneral(final MetricServerConfig config,
                                        final int port,
                                        final int expectedContentLines,
                                        final int expectedContentLength,
                                        final int expectedContentLengthRange) throws IOException {

        MetricServer metricServer = new MetricServerBuilderImpl()
                .withServerConfig(config)
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent("http://localhost:" + port);
        assertNotNull(content);
        assertTrue(content.length() > 0);

        int contentLength = content.length();
        int contentLines = content.split("\r\n|\r|\n").length;

        logger.info("Content length: " + contentLength);
        logger.info("Content lines:  " + contentLines);

        assertEquals("Unexpected number of lines", expectedContentLines, contentLines);
        assertTrue("Unexpected content length",
                contentLength > expectedContentLength - expectedContentLengthRange &&
                        contentLength < expectedContentLength + expectedContentLengthRange);

//        Uncomment for manual testing
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        metricServer.stopServer();
    }
}