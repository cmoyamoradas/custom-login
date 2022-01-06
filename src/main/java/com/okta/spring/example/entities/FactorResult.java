package com.okta.spring.example.entities;

public class FactorResult {
    public String getFactorResult() {
        return factorResult;
    }

    public void setFactorResult(String factorResult) {
        this.factorResult = factorResult;
    }

    public FactorResult(){};
    public FactorResult(String factorResult) {
        this.factorResult = factorResult;
    }

    private String factorResult;


}
