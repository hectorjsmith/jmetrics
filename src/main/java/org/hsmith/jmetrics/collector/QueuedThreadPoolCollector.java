package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.util.thread.QueuedThreadPool;


final class QueuedThreadPoolCollector extends BaseCollector {
    private final QueuedThreadPool queuedThreadPool;

    QueuedThreadPoolCollector(final QueuedThreadPool queuedThreadPool) {
        this.queuedThreadPool = queuedThreadPool;

        // super.addMetric(null);
    }


//    @Override
//    public List<Metric<?>> collect() {
//        return Arrays.asList(
////                MetricsUtil.buildGauge("jetty_queued_thread_pool_threads", "Number of total threads", queuedThreadPool.getThreads()),
////                MetricsUtil.buildGauge("jetty_queued_thread_pool_utilization", "Percentage of threads in use", (double)queuedThreadPool.getThreads() / queuedThreadPool.getMaxThreads()),
////                MetricsUtil.buildGauge("jetty_queued_thread_pool_threads_idle", "Number of idle threads", queuedThreadPool.getIdleThreads()),
////                MetricsUtil.buildGauge("jetty_queued_thread_pool_jobs", "Number of total jobs", queuedThreadPool.getQueueSize())
//        );
//    }
}
