package org.hsmith.jmetrics.collector;

import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;


public final class QueuedThreadPoolCollector extends BaseCollector {
    private final QueuedThreadPool queuedThreadPool;

    public QueuedThreadPoolCollector(final QueuedThreadPool queuedThreadPool) {
        this.queuedThreadPool = queuedThreadPool;
    }

    @Override
    public void initialize(final MetricBuilderFactory metricBuilderFactory) {
        buildMetrics(metricBuilderFactory);
        super.register();
    }

    @Override
    public String getCollectorName() {
        return this.getClass().getSimpleName();
    }

    private void buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_threads")
                .withMetricHelp("Number of total threads")
                .withMetricSample(queuedThreadPool::getThreads)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_utilization")
                .withMetricHelp("Percentage of threads in use")
                .withMetricSample(() -> queuedThreadPool.getThreads() / queuedThreadPool.getMaxThreads())
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_threads_idle")
                .withMetricHelp("Number of idle threads")
                .withMetricSample(queuedThreadPool::getIdleThreads)
                .build());
        super.addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.GAUGE)
                .withMetricName("jetty_queued_thread_pool_jobs")
                .withMetricHelp("Number of total jobs")
                .withMetricSample(queuedThreadPool::getQueueSize)
                .build());
    }
}
