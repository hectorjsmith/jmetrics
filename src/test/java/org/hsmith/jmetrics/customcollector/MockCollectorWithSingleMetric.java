package org.hsmith.jmetrics.customcollector;

import org.hsmith.jmetrics.collector.BaseCollector;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public class MockCollectorWithSingleMetric extends BaseCollector {
    private final String sampleName;
    private final DoubleSupplier sampleValueSupplier;

    MockCollectorWithSingleMetric(String sampleName, DoubleSupplier sampleValueSupplier) {
        this.sampleName = sampleName;
        this.sampleValueSupplier = sampleValueSupplier;
    }

    @Override
    public String getCollectorName() {
        return getClass().getSimpleName();
    }

    @Override
    protected List<Metric> buildMetrics(MetricBuilderFactory metricBuilderFactory) {
        List<Metric> results = new ArrayList<>();
        results.add(metricBuilderFactory
                .newInstance()
                .withMetricName(sampleName)
                .withMetricType(MetricType.COUNTER)
                .withMetricSample(sampleValueSupplier)
                .build()
        );
        return results;
    }
}
