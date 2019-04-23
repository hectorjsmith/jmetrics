package org.hsmith.jmetrics.collector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueuedThreadPoolCollectorTest {

    @Test
    void getCollectorName() {
        Collector collector = new QueuedThreadPoolCollector(null);
        assertEquals("QueuedThreadPoolCollector", collector.getCollectorName());
    }
}