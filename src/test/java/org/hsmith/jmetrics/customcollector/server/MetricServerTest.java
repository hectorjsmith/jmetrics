package org.hsmith.jmetrics.customcollector.server;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.customcollector.MockCollector;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;

public class MetricServerTest {
    public static final String HTTP_LOCALHOST = "http://localhost:";
    private final Logger logger = LogManager.getLogger(this.getClass());

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
        int port = 9980;

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(port)
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent(HTTP_LOCALHOST + port);

        assertNotNull("Content should not be null", content);
        assertTrue("Content length should be greater than 0", content.length() > 0);
    }

    @Test
    void startServerTestWithCustomMetrics() throws IOException {
        int port = 9980;
        int expectedContentLines = 19;
        int expectedContentLength = 560;
        int expectedContentLengthRange = 20;

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(port)
                .collectJvmMetrics(false)
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .withCollector(new MockCollector())
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent(HTTP_LOCALHOST + port);
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
