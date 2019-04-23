package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;


public final class JettyStatisticsCollector extends BaseCollector {
    private final double timeToSecondsMultiplier = 1000.0;
    private final StatisticsHandler jettyStatistics;

    public JettyStatisticsCollector(final StatisticsHandler jettyStatistics) {
        this.jettyStatistics = jettyStatistics;
    }

    @Override
    public void initialize(final MetricBuilderFactory metricBuilderFactory) {
        buildMetrics(metricBuilderFactory);
        super.register();
    }

    @Override
    public String getCollectorName() {
        return this.getClass().getName();
    }

    private void buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_requests_total")
                .withMetricHelp("Number of requests")
                .withMetricSample(jettyStatistics::getRequests)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active")
                .withMetricHelp("Number of requests currently active")
                .withMetricSample(jettyStatistics::getRequestsActive)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active_max")
                .withMetricHelp("Maximum number of requests that have been active at once")
                .withMetricSample(jettyStatistics::getRequestsActiveMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_request_time_max_seconds")
                .withMetricHelp("Maximum time spent handling requests")
                .withMetricSample(() -> jettyStatistics.getRequestTimeMax() / timeToSecondsMultiplier)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_request_time_seconds_total")
                .withMetricHelp("Total time spent in all request handling")
                .withMetricSample(() -> jettyStatistics.getRequestTimeTotal() / timeToSecondsMultiplier)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_total")
                .withMetricHelp("Number of dispatches")
                .withMetricSample(jettyStatistics::getDispatched)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active")
                .withMetricHelp("Number of dispatches currently active")
                .withMetricSample(jettyStatistics::getDispatchedActive)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active_max")
                .withMetricHelp("Maximum number of active dispatches being handled")
                .withMetricSample(jettyStatistics::getDispatchedActiveMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_time_max")
                .withMetricHelp("Maximum time spent in dispatch handling")
                .withMetricSample(jettyStatistics::getDispatchedTimeMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_time_seconds_total")
                .withMetricHelp("Total time spent in dispatch handling")
                .withMetricSample(() -> jettyStatistics.getDispatchedTimeTotal() / timeToSecondsMultiplier)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_requests_total")
                .withMetricHelp("Total number of async requests")
                .withMetricSample(jettyStatistics::getAsyncRequests)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting")
                .withMetricHelp("Currently waiting async requests")
                .withMetricSample(jettyStatistics::getAsyncRequestsWaiting)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting_max")
                .withMetricHelp("Maximum number of waiting async requests")
                .withMetricSample(jettyStatistics::getAsyncRequestsWaitingMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_dispatches_total")
                .withMetricHelp("Number of requested that have been asynchronously dispatched")
                .withMetricSample(jettyStatistics::getAsyncDispatches)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_expires_total")
                .withMetricHelp("Number of async requests requests that have expired")
                .withMetricSample(jettyStatistics::getExpires)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_stats_seconds")
                .withMetricHelp("Time in seconds stats have been collected for")
                .withMetricSample(() -> jettyStatistics.getStatsOnMs() / timeToSecondsMultiplier)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_bytes_total")
                .withMetricHelp("Total number of bytes across all responses")
                .withMetricSample(jettyStatistics::getResponsesBytesTotal)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_total")
                .withMetricHelp("Number of requests with response status")
                .withMetricSample("code", "1xx", jettyStatistics::getResponses1xx)
                .withMetricSample("code", "2xx", jettyStatistics::getResponses2xx)
                .withMetricSample("code", "3xx", jettyStatistics::getResponses3xx)
                .withMetricSample("code", "4xx", jettyStatistics::getResponses4xx)
                .withMetricSample("code", "5xx", jettyStatistics::getResponses5xx)
                .build());
    }
}
