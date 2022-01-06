package com.okta.spring.example.entities;

public class OktaNewUser extends OktaEditUser {

    public static class Credentials {
        public static class Password{
            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            private String value;
            public Password(){};
            public Password(String value){
                this.value = value;
            }
        }

        public Password getPassword() {
            return password;
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        private Password password;

        public Credentials(){};
        public Credentials(Password password){
            this.password = password;
        }
    }


    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    private Credentials credentials;

    public OktaNewUser(){
        super();
    };
    public OktaNewUser(Profile profile, Credentials credentials){
        super(profile);
        this.credentials = credentials;
    }

}
