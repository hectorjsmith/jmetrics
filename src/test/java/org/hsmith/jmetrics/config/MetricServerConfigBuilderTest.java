package org.hsmith.jmetrics.config;

import org.hsmith.jmetrics.TestUtil;
import org.hsmith.jmetrics.config.impl.MetricServerConfigBuilderImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MetricServerConfigBuilderTest {
    @BeforeAll
    static void loggerSetup() {
        TestUtil.setupLoggerForTests();
    }

    @Test
    void testGivenMetricConfigBuilderWhenSetInvalidPortThenExceptionThrown() {
        MetricServerConfigBuilder builder = new MetricServerConfigBuilderImpl();

        assertThrows(IllegalArgumentException.class, () -> builder.withServerHttpPort(-200),
                "Expected exception when setting negative port");

        assertThrows(IllegalArgumentException.class, () -> builder.withServerHttpPort(999_999),
                "Expected exception when setting port beyond maximum");
    }

    @Test
    void testGivenMetricConfigBuilderWhenSetInvalidThreadsThenExceptionThrown() {
        MetricServerConfigBuilder builder = new MetricServerConfigBuilderImpl();

        assertThrows(IllegalArgumentException.class, () -> builder.withServerMinThreads(0),
                "Expected exception when setting 0 min threads");
        assertThrows(IllegalArgumentException.class, () -> builder.withServerMaxThreads(0),
                "Expected exception when setting 0 max threads");

        assertThrows(IllegalArgumentException.class, () -> builder.withServerMinThreads(-1),
                "Expected exception when setting negative min threads");
        assertThrows(IllegalArgumentException.class, () -> builder.withServerMaxThreads(-1),
                "Expected exception when setting negative max threads");
    }

    @Test
    void testGivenMetricConfigBuilderWhenSetMoreMinThreadsThanMaxThenExceptionThrown() {
        MetricServerConfigBuilder builder = new MetricServerConfigBuilderImpl();

        builder.withServerMinThreads(100);
        builder.withServerMaxThreads(10);

        assertThrows(IllegalArgumentException.class, () -> builder.build(),
                "Expected exception when setting more min threads than max");
    }

    @ParameterizedTest
    @MethodSource("providerForConfigOptions")
    void testGivenMetricConfigBuilderWhenSetPortThenCorrectValuesSetInConfig(
            int testPort,
            int testTimeout,
            int testMinThreads,
            int testMaxThreads,
            boolean testJvmMetrics,
            boolean testJettyMetrics) {

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(testPort)
                .withServerIdleTimeout(testTimeout)
                .withServerMinThreads(testMinThreads)
                .withServerMaxThreads(testMaxThreads)
                .collectJvmMetrics(testJvmMetrics)
                .collectJettyMetrics(testJettyMetrics)
                .build();

        assertEquals("Port in config should match value set in builder",
                testPort, config.getServerHttpPort());
        assertEquals("Server timeout in config should match value set in builder",
                testTimeout, config.getServerIdleTimout());
        assertEquals("Min threads in config should match value set in builder",
                testMinThreads, config.getServerMinThreads());
        assertEquals("Max threads in config should match value set in builder",
                testMaxThreads, config.getServerMaxThreads());
        assertEquals("Collect JVM metrics option in config should match value set in builder",
                testJvmMetrics, config.collectJvmMetrics());
        assertEquals("Collect Jetty metrics option in config should match value set in builder",
                testJettyMetrics, config.collectJettyMetrics());
    }

    private static Stream<Arguments> providerForConfigOptions() {
        return Stream.of(
                Arguments.of(1199, 1199, 10, 20, true, true),
                Arguments.of(9999, 5000, 3, 4, false, false),
                Arguments.of(1111, 8000, 33, 44, false, true),
                Arguments.of(2222, 4444, 22, 55, true, false),
                Arguments.of(3333, 3333, 33, 55, false, false),
                Arguments.of(4444, 2222, 11, 77, false, true),
                Arguments.of(12000, 111, 1, 1000, true, false)
        );
    }
}
