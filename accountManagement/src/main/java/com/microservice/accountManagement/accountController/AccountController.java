package com.microservice.accountManagement.accountController;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;

@RestController
@RequestMapping("/user")
public class AccountController {
    RestTemplate restTemplate;

    public AccountController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/reset")
    public String ResetCode(){
        return "Code reset successful!";
    }

    @GetMapping("/template")
    public ResponseEntity<String> testRestTemplate(){
        String url = "http://JWT-WS/user/home";
        ResponseEntity<String> str = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<String>(){});
        return str;
    }
}