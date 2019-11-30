package org.hsmith.jmetrics.config.impl;

// CHECKSTYLE-OFF: InterfaceIsType - This interface is used for constants

interface MetricServerConfigDefaults {
    int SERVER_PORT = 9909;
    int SERVER_MAX_THREADS = 60;
    int SERVER_MIN_THREADS = 5;
    int SERVER_IDLE_TIMEOUT = 60_000;
    boolean COLLECT_JVM_METRICS = true;
    boolean COLLECT_JETTY_METRICS = false;
}

// CHECKSTYLE-ON: InterfaceIsType
