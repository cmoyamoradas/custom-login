package com.okta.spring.example.entities;

public class OktaGroup {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public OktaGroup(){}
    public OktaGroup(String id, String type, Profile profile) {
        this.id = id;
        this.type = type;
        this.profile = profile;
    }

    private String id;
    private String type;
    private Profile profile;

    public static class Profile{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Profile(){}
        public Profile(String name, String description) {
            this.name = name;
            this.description = description;
        }

        private String name;
        private String description;
    }

    @Override
    public boolean equals(Object obj) {
        return ((OktaGroup)obj).id.equals(this.id);
    }
}
