package org.hsmith.jmetrics.collector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JettyStatisticsCollectorTest {

    @Test
    void getCollectorName() {
        Collector collector = new JettyStatisticsCollector(null);
        assertEquals("JettyStatisticsCollector", collector.getCollectorName());
    }
}