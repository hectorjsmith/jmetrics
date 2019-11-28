package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.collector.Collector;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.hsmith.jmetrics.metrics.MetricType;
import org.hsmith.jmetrics.metrics.impl.MetricBuilderFactoryImpl;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.prometheus.client.Collector.MetricFamilySamples;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class MetricServerWithMockCollectorTest {
    private static double mockCounterForTest = 0.0;

    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @AfterEach
    void tearDown() throws NoSuchFieldException, IllegalAccessException {
        TestUtil.resetCollectors();
    }

    @Test
    void testGivenCustomCollectorWhenStartServerThenCustomMetricsExported() throws IOException {
        Collector mockCollector = new MockCollectorWithSingleMetric("mock", () -> 100.0);
        String content = startServerWithMockCollectorAndGetMetrics(mockCollector);

        assertNotNull("Content should not be null", content);
        assertTrue("Content should include test metric", content.contains("mock 100.0"));
    }

    @Test
    void testGivenCustomCollectorWhenMetricsCollectedThenCorrectTypeReturned() {
        String mockName = "mock";
        double mockValue = 500.0;

        MockCollectorWithSingleMetric mockCollector = new MockCollectorWithSingleMetric(mockName, () -> mockValue);
        mockCollector.initialize(new MetricBuilderFactoryImpl());
        List<MetricFamilySamples> rawMetrics = mockCollector.collect();

        assertTrue("Mock metric should be included in raw metrics",
                rawMetrics.stream().anyMatch(m -> m.name.equals(mockName)));
        assertTrue("Mock sample value be included in raw metrics",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(mockName)
                                && m.samples.stream().anyMatch(s -> s.value == mockValue)));
        assertTrue("Mock sample type is set to Counter",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(mockName)
                                && m.type == io.prometheus.client.Collector.Type.COUNTER));
    }

    @Test
    void testGivenCustomCollectorWithIncrementingValueWhenMetricsCollectedThenSampleValueChanges() {
        String mockName = "mock";

        MockCollectorWithSingleMetric mockCollector = new MockCollectorWithSingleMetric(mockName, this::doubleNumGenerator);
        mockCollector.initialize(new MetricBuilderFactoryImpl());

        List<MetricFamilySamples> firstCollection = mockCollector.collect();
        assertTrue("Sample value should be 1.0 in first collection",
                firstCollection
                        .stream()
                        .anyMatch(m -> m.name.equals(mockName)
                                && m.samples.stream().anyMatch(s -> s.value == 1.0)));

        List<MetricFamilySamples> secondCollection = mockCollector.collect();
        assertTrue("Sample value should be 2.0 in second collection",
                secondCollection
                        .stream()
                        .anyMatch(m -> m.name.equals(mockName)
                                && m.samples.stream().anyMatch(s -> s.value == 2.0)));
    }

    @Test
    void testGivenCustomCollectorWhenCollectMetricsThenAllMetricTypesAreExportedCorrectly() {
        MockCollectorWithAllMetricTypes mockCollector = new MockCollectorWithAllMetricTypes();
        mockCollector.initialize(new MetricBuilderFactoryImpl());

        List<MetricFamilySamples> rawMetrics = mockCollector.collect();

        assertTrue("Raw metrics should include a COUNTER metric",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(MetricType.COUNTER.name()) &&
                                m.type.equals(io.prometheus.client.Collector.Type.COUNTER)));
        assertTrue("Raw metrics should include a GAUGE metric",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(MetricType.GAUGE.name()) &&
                                m.type.equals(io.prometheus.client.Collector.Type.GAUGE)));
        assertTrue("Raw metrics should include a HISTOGRAM metric",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(MetricType.HISTOGRAM.name()) &&
                                m.type.equals(io.prometheus.client.Collector.Type.HISTOGRAM)));
        assertTrue("Raw metrics should include a SUMMARY metric",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(MetricType.SUMMARY.name()) &&
                                m.type.equals(io.prometheus.client.Collector.Type.SUMMARY)));
        assertTrue("Raw metrics should include a UNTYPED metric",
                rawMetrics
                        .stream()
                        .anyMatch(m -> m.name.equals(MetricType.UNTYPED.name()) &&
                                m.type.equals(io.prometheus.client.Collector.Type.UNTYPED)));
    }

    private double doubleNumGenerator() {
        return mockCounterForTest++;
    }

    private String startServerWithMockCollectorAndGetMetrics(Collector mockCollector) throws IOException {
        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(TestUtil.TEST_PORT)
                .collectJvmMetrics(false)
                .build();

        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .withCollector(mockCollector)
                .build();

        metricServer.startServer();

        String content = TestUtil.getWebpageContent(TestUtil.HTTP_LOCALHOST + TestUtil.TEST_PORT);

        metricServer.stopServer();
        return content;
    }
}
