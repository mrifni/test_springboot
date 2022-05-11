package com.rezolve.test;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlTest {

    public static void test(String href) {
        HttpURLConnection connection = null;
        try {
            URL u = new URL(href);
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            System.out.println("URL status code - " + code);
            if (code != 200) {
                throw new RuntimeException("Invalid URL " + href);
            }
            // You can determine on HTTP return code received. 200 is success.
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("Invalid URL " + href);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}
