package com.okta.spring.example.entities;

public class OktaLogin {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    private String username;
    private String password;
    private Options options;

    public static class Options{
        public boolean isMultiOptionalFactorEnroll() {
            return multiOptionalFactorEnroll;
        }

        public void setMultiOptionalFactorEnroll(boolean multiOptionalFactorEnroll) {
            this.multiOptionalFactorEnroll = multiOptionalFactorEnroll;
        }

        public boolean isWarnBeforePasswordExpired() {
            return warnBeforePasswordExpired;
        }

        public void setWarnBeforePasswordExpired(boolean warnBeforePasswordExpired) {
            this.warnBeforePasswordExpired = warnBeforePasswordExpired;
        }

        private boolean multiOptionalFactorEnroll;
        private boolean warnBeforePasswordExpired;

        public Options(){}
        public Options(boolean multiOptionalFactorEnroll, boolean warnBeforePasswordExpired){
            this.multiOptionalFactorEnroll = multiOptionalFactorEnroll;
            this.warnBeforePasswordExpired = warnBeforePasswordExpired;
        }

    }

    public OktaLogin(){}
    public OktaLogin(String username, String password, Options options){
        this.username = username;
        this.password = password;
        this.options = options;
    }
}
