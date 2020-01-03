package org.hsmith.jmetrics.collector;

import io.javalin.Javalin;
import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class JettyStatisticsCollectorTest {
    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @Test
    void testGivenJettyCollectorWhenGetNameThenNmeIsCorrect() {
        Collector collector = new JettyStatisticsCollector(null, null);
        assertEquals(JettyStatisticsCollector.class.getSimpleName(), collector.getCollectorName());
    }

    @Test
    void testGivenJettyServerWhenMetricsEnabledAndRequestsMadeThenMetricsShowRequestNumber() throws Exception {
        MetricServer metrics = startMetricsServer();

        int requestNumber = 10;
        String metricsBefore = collectMetricsFromServer();
        startJettyServerAndMakeRequests(metrics, requestNumber);
        String metricsAfter = collectMetricsFromServer();

        assertEquals(0.0, getRequestNumberFromMetrics(metricsBefore),
                "Number of requests should be 0");
        assertEquals(requestNumber, getRequestNumberFromMetrics(metricsAfter),
                "Expected number of requests measured to match number of requests made");

        metrics.stopServer();
    }

    private MetricServer startMetricsServer() throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(TestUtil.TEST_PORT)
                .collectJettyMetrics()
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .build();

        metricServer.startServer();
        return metricServer;
    }

    private String collectMetricsFromServer() throws IOException {
        return TestUtil.getWebpageContent(TestUtil.HTTP_LOCALHOST + TestUtil.TEST_PORT);
    }

    private double getRequestNumberFromMetrics(String metrics) {
        String regexStr = ".*jetty_requests_total ([0-9]+).*";
        Pattern p = Pattern.compile(regexStr, Pattern.DOTALL);

        Matcher matcher = p.matcher(metrics);
        assertTrue(matcher.matches(), "Metrics data should match regex");

        String requestNumStr = matcher.group(1);
        return Integer.parseInt(requestNumStr);
    }

    private void startJettyServerAndMakeRequests(MetricServer metricServer, int requestNumber) throws Exception {
        int testServerPort = 11994;
        String testServerResponse = "Hello World";

        Javalin app = Javalin.create(config -> {
            config.server(metricServer::getJettyServer);
        });

        app.get("/", ctx -> ctx.result(testServerResponse));
        app.start(testServerPort);

        for (int i = 0; i < requestNumber; i++) {
            String response = TestUtil.getHTML(TestUtil.HTTP_LOCALHOST + testServerPort + "/");
            assertEquals(testServerResponse, response);
        }
    }
}