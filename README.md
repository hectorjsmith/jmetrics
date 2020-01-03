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

The `IdleTimeout`, `MinThreads`, and `MaxThread` values are only used if Jetty metrics are being collected and no custom Jetty server was provided.
When the metrics server creates a Jetty server to collect metrics from it will use these parameters.
If a custom jetty server is provided these options can be ignored.

### Jetty

It is possible to collect metrics from a Jetty server. This will expose metrics such as total request count, amount of time processing
requests, bytes sent in responses, etc.

There are two ways to enable collecting metrics from a Jetty server:

1. `metricConfig.collectJettyMetrics()`
2. `metricConfig.collectJettyMetrics(jettyServer)`

When option 1 is used, a new Jetty server is created by the metrics server and can be retrieved by calling `metricServer.getJettyServer()`.
Option 2 will take a custom jetty server and collect metrics from it. It is up to the client code to decide which option makes more sense.

*Note:* Calls to the metrics endpoint to collect metrics do not count towards the Jetty metrics.

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
