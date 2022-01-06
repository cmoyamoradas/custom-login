package com.okta.spring.example.entities;

public class OktaEnrolledFactors {
    public OktaEnrolledFactors(){};
    public OktaEnrolledFactors(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String id;
    private String status;



}
