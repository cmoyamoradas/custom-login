package com.okta.spring.example.entities;

public class OktaStagedUser extends OktaUser{
    public OktaStagedUser(){};
    public OktaStagedUser(Links _links) {
        this._links = _links;
    }

    public OktaStagedUser(String id, Profile profile, Credentials credentials, Links _links) {
        super(id, profile, credentials);
        this._links = _links;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

    private Links _links;

    public static class Links{
        public Links(){};
        public Links(Activate activate, Self self) {
            this.activate = activate;
            this.self = self;
        }

        public Activate getActivate() {
            return activate;
        }

        public void setActivate(Activate activate) {
            this.activate = activate;
        }

        public Self getSelf() {
            return self;
        }

        public void setSelf(Self self) {
            this.self = self;
        }

        private Activate activate;
        private Self self;

        public static class Activate{
            public Activate(){};
            public Activate(String href) {
                this.href = href;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            private String href;

        }
        public static class Self{
            public Self(){};
            public Self(String href) {
                this.href = href;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }

            private String href;

        }
    }
}
