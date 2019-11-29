package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;


public final class JettyStatisticsCollector extends BaseCollector {
    private final double timeToSecondsMultiplier = 1000.0;
    private final StatisticsHandler jettyStatistics;
    private final QueuedThreadPool queuedThreadPool;

    public JettyStatisticsCollector(final StatisticsHandler jettyStatistics,
                                    final QueuedThreadPool queuedThreadPool) {
        this.jettyStatistics = jettyStatistics;
        this.queuedThreadPool = queuedThreadPool;
    }

    @Override
    public String getCollectorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_requests_total")
                .withMetricHelp("Number of requests")
                .withMetricSample(jettyStatistics::getRequests)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active")
                .withMetricHelp("Number of requests currently active")
                .withMetricSample(jettyStatistics::getRequestsActive)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_requests_active_max")
                .withMetricHelp("Maximum number of requests that have been active at once")
                .withMetricSample(jettyStatistics::getRequestsActiveMax)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_request_time_max_seconds")
                .withMetricHelp("Maximum time spent handling requests")
                .withMetricSample(() -> jettyStatistics.getRequestTimeMax() / timeToSecondsMultiplier)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_request_time_seconds_total")
                .withMetricHelp("Total time spent in all request handling")
                .withMetricSample(() -> jettyStatistics.getRequestTimeTotal() / timeToSecondsMultiplier)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_total")
                .withMetricHelp("Number of dispatches")
                .withMetricSample(jettyStatistics::getDispatched)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active")
                .withMetricHelp("Number of dispatches currently active")
                .withMetricSample(jettyStatistics::getDispatchedActive)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_active_max")
                .withMetricHelp("Maximum number of active dispatches being handled")
                .withMetricSample(jettyStatistics::getDispatchedActiveMax)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_dispatched_time_max")
                .withMetricHelp("Maximum time spent in dispatch handling")
                .withMetricSample(jettyStatistics::getDispatchedTimeMax)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_dispatched_time_seconds_total")
                .withMetricHelp("Total time spent in dispatch handling")
                .withMetricSample(() -> jettyStatistics.getDispatchedTimeTotal() / timeToSecondsMultiplier)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_requests_total")
                .withMetricHelp("Total number of async requests")
                .withMetricSample(jettyStatistics::getAsyncRequests)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting")
                .withMetricHelp("Currently waiting async requests")
                .withMetricSample(jettyStatistics::getAsyncRequestsWaiting)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_async_requests_waiting_max")
                .withMetricHelp("Maximum number of waiting async requests")
                .withMetricSample(jettyStatistics::getAsyncRequestsWaitingMax)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_async_dispatches_total")
                .withMetricHelp("Number of requested that have been asynchronously dispatched")
                .withMetricSample(jettyStatistics::getAsyncDispatches)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_expires_total")
                .withMetricHelp("Number of async requests requests that have expired")
                .withMetricSample(jettyStatistics::getExpires)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_stats_seconds")
                .withMetricHelp("Time in seconds stats have been collected for")
                .withMetricSample(() -> jettyStatistics.getStatsOnMs() / timeToSecondsMultiplier)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_bytes_total")
                .withMetricHelp("Total number of bytes across all responses")
                .withMetricSample(jettyStatistics::getResponsesBytesTotal)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("jetty_responses_total")
                .withMetricHelp("Number of requests with response status")
                .withMetricSample("code", "1xx", jettyStatistics::getResponses1xx)
                .withMetricSample("code", "2xx", jettyStatistics::getResponses2xx)
                .withMetricSample("code", "3xx", jettyStatistics::getResponses3xx)
                .withMetricSample("code", "4xx", jettyStatistics::getResponses4xx)
                .withMetricSample("code", "5xx", jettyStatistics::getResponses5xx)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_threads")
                .withMetricHelp("Number of total threads")
                .withMetricSample(queuedThreadPool::getThreads)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_utilization")
                .withMetricHelp("Percentage of threads in use")
                .withMetricSample(() -> (double) queuedThreadPool.getThreads() / queuedThreadPool.getMaxThreads())
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_threads_idle")
                .withMetricHelp("Number of idle threads")
                .withMetricSample(queuedThreadPool::getIdleThreads)
                .build());
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_jobs")
                .withMetricHelp("Number of total jobs")
                .withMetricSample(queuedThreadPool::getQueueSize)
                .build());
        return metrics;
    }
}
