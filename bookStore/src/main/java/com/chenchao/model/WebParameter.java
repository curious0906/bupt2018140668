package com.chenchao.model;

public class WebParameter {
    private long webParamId;
    private String webName;
    private String RegistrationTerms;
    private String announcement;
    private String address;
    private String postalCode;
    private String phoneCall;

    public void setWebParamId(long webParamId) {
        this.webParamId = webParamId;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public void setRegistrationTerms(String registrationTerms) {
        RegistrationTerms = registrationTerms;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneCall(String phoneCall) {
        this.phoneCall = phoneCall;
    }


    public long getWebParamId() {
        return webParamId;
    }

    public String getWebName() {
        return webName;
    }

    public String getRegistrationTerms() {
        return RegistrationTerms;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhoneCall() {
        return phoneCall;
    }



}
