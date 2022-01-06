package com.okta.spring.example.services;

import com.okta.spring.example.entities.TokenVerificationResponse;

public interface Oauth2Service {
    TokenVerificationResponse verifyAccessToken(String token);
}
