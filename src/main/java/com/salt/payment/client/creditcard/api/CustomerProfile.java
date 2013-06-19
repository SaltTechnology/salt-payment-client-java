/*******************************************************************************
 * Salt Payment Client API
 * Version 1.0.0
 * http://salttechnology.github.io/core_api_doc.htm
 * 
 * Copyright (c) 2013 Salt Technology
 * Licensed under the MIT license
 * https://github.com/SaltTechnology/salt-payment-client-java/blob/master/LICENSE
 ******************************************************************************/
package com.salt.payment.client.creditcard.api;

/**
 * A customer profile.
 * 
 * @since JSE5
 */
public class CustomerProfile {
    private String legalName;
    private String tradeName;
    private String website;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String faxNumber;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postal;
    private String country;

    /**
     * Create a new blank CustomerProfile.
     */
    public CustomerProfile() {
    }

    public String getLegalName() {
        return this.legalName;
    }

    public String getTradeName() {
        return this.tradeName;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getFaxNumber() {
        return this.faxNumber;
    }

    public String getAddress1() {
        return this.address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public String getCity() {
        return this.city;
    }

    public String getProvince() {
        return this.province;
    }

    public String getPostal() {
        return this.postal;
    }

    public String getCountry() {
        return this.country;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isBlank() {
        return (!((firstName != null && firstName.length() > 0)
                || (lastName != null && lastName.length() > 0)
                || (legalName != null && legalName.length() > 0)
                || (tradeName != null && tradeName.length() > 0)
                || (address1 != null && address1.length() > 0)
                || (address2 != null && address2.length() > 0)
                || (city != null && city.length() > 0)
                || (province != null && province.length() > 0)
                || (postal != null && postal.length() > 0)
                || (country != null && country.length() > 0)
                || (website != null && website.length() > 0)
                || (phoneNumber != null && phoneNumber.length() > 0) || (faxNumber != null && faxNumber
                .length() > 0)));
    }

    @Override
    public String toString() {
        StringBuilder req = new StringBuilder();
        Utils.appendToStringBuilder(req, "profileLegalName", this.getLegalName());
        Utils.appendToStringBuilder(req, "profileTradeName", this.getTradeName());
        Utils.appendToStringBuilder(req, "profileWebsite", this.getWebsite());
        Utils.appendToStringBuilder(req, "profileFirstName", this.getFirstName());
        Utils.appendToStringBuilder(req, "profileLastName", this.getLastName());
        Utils.appendToStringBuilder(req, "profilePhoneNumber", this.getPhoneNumber());
        Utils.appendToStringBuilder(req, "profileFaxNumber", this.getFaxNumber());
        Utils.appendToStringBuilder(req, "profileAddress1", this.getAddress1());
        Utils.appendToStringBuilder(req, "profileAddress2", this.getAddress2());
        Utils.appendToStringBuilder(req, "profileCity", this.getCity());
        Utils.appendToStringBuilder(req, "profileProvince", this.getProvince());
        Utils.appendToStringBuilder(req, "profilePostal", this.getPostal());
        Utils.appendToStringBuilder(req, "profileCountry", this.getCountry());
        return req.toString();
    }
} // end class
