package com.example.resttemplate.config;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(clientHttpRequestFactory());
    }

    @Bean
    public HttpClientConnectionManager poolingConnectionManager() {
        // 连接池
        PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        poolingConnectionManager.setMaxTotal(1000);
        // 每个主机的并发
        poolingConnectionManager.setDefaultMaxPerRoute(100);
        // 空闲连接过期时间
        poolingConnectionManager.setValidateAfterInactivity(10_000);
        return poolingConnectionManager;
    }


    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(poolingConnectionManager());
        clientHttpRequestFactory.setHttpClient(httpClientBuilder.build());
        //连接请求超时时间
        clientHttpRequestFactory.setConnectionRequestTimeout(3000);
        // 客户端和服务器建立连接的超时时间
        clientHttpRequestFactory.setConnectTimeout(3000);
        // 客户端从服务器读取数据的超时时间
        clientHttpRequestFactory.setReadTimeout(12000);
        return clientHttpRequestFactory;
    }

}
