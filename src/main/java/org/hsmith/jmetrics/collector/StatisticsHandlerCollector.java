package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;


public final class StatisticsHandlerCollector extends BaseCollector {
    private final StatisticsHandler statisticsHandler;
    private final MetricBuilderFactory metricBuilderFactory;

    public StatisticsHandlerCollector(final StatisticsHandler statisticsHandler,
                                      final MetricBuilderFactory metricBuilderFactory) {
        this.statisticsHandler = statisticsHandler;
        this.metricBuilderFactory = metricBuilderFactory;
        buildMetrics();
    }

    private void buildMetrics() {
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_requests_total")
                .withMetricHelp("Number of requests")
                .withMetricSample(statisticsHandler::getRequests)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active")
                .withMetricHelp("Number of requests currently active")
                .withMetricSample(statisticsHandler::getRequestsActive)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active_max")
                .withMetricHelp("Maximum number of requests that have been active at once")
                .withMetricSample(statisticsHandler::getRequestsActiveMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_request_time_max_seconds")
                .withMetricHelp("Maximum time spent handling requests")
                .withMetricSample(() -> statisticsHandler.getRequestTimeMax() / 1000.0)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_request_time_seconds_total")
                .withMetricHelp("Total time spent in all request handling")
                .withMetricSample(() -> statisticsHandler.getRequestTimeTotal() / 1000.0)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_total")
                .withMetricHelp("Number of dispatches")
                .withMetricSample(statisticsHandler::getDispatched)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active")
                .withMetricHelp("Number of dispatches currently active")
                .withMetricSample(statisticsHandler::getDispatchedActive)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active_max")
                .withMetricHelp("Maximum number of active dispatches being handled")
                .withMetricSample(statisticsHandler::getDispatchedActiveMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_time_max")
                .withMetricHelp("Maximum time spent in dispatch handling")
                .withMetricSample(statisticsHandler::getDispatchedTimeMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_time_seconds_total")
                .withMetricHelp("Total time spent in dispatch handling")
                .withMetricSample(() -> statisticsHandler.getDispatchedTimeTotal() / 1000.0)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_requests_total")
                .withMetricHelp("Total number of async requests")
                .withMetricSample(statisticsHandler::getAsyncRequests)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting")
                .withMetricHelp("Currently waiting async requests")
                .withMetricSample(statisticsHandler::getAsyncRequestsWaiting)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting_max")
                .withMetricHelp("Maximum number of waiting async requests")
                .withMetricSample(statisticsHandler::getAsyncRequestsWaitingMax)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_dispatches_total")
                .withMetricHelp("Number of requested that have been asynchronously dispatched")
                .withMetricSample(statisticsHandler::getAsyncDispatches)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_expires_total")
                .withMetricHelp("Number of async requests requests that have expired")
                .withMetricSample(statisticsHandler::getExpires)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_stats_seconds")
                .withMetricHelp("Time in seconds stats have been collected for")
                .withMetricSample(() -> statisticsHandler.getStatsOnMs() / 1000.0)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_bytes_total")
                .withMetricHelp("Total number of bytes across all responses")
                .withMetricSample(statisticsHandler::getResponsesBytesTotal)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_total")
                .withMetricHelp("Number of requests with response status")
                .withMetricSample("code", "1xx", statisticsHandler::getResponses1xx)
                .withMetricSample("code", "2xx", statisticsHandler::getResponses2xx)
                .withMetricSample("code", "3xx", statisticsHandler::getResponses3xx)
                .withMetricSample("code", "4xx", statisticsHandler::getResponses4xx)
                .withMetricSample("code", "5xx", statisticsHandler::getResponses5xx)
                .build());
    }
}
