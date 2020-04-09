# JMetrics

[![pipeline status](https://gitlab.com/hectorjsmith/jmetrics/badges/master/pipeline.svg)](https://gitlab.com/hectorjsmith/jmetrics/-/commits/master)

Java metrics library to facilitate using the prometheus metrics framework.

## Getting Started

### Collectors

Define custom collectors by extending the `BaseCollector` class.
Custom collectors need to override the `buildMetrics` method to build the custom collectors.

```java
public class MyNewCollector extends BaseCollector implements Collector {
    @Override
    protected List<Metric> buildMetrics(final MetricBuilderFactory metricBuilderFactory) {
        List<Metric> metrics = new ArrayList<>();
        metrics.add(metricBuilderFactory.newInstance()
                .withMetricType(MetricType.COUNTER)
                .withMetricName("test_metric")
                .withMetricHelp("Test metric")
                .withMetricSample(() -> someValueOrFunction)
                .build());
        return metrics;
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
    .withServerHttpPort(9000)
    .build();
```

Calling `collectJvmMetrics` will enable collection of standard JVM metrics. These metrics include memory usage, CPU usage, and number of threads. 

### Jetty

It is possible to collect metrics from a Jetty server. This will expose metrics such as total request count, amount of time processing
requests, bytes sent in responses, etc.

To collect metrics from a Jetty server you will need to provide the server to the metrics server when it is configured.

```java
metricConfigBuilder.collectJettyMetrics(jettyServer)
```

*Note:* Calls to the metrics endpoint to collect metrics do not count towards the Jetty metrics because the metrics server uses a different server.

### Hibernate

It is possible to collect hibernate metrics by providing the hibernate session factory to the metric config builder.


```java
metricConfigBuilder.collectHibernateMetrics(sessionFactory)
```

Ensure that statistic collection is enabled on the hibernate side to ensure metrics are collected.
You can enable statistics on hibernate either by setting the `generate_statistics` property to true, or by calling the following method:

```java
sessionFactory.getStatistics().setStatisticsEnabled(true);
```

### Start server

Then use the config object to build the server object. Also make sure to register any other custom collectors you have
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
