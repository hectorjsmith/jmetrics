package org.hsmith.jmetrics.metrics.impl;

import org.hsmith.jmetrics.general.impl.BuilderBase;
import org.hsmith.jmetrics.metrics.Metric;
import org.hsmith.jmetrics.metrics.MetricBuilder;
import org.hsmith.jmetrics.metrics.MetricSample;
import org.hsmith.jmetrics.metrics.MetricType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;


// CHECKSTYLE-OFF: HiddenField - It makes sense to use the same variable name in builder methods

final class MetricBuilderImpl extends BuilderBase implements MetricBuilder {
    private MetricType metricType;
    private String metricName;
    private String metricHelp;
    private List<MetricSample> metricSampleList;

    MetricBuilderImpl() {
        this.metricType = MetricType.COUNTER;
        this.metricName = "";
        this.metricHelp = "";
        this.metricSampleList = new ArrayList<>();
    }

    @Override
    public MetricBuilder withMetricType(final MetricType metricType) {
        assertNotNull(metricType, "metricType");
        this.metricType = metricType;
        return this;
    }

    @Override
    public MetricBuilder withMetricName(final String metricName) {
        this.metricName = (metricName == null ? "" : metricName);
        return this;
    }

    @Override
    public MetricBuilder withMetricHelp(final String metricHelp) {
        this.metricHelp = (metricHelp == null ? "" : metricHelp);
        return this;
    }

    @Override
    public MetricBuilder withMetricSample(final DoubleSupplier sampleValueFunction) {
        this.withMetricSample("", "", sampleValueFunction);
        return this;
    }

    @Override
    public MetricBuilder withMetricSample(final String sampleLabelName,
                                          final String sampleLabelValue,
                                          final DoubleSupplier sampleValueFunction) {

        assertNotNull(sampleValueFunction, "sampleValueFunction");
        this.metricSampleList.add(
                new MetricSampleImpl(
                        (sampleLabelName == null ? "" : sampleLabelName),
                        (sampleLabelValue == null ? "" : sampleLabelValue),
                        sampleValueFunction
                )
        );
        return this;
    }

    @Override
    public Metric build() {
        return new MetricImpl(
                this.metricType,
                this.metricName,
                this.metricHelp,
                this.metricSampleList
        );
    }
}

// CHECKSTYLE-ON: HiddenField
