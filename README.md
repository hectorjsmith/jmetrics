# JMetrics

Java metrics library to facilitate using the prometheus metrics framework.

## Getting Started

### Collectors

Define custom collectors by extending the `BaseCollector` class.
Custom collectors need to override the `buildMetrics` method to build the custom collectors.

```java
public class SampleCollector extends BaseCollector implements Collector {
    @Override
    protected void buildMetrics(MetricBuilderFactory metricBuilderFactory) {
        addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric")
                .withMetricHelp("Test metric")
                .withMetricSample(() -> /* TODO */)
                .build());
    }

    @Override
    public String getCollectorName() {
        return "SampleCollector";
    }
}
```

The function given in the `withMetricSample` method will be called every time metrics are collected to generate the value for that collector.
It is possible to add multiple metrics for a single collector.

The `JettyStatisticsCollector` is a good example on how to build a collector with multiple metrics.

### Config

Build the configuration object;

```java
MetricServerConfig metricConfig = new MetricServerConfigBuilderImpl()
    .collectQueuedThreadPoolMetrics()
    .collectJettyMetrics()
    .collectJvmMetrics()
    .withServerHttpPort(9000)
    .withServerIdleTimeout(10)
    .withServerMinThreads(1)
    .withServerMaxThreads(10)
    .build();
```

### Start server

Then use the config object to build the server object.

```java
MetricServer server = new MetricServerBuilderImpl(metricConfig)
    .withCollector()
    .build();
```

Finally, start the server.

```java
server.startServer();
```

Once the server is running the collected metrics can be seen by navigating to the port defined in the server config.

```
# HELP test_metric Test metric
# TYPE test_metric counter
test_metric 0.0
```