package org.hsmith.jmetrics.collector;

import io.javalin.Javalin;
import org.eclipse.jetty.server.Server;
import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JettyStatisticsCollectorTest {
    private static final int testServerPort = 11994;
    private static final String testServerResponse = "Hello World";
    private static final String requestCountRegex = ".*jetty_requests_total ([0-9]+).*";

    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        TestUtil.resetCollectors();
    }

    @Test
    void testGivenJettyCollectorWhenGetNameThenNmeIsCorrect() {
        Collector collector = new JettyStatisticsCollector(null, null);
        assertEquals(JettyStatisticsCollector.class.getSimpleName(), collector.getCollectorName());
    }

    @Test
    void testGivenJettyServerCreatedByJavalinWhenMetricsEnabledAndRequestsMadeThenMetricsShowRequestNumber()
            throws Exception {

        Javalin app = Javalin.create();
        MetricServer metrics = startMetricsServerWithJettyServer(app.server().server());

        app.get("/", ctx -> ctx.result(testServerResponse));
        app.start(testServerPort);

        int requestNumber = 10;
        String metricsBefore = TestUtil.collectMetricsFromServer();
        makeRequests(requestNumber);
        String metricsAfter = TestUtil.collectMetricsFromServer();

        app.stop();
        metrics.stopServer();

        assertEquals(0.0, TestUtil.getMetricValue(metricsBefore, requestCountRegex),
                "Number of requests should be 0");
        assertEquals(requestNumber, TestUtil.getMetricValue(metricsAfter, requestCountRegex),
                "Expected number of requests measured to match number of requests made");
    }

    private MetricServer startMetricsServerWithJettyServer(Server jettyServer) throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(TestUtil.TEST_PORT)
                .collectJettyMetrics(jettyServer)
                .build();

        return TestUtil.startMetricsServer(config);
    }

    private void startJettyServerAndMakeRequests(MetricServer metricServer, int requestNumber) throws Exception {
        Javalin app = Javalin.create(config -> {
            config.server(metricServer::getJettyServer);
        });

        app.get("/", ctx -> ctx.result(testServerResponse));
        app.start(testServerPort);

        makeRequests(requestNumber);
        app.stop();
    }

    private void makeRequests(int requestNumber) throws Exception {
        for (int i = 0; i < requestNumber; i++) {
            String response = TestUtil.getHTML(TestUtil.HTTP_LOCALHOST + testServerPort + "/");
            assertEquals(testServerResponse, response);
        }
    }
}