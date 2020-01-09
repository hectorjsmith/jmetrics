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

    @ParameterizedTest
    @MethodSource("providerForConfigOptions")
    void testGivenMetricConfigBuilderWhenSetPortThenCorrectValuesSetInConfig(
            int testPort,
            boolean testJvmMetrics) {

        MetricServerConfig config = new MetricServerConfigBuilderImpl()
                .withServerHttpPort(testPort)
                .collectJvmMetrics(testJvmMetrics)
                .build();

        assertEquals("Port in config should match value set in builder",
                testPort, config.getServerHttpPort());
        assertEquals("Collect JVM metrics option in config should match value set in builder",
                testJvmMetrics, config.collectJvmMetrics());
    }

    private static Stream<Arguments> providerForConfigOptions() {
        return Stream.of(
                Arguments.of(1199, true),
                Arguments.of(9999, false)
        );
    }
}
