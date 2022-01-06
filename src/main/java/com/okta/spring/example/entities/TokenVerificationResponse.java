package com.okta.spring.example.entities;

public class TokenVerificationResponse {

    public TokenVerificationResponse(){};
    public TokenVerificationResponse(boolean active, String token_type, String scope, String client_id, String username, String sub) {
        this.active = active;
        this.token_type = token_type;
        this.scope = scope;
        this.client_id = client_id;
        this.username = username;
        this.sub = sub;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    private boolean active;
    private String token_type;
    private String scope;
    private String client_id;
    private String username;
    private String sub;


}
