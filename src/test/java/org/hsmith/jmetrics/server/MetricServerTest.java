package org.hsmith.jmetrics.server;

import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MetricServerTest {

    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }


    @Test
    void startServerTest() throws IOException {
        MetricServerConfig config = mock(MetricServerConfig.class);
        when(config.getServerHttpPort()).thenReturn(9977);
        when(config.getServerMaxThreads()).thenReturn(200);
        when(config.getServerMinThreads()).thenReturn(5);
        when(config.getServerIdleTimout()).thenReturn(60_000);
        when(config.collectJvmMetrics()).thenReturn(true);
        when(config.collectJettyMetrics()).thenReturn(true);
        when(config.collectQueuedThreadPoolMetrics()).thenReturn(true);

        MetricServer metricServer = new MetricServerBuilderImpl()
                .withServerConfig(config)
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent("http://localhost:9977");
        assertNotNull(content);
        assertTrue(content.length() > 50);

        System.out.println("Content length: " + content.length());
        System.out.println("Content lines:  " + content.split("\r\n|\r|\n").length);

//        Uncomment for manual testing
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}