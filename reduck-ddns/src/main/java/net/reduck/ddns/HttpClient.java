package net.reduck.ddns;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Gin
 * @since 2022/8/15 11:21
 */
public class HttpClient {

    public String get(String url) {
        HttpURLConnection http = null;
        InputStream is = null;
        try {
            URL urlGet = new URL(url);
            http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            return new String(jsonBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
