package com.okta.spring.example.entities;

public class OktaUser extends OktaNewUser{

    public OktaUser(String id, ExtCredentias credentials) {
        this.id = id;
        this.credentials = credentials;
    }

    public OktaUser(Profile profile, Credentials credentials, String id, ExtCredentias credentials1) {
        super(profile, credentials);
        this.id = id;
        this.credentials = credentials1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    private String id;

    @Override
    public ExtCredentias getCredentials() {
        return credentials;
    }

    public void setCredentials(ExtCredentias credentials) {
        this.credentials = credentials;
    }

    private ExtCredentias credentials;

    public static class ExtCredentias extends Credentials{
        public ExtCredentias(){};
        public ExtCredentias(Provider provider) {
            this.provider = provider;
        }

        public ExtCredentias(Password password, Provider provider) {
            super(password);
            this.provider = provider;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        private Provider provider;

        public class Provider{
            private String type;

            public Provider(){};
            public Provider(String type, String name) {
                this.type = type;
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String name;

        }
    }

    public OktaUser(){
        super();
    };

    public OktaUser(String id, Profile profile, Credentials credentials){
        super(profile,credentials);
        this.id = id;
    }


}
