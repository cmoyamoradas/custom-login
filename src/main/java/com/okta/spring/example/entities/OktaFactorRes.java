package com.okta.spring.example.entities;

import java.util.List;

public class OktaFactorRes extends OktaFactorReq{
    public OktaFactorRes(){};
    public OktaFactorRes(String id, Embedded _embedded) {
        this.id = id;
        this._embedded = _embedded;
    }

    public OktaFactorRes(String factorType, String provider, String id, Embedded _embedded) {
        super(factorType, provider);
        this.id = id;
        this._embedded = _embedded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(Embedded _embedded) {
        this._embedded = _embedded;
    }

    private String id;
    private Embedded _embedded;
    public static class Embedded {
        public Embedded(){};
        public Embedded(Activation activation) {
            this.activation = activation;
        }

        public Activation getActivation() {
            return activation;
        }

        public void setActivation(Activation activation) {
            this.activation = activation;
        }

        private Activation activation;
        public static class Activation{
            public Activation(){};
            public Activation(Links _links) {
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
                public Links(Qrcode qrcode, List<Send> send) {
                    this.qrcode = qrcode;
                    this.send = send;
                }

                public Qrcode getQrcode() {
                    return qrcode;
                }

                public void setQrcode(Qrcode qrcode) {
                    this.qrcode = qrcode;
                }

                private Qrcode qrcode;

                public static class Qrcode{
                    private String href;

                    public Qrcode(){};
                    public Qrcode(String href, String type) {
                        this.href = href;
                        this.type = type;
                    }

                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                        this.href = href;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    private String type;
                }


                public Links(){};

                public List<Send> getSend() {
                    return send;
                }

                public void setSend(List<Send> send) {
                    this.send = send;
                }

                private List<Send> send;

                public static class Send{

                    public Send(){};

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getHref() {
                        return href;
                    }

                    public void setHref(String href) {
                        this.href = href;
                    }

                    private String name;
                    private String href;

                    public Hints getHints() {
                        return hints;
                    }

                    public void setHints(Hints hints) {
                        this.hints = hints;
                    }

                    private Hints hints;

                    public static class Hints{
                        public Hints(){};
                        public Hints(List<String> allow) {
                            this.allow = allow;
                        }

                        public List<String> getAllow() {
                            return allow;
                        }

                        public void setAllow(List<String> allow) {
                            this.allow = allow;
                        }

                        private List<String> allow;

                    }
                }
            }
        }
    }
}
