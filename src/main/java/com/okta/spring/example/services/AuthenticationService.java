package com.okta.spring.example.services;

import com.okta.spring.example.OktaAuthenticationToken;
import com.okta.spring.example.entities.OktaGroup;
import com.okta.spring.example.entities.OktaLogin;
import com.okta.spring.example.entities.OktaSession;
import com.okta.spring.example.entities.OktaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService implements AuthenticationProvider {
    WebClient.Builder apiClientBuilder;

    public static final String BASE_URL = System.getProperty("baseUrl");
    public static final String AUTHORIZATION = System.getProperty("authorization");

    @Autowired
    public void setApiClientBuilder(WebClient.Builder builder){
        this.apiClientBuilder = builder.baseUrl(BASE_URL)
                .defaultHeader("Authorization", "SSWS " + AUTHORIZATION);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        OktaLogin oktaLogin = new OktaLogin(username, password, new OktaLogin.Options(false,false));

        OktaSession session = this.apiClientBuilder.build()
                .post()
                .uri("/api/v1/authn")
                .body(Mono.just(oktaLogin), OktaLogin.class)
                .retrieve()
                .bodyToMono(OktaSession.class)
                .block();

        if (session != null) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH"));
            return new OktaAuthenticationToken(username, password, grantedAuthorities, session.get_embedded().getUser().getId());
        }
        throw new AuthenticationServiceException("Invalid credentials.");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
