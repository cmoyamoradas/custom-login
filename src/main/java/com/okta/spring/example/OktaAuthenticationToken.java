package com.okta.spring.example;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OktaAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public OktaAuthenticationToken(Object principal, Object credentials, String id) {
        super(principal, credentials);
        this.id = id;
    }

    public OktaAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String id) {
        super(principal, credentials, authorities);
        this.id = id;
    }
}
