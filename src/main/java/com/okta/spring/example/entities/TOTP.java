package com.okta.spring.example.entities;

import javax.validation.constraints.NotBlank;

public class TOTP {
    public TOTP(){};
    public TOTP(String passCode) {
        this.passCode = passCode;
    }

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }
    @NotBlank(message = "Passcode cannot be empty")
    private String passCode;

}
