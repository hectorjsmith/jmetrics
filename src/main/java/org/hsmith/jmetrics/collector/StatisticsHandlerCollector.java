package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.server.handler.StatisticsHandler;


final class StatisticsHandlerCollector extends BaseCollector {
    private final StatisticsHandler statisticsHandler;

    private StatisticsHandlerCollector(final StatisticsHandler statisticsHandler) {
        this.statisticsHandler = statisticsHandler;

        // super.addMetric(null);
    }



//    @Override
//    public void initialize() {
//        this.register();
//    }

//    @Override
//    public List<MetricFamilySamples> collect() {
//        return Arrays.asList(
////                MetricsUtil.buildCounter("jetty_requests_total", "Number of requests", statisticsHandler.getRequests()),
////                MetricsUtil.buildGauge("jetty_requests_active", "Number of requests currently active", statisticsHandler.getRequestsActive()),
////                MetricsUtil.buildGauge("jetty_requests_active_max", "Maximum number of requests that have been active at once", statisticsHandler.getRequestsActiveMax()),
////                MetricsUtil.buildGauge("jetty_request_time_max_seconds", "Maximum time spent handling requests", statisticsHandler.getRequestTimeMax() / 1000.0),
////                MetricsUtil.buildCounter("jetty_request_time_seconds_total", "Total time spent in all request handling", statisticsHandler.getRequestTimeTotal() / 1000.0),
////                MetricsUtil.buildCounter("jetty_dispatched_total", "Number of dispatches", statisticsHandler.getDispatched()),
////                MetricsUtil.buildGauge("jetty_dispatched_active", "Number of dispatches currently active", statisticsHandler.getDispatchedActive()),
////                MetricsUtil.buildGauge("jetty_dispatched_active_max", "Maximum number of active dispatches being handled", statisticsHandler.getDispatchedActiveMax()),
////                MetricsUtil.buildGauge("jetty_dispatched_time_max", "Maximum time spent in dispatch handling", statisticsHandler.getDispatchedTimeMax()),
////                MetricsUtil.buildCounter("jetty_dispatched_time_seconds_total", "Total time spent in dispatch handling", statisticsHandler.getDispatchedTimeTotal() / 1000.0),
////                MetricsUtil.buildCounter("jetty_async_requests_total", "Total number of async requests", statisticsHandler.getAsyncRequests()),
////                MetricsUtil.buildGauge("jetty_async_requests_waiting", "Currently waiting async requests", statisticsHandler.getAsyncRequestsWaiting()),
////                MetricsUtil.buildGauge("jetty_async_requests_waiting_max", "Maximum number of waiting async requests", statisticsHandler.getAsyncRequestsWaitingMax()),
////                MetricsUtil.buildCounter("jetty_async_dispatches_total", "Number of requested that have been asynchronously dispatched", statisticsHandler.getAsyncDispatches()),
////                MetricsUtil.buildCounter("jetty_expires_total", "Number of async requests requests that have expired", statisticsHandler.getExpires()),
////                buildStatusCounter(),
////                MetricsUtil.buildGauge("jetty_stats_seconds", "Time in seconds stats have been collected for", statisticsHandler.getStatsOnMs() / 1000.0),
////                MetricsUtil.buildCounter("jetty_responses_bytes_total", "Total number of bytes across all responses", statisticsHandler.getResponsesBytesTotal())
//        );
//    }


//    private MetricFamilySamples buildStatusCounter() {
//        String name = "jetty_responses_total";
//        return new MetricFamilySamples(
//                name,
//                Type.COUNTER,
//                "Number of requests with response status",
//                Arrays.asList(
//                        buildStatusSample(name, "1xx", statisticsHandler.getResponses1xx()),
//                        buildStatusSample(name, "2xx", statisticsHandler.getResponses2xx()),
//                        buildStatusSample(name, "3xx", statisticsHandler.getResponses3xx()),
//                        buildStatusSample(name, "4xx", statisticsHandler.getResponses4xx()),
//                        buildStatusSample(name, "5xx", statisticsHandler.getResponses5xx())
//                )
//        );
//    }
//
//    private static MetricFamilySamples.Sample buildStatusSample(String name, String status, double value) {
//        return new MetricFamilySamples.Sample(
//                name,
//                Collections.singletonList("code"),
//                Collections.singletonList(status),
//                value
//        );
//    }

}
