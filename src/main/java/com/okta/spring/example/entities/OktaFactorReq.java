package com.okta.spring.example.entities;

public class OktaFactorReq {

    public String getFactorType() {
        return factorType;
    }

    public void setFactorType(String factorType) {
        this.factorType = factorType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public OktaFactorReq(){};
    public OktaFactorReq(String factorType,String provider) {
        this.provider = provider;
        this.factorType = factorType;
    }

    private String provider;
    private String factorType;



}
