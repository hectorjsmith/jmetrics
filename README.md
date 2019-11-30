# JMetrics

[![pipeline status](https://gitlab.com/java.libraries/jmetrics/badges/master/pipeline.svg)](https://gitlab.com/java.libraries/jmetrics/commits/master)

Java metrics library to facilitate using the prometheus metrics framework.

## Getting Started

### Collectors

Define custom collectors by extending the `BaseCollector` class.
Custom collectors need to override the `buildMetrics` method to build the custom collectors.

```java
public class MyNewCollector extends BaseCollector implements Collector {
    @Override
    protected void buildMetrics(MetricBuilderFactory metricBuilderFactory) {
        addMetric(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric")
                .withMetricHelp("Test metric")
                .withMetricSample(() -> someValueOrFunction)
                .build());
    }

    @Override
    public String getCollectorName() {
        return "MyNewCollector";
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
    .collectJvmMetrics()
    .collectJettyMetrics()
    .withServerHttpPort(9000)
    .withServerIdleTimeout(10)
    .withServerMinThreads(1)
    .withServerMaxThreads(10)
    .build();
```

### Start server

Then use the config object to build the server object. Also register any other custom collectors you have
defined when building the server. It is not possible to register them after the server is built.

```java
MetricServer server = new MetricServerBuilderImpl(metricConfig)
    .withCollector(new MyNewCollector())
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
test_metric 113.7
```
