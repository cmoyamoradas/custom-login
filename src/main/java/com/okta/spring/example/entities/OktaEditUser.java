package com.okta.spring.example.entities;

public class OktaEditUser {
    public static class Profile {
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }


        private String firstName;
        private String lastName;
        private String email;
        private String login;

        public Profile(){};
        public Profile(String firstName, String lastName, String email, String login){
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.login = login;
        }
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private Profile profile;

    public OktaEditUser(){}
    public OktaEditUser(Profile profile){
        this.profile=profile;
    }

}
