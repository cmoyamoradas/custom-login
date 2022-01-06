package com.okta.spring.example.services;

import com.okta.spring.example.entities.TokenVerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class Oauth2ServiceImpl implements Oauth2Service{

    public static final String BASE_URL = System.getProperty("authServerBaseUrl");
    public static final String AUTHORIZATION = System.getProperty("clientCredentials");

    WebClient.Builder apiClientBuilder;

    @Autowired
    public Oauth2ServiceImpl(WebClient.Builder apiClientBuilder){
        this.apiClientBuilder = apiClientBuilder;
        this.apiClientBuilder.baseUrl(BASE_URL)
                .defaultHeader("Authorization", "Basic " + AUTHORIZATION);
    }

    @Override
    public TokenVerificationResponse verifyAccessToken(String token) {
        return this.apiClientBuilder.build()
                .post()
                .uri("/v1/introspect?token=" + token + "&token_type_hint=access_token")
                .header("Content-Type","application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(TokenVerificationResponse.class)
                .block();
    }
}
