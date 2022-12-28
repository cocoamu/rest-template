package com.example.resttemplate;

import com.example.resttemplate.entity.InnerRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class RestTemplateApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    private String urlFormat = "https://api.juejin.cn/growth_api/v1/get_coder_calendar?aid=%s&uuid=7037140143551399436";

    @Test
    void testGet() {
        // 使用方法一，不带参数
        String url = "https://story.hhui.top/detail?id=666106231640";
        InnerRes res = restTemplate.getForObject(url, InnerRes.class);
        System.out.println(res.toString());


        // 使用方法一，传参替换
        url = "https://story.hhui.top/detail?id={?}";
        res = restTemplate.getForObject(url, InnerRes.class, "666106231640");
        System.out.println(res.toString());

        // 使用方法二，map传参
        url = "https://story.hhui.top/detail?id={id}";
        Map<String, Object> params = new HashMap<>();
        params.put("id", 666106231640L);
        res = restTemplate.getForObject(url, InnerRes.class, params);
        System.out.println(res.toString());
    }

    @Test
    public void testGetForEntity() {
        String url = "https://story.hhui.top/detail?id=666106231640";
        ResponseEntity<InnerRes> res = restTemplate.getForEntity(url, InnerRes.class);
        System.out.println(res);
    }

    @Test
    public void testPost() {
        String url = "http://localhost:8080/post";
        String email = "422943393@qq.com";
        String nick = "key";

        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("email", email);
        request.add("nick", nick);

        // 使用方法三
        URI uri = URI.create(url);
        String ans = restTemplate.postForObject(uri, request, String.class);
        System.out.println(ans);

        // 使用方法一
        ans = restTemplate.postForObject(url, request, String.class);
        System.out.println(ans);

        // 使用方法一，但是结合表单参数和uri参数的方式，其中uri参数的填充和get请求一致
        request.clear();
        request.add("email", email);
        ans = restTemplate.postForObject(url + "?nick={?}", request, String.class, nick);
        System.out.println(ans);

        // 使用方法二
        Map<String, String> params = new HashMap<>();
        params.put("nick", nick);
        ans = restTemplate.postForObject(url + "?nick={nick}", request, String.class, params);
        System.out.println(ans);
    }

}
