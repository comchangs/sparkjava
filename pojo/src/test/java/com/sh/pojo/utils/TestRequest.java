package com.sh.pojo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;

public class TestRequest {
    private static Logger log = LoggerFactory.getLogger(TestRequest.class);

    public TestRequest() {
    }

    public static URL sendUrl(String  uri, String port, String path) {
        StringBuilder sb = new StringBuilder();
        URL url = null;
        try {
            url = new URL(sb.append(uri).append(':').append(port).append(path).toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            fail("failed URL: " + e.getMessage());
        }
        return url;
    }


    public static TestResponse request(String method,URL url, String data) {
        HttpURLConnection connection = null;
        try {
            log.info("url : {}",url);
            log.info("request body : {}",data);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);

            if(!Objects.isNull(data)){
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream out = connection.getOutputStream();
                out.write(data.getBytes(StandardCharsets.UTF_8));
            }

            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            connection.disconnect();
            return new TestResponse(body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }

    }

}
