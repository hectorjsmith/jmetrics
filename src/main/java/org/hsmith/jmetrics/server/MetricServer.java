package org.hsmith.jmetrics.server;

import java.io.IOException;

/**
 * Metric server. Once started will expose metrics collected from all registered metrics at the port defined
 * in the configuration object used to build it.
 * @see org.hsmith.jmetrics.server.MetricServerBuilder
 */
public interface MetricServer {
    /**
     * Start the server.
     */
    void startServer() throws IOException;

    /**
     * Shutdown the server.
     */
    void stopServer();
}
