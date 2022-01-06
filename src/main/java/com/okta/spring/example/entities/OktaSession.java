package com.okta.spring.example.entities;

public class OktaSession {
    private String expiresAt;
    private String status;
    private String sessionToken;
    private Embedded _embedded;

    public static class Embedded {
        public OktaUser getUser() {
            return user;
        }

        public void setUser(OktaUser user) {
            this.user = user;
        }

        public Embedded(OktaUser user) {
            this.user = user;
        }

        public Embedded(){}

        private OktaUser user;

    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    public OktaSession(String expiresAt, String status, String sessionToken, Embedded _embedded) {
        this.expiresAt = expiresAt;
        this.status = status;
        this.sessionToken = sessionToken;
        this._embedded = _embedded;
    }
    public OktaSession(){}

}
