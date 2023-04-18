package net.reduck.jpa.test.controller;

import lombok.extern.slf4j.Slf4j;
import net.reduck.jpa.test.controller.pojo.CasTokenResponse;
import net.reduck.jpa.test.controller.pojo.CasUserInfoResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Gin
 * @since 2023/2/10 11:17
 */
@RestController
@RequestMapping(value = "/cas")
@Slf4j
public class CasController {

    CasProperties casProperties = new CasProperties();

    RestTemplate restTemplate = buildTrustAllRestTemplate();
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect(
                casProperties.getRemoteUrl() + "/authorize" + "?response_type=code"
                        + "&client_id=" + URLEncoder.encode("ai-13d6bc4b75154841a5ba3a65fea6b214")
                        + "&redirect_uri=" + URLEncoder.encode("https://gintest.com/cas/callback")
                        + "&scope=" + URLEncoder.encode("openid offline_access")
//                        + "&state=" + UUID.randomUUID().toString()
        );
    }


    @RequestMapping(value = "/callback")
    public String callback(@RequestParam(required = false) String code) {
        String token = obtainTokenByCode(code);

        CasUserInfoResponse response = obtainUserInfoByToken(token);
        return response.toString();
    }

    private String obtainTokenByCode(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 按照`Basic Base64(client_id:client_secret)` 方式拼接
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                (casProperties.getClientId() + ":" + casProperties.getClientSecret()).getBytes(StandardCharsets.UTF_8))
        );

        CasTokenResponse response = null;

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.put("grant_type", Collections.singletonList("authorization_code"));
        data.put("code", Collections.singletonList(code));
        data.put("redirect_uri", Collections.singletonList(casProperties.getCallbackUrl()));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(data, headers);

        try {
            response = restTemplate.postForEntity(casProperties.getRemoteUrl() + "/token", requestEntity, CasTokenResponse.class).getBody();
            if (response != null && StringUtils.hasText(response.getAccessToken())) {
                return response.getAccessToken();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        throw new RuntimeException("Cas认证失败, code :" + code);
    }

    @SuppressWarnings("DuplicatedCode")
    private RestTemplate buildTrustAllRestTemplate() {
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null,
                            new TrustAllStrategy())
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new RuntimeException(e);
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                (s, sslSession) -> true);

        HttpClient httpClient = HttpClients
                .custom()
                .setSSLSocketFactory(sslsf)
                .build();

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        return restTemplate;
    }

    private CasUserInfoResponse obtainUserInfoByToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 按照`Bearer token` 方式拼接
        headers.set("Authorization", "Bearer " + token);

        try {
            CasUserInfoResponse response = restTemplate.exchange(casProperties.getRemoteUrl() + "/userinfo", HttpMethod.GET, new HttpEntity<>(null, headers), CasUserInfoResponse.class).getBody();

            if (response != null && response.getSub() != null) {
                return response;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        throw new RuntimeException("Cas认证失败, token :" + token);
    }
}
