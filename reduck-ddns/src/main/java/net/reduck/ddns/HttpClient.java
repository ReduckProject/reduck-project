package net.reduck.ddns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Reduck
 * @since 2022/8/15 11:21
 */
@Slf4j
public class HttpClient {

    public String get(String url) {
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            return new String(FileCopyUtils.copyToByteArray(http.getInputStream()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }
}
