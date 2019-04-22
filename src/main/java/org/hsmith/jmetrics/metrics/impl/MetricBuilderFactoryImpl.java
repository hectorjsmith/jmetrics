package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.metrics.MetricBuilder;
import org.hsmith.jmetrics.metrics.MetricBuilderFactory;

public final class MetricBuilderFactoryImpl implements MetricBuilderFactory {
    @Override
    public MetricBuilder newInstance() {
        return new MetricBuilderImpl();
    }
}
