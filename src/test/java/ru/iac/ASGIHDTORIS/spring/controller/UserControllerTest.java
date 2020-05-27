package ru.iac.ASGIHDTORIS.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void checkName() {
        final RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "Token 0167a137-9f97-4b93-8588-45ac7a736148");
        headers.set("SystemID", "urn-eis-toris-gihd-uzd-2020");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://test.toris.gov.spb.ru/api/personal/user/profile/";
        String str = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).toString();

        System.out.println(str);

//        String source = restTemplate.getForObject("http://test.toris.gov.spb.ru/api/personal/user/profile/", String.class);
//
//        System.out.println(source);
    }
}