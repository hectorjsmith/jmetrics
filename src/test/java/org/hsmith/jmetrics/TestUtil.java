package org.hsmith.jmetrics;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.hotspot.DefaultExports;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.hsmith.jmetrics.config.MetricServerConfig;
import org.hsmith.jmetrics.server.MetricServer;
import org.hsmith.jmetrics.server.impl.MetricServerBuilderImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUtil {
    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final int TEST_PORT = 51735;

    public static void setupLoggerForTests() {
        Properties props = new Properties();
        props.put("log4j.rootLogger", "INFO, stdlog");
        props.put("log4j.logger.com.mchange", "WARN, stdlog");
        props.put("log4j.logger.org.hibernate", "WARN, stdlog");

        props.put("log4j.appender.stdlog", "org.apache.log4j.ConsoleAppender");
        props.put("log4j.appender.stdlog.target", "System.out");
        props.put("log4j.appender.stdlog.layout", "org.apache.log4j.PatternLayout");
        props.put("log4j.appender.stdlog.layout.ConversionPattern", "%d{yyyy-MM-dd HH:mm:ss} %C %-5p %c{1} - %m%n");

        LogManager.resetConfiguration();
        PropertyConfigurator.configure(props);
    }

    public static String getWebpageContent(String targetURL) throws IOException {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

//            connection.setRequestProperty("Content-Length",
//                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
//            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    public static void resetCollectors() throws NoSuchFieldException, IllegalAccessException {
        // Clearing all collectors
        CollectorRegistry.defaultRegistry.clear();

        // Using reflection to set the initialized flag on the DefaultExports back to false
        // Otherwise the default exports never get re-enabled
        Field field = DefaultExports.class.getDeclaredField("initialized");
        field.setAccessible(true);
        field.set(null, false);
    }

    public static MetricServer startMetricsServer(MetricServerConfig config) throws IOException {
        MetricServer metricServer = new MetricServerBuilderImpl(config)
                .build();

        metricServer.startServer();
        return metricServer;
    }

    public static String collectMetricsFromServer() throws IOException {
        return getWebpageContent(TestUtil.HTTP_LOCALHOST + TestUtil.TEST_PORT);
    }

    public static long getMetricValue(String metrics, String regexString) {;
        Pattern p = Pattern.compile(regexString, Pattern.DOTALL);

        Matcher matcher = p.matcher(metrics);
        assertTrue(matcher.matches(), "Metrics data should match regex");

        String requestNumStr = matcher.group(1);
        return Long.parseLong(requestNumStr);
    }
}
