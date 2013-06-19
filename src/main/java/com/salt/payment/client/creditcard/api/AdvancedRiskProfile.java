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
 * For now, each field is split up into a semantic grouping.
 * 
 */
public final class AdvancedRiskProfile {
    public static final String HANDLE = "arp";

    public static class Builder {
        private final Customer customer = new Customer();
        private final Address shipping = new Address();
        private final Merchandise merchandise = new Merchandise();
        private final Issuer issuer = new Issuer();
        private Network network;
        private Address billing;

        public Builder(String billingAddress, String billingCity, String billingProvince,
                String billingPostal, String billingCountry, String ip) {
            this.billing =
                    new Address(billingAddress, billingCity, billingProvince, billingPostal,
                            billingCountry);
            this.network = new Network(ip);
        }

        public Builder() {
            network = new Network();
            billing = new Address();
        }

        public Builder setEmailDomain(String domain) {
            this.customer.domain = domain;
            return this;
        }

        public Builder setUsernameHash(String usernameHash) {
            this.customer.usernameHash = usernameHash;
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            this.customer.passwordHash = passwordHash;
            return this;
        }

        public Builder setEmailHash(String emailHash) {
            this.customer.emailHash = emailHash;
            return this;
        }

        public Builder setPhone(String phone) {
            this.customer.phone = phone;
            return this;
        }

        public Builder setCustomerName(String name) {
            this.customer.customerName = name;
            return this;
        }

        public Builder setGender(Gender gender) {
            this.customer.gender = gender;
            return this;
        }

        public Builder setDriverLicense(String driverLicense) {
            this.customer.driverLicense = driverLicense;
            return this;
        }

        public Builder setUniqueId(String uniqueId) {
            this.customer.uniqueId = uniqueId;
            return this;
        }

        public Builder setAnid(String anid) {
            this.customer.anid = anid;
            return this;
        }

        public Builder setFraudSessionId(String fraudSessionId) {
            this.customer.fraudSessionId = fraudSessionId;
            return this;
        }

        public Builder setShippingAddress(String address) {
            this.shipping.address = address;
            return this;
        }

        public Builder setShippingCity(String city) {
            this.shipping.city = city;
            return this;
        }

        public Builder setShippingProvince(String province) {
            this.shipping.province = province;
            return this;
        }

        public Builder setShippingPostal(String postal) {
            this.shipping.postal = postal;
            return this;
        }

        public Builder setShippingCountry(String country) {
            this.shipping.country = country;
            return this;
        }

        public Builder setUserAgent(String userAgent) {
            this.network.userAgent = userAgent;
            return this;
        }

        public Builder setAcceptedLanguages(String acceptedLanguages) {
            this.network.language = acceptedLanguages;
            return this;
        }

        public Builder setProxiedIP(String proxiedIP) {
            this.network.proxiedIP = proxiedIP;
            return this;
        }

        public Builder setBIN(String bin) {
            this.issuer.bin = bin;
            return this;
        }

        public Builder setIssuerName(String name) {
            this.issuer.name = name;
            return this;
        }

        public Builder setIssuerPhone(String phone) {
            this.issuer.phone = phone;
            return this;
        }

        public Builder setBillingPhone(String billingPhone) {
            this.merchandise.billingPhone = billingPhone;
            return this;
        }

        public Builder setShippingPhone(String shippingPhone) {
            this.merchandise.shippingPhone = shippingPhone;
            return this;
        }

        public Builder setShippingName(String shippingName) {
            this.merchandise.shippingName = shippingName;
            return this;
        }

        public Builder setshippingType(ShippingType shippingType) {
            this.merchandise.shippingType = shippingType;
            return this;
        }

        public Builder setTransactionSource(TransactionSource transactionSource) {
            this.merchandise.transactionSource = transactionSource;
            return this;
        }

        public AdvancedRiskProfile build() {
            return new AdvancedRiskProfile(this.customer, this.billing, this.shipping,
                    this.merchandise, this.network, this.issuer);
        }
    }

    public enum TransactionSource {
        IVR("IVR"), CRM("CRM"), WSC("Web Self Care"), AP("AP"), AGENT("Agent");
        private String desc;

        private TransactionSource(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }

    static class Merchandise {
        private String billingPhone;
        private String shippingPhone;
        private String shippingName;
        private ShippingType shippingType;
        private TransactionSource transactionSource;
    }

    static class Customer {
        private String domain;
        private String usernameHash;
        private String passwordHash;
        private String emailHash;
        private String phone;
        private String customerName;
        private Gender gender;
        private String driverLicense;
        private String uniqueId;
        private String anid;
        private String fraudSessionId;
    }

    static class Address {
        private String address;
        private String city;
        private String province;
        private String postal;
        private String country;

        public Address() {
        }

        public Address(String address, String city, String province, String postal, String country) {
            this.address = address;
            this.city = city;
            this.province = province;
            this.postal = postal;
            this.country = country;
        }
    }

    static class Network {
        private String ip;
        private String userAgent;
        private String language;
        private String proxiedIP;

        public Network(String ip) {
            this.ip = ip;
        }

        public Network() {

        }
    }

    static class Issuer {
        private String bin;
        private String name;
        private String phone;
    }

    public enum Gender {
        M, F;
    }

    public enum ShippingType {
        SAME_DAY("SD"), NEXT_DAY("ND"), SECOND_DAY("2D"), STANDARD("ST");

        private String value;

        private ShippingType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private final Customer customer;
    private final Address billing;
    private final Address shipping;
    private final Network network;
    private final Issuer issuer;
    private final Merchandise merchandise;

    private AdvancedRiskProfile(Customer customer, Address billing, Address shipping,
            Merchandise merchandise, Network network, Issuer issuer) {
        this.customer = customer;
        this.billing = billing;
        this.shipping = shipping;
        this.network = network;
        this.issuer = issuer;
        this.merchandise = merchandise;
    }

    public String getEmailDomain() {
        return this.customer.domain;
    }

    public String getUsernameHash() {
        return this.customer.usernameHash;
    }

    public String getPasswordHash() {
        return this.customer.passwordHash;
    }

    public String getEmailHash() {
        return this.customer.emailHash;
    }

    public String getPhone() {
        return this.customer.phone;
    }

    public String getCustomerName() {
        return this.customer.customerName;
    }

    public String getGender() {
        if (this.customer.gender != null) {
            return this.customer.gender.name();
        }
        return null;
    }

    public String getDriverLicense() {
        return this.customer.driverLicense;
    }

    public String getUniqueId() {
        return this.customer.uniqueId;
    }

    public String getAnid() {
        return this.customer.anid;
    }

    public String getFraudSessionId() {
        return this.customer.fraudSessionId;
    }

    public String getBillingAddress() {
        return this.billing.address;
    }

    public String getBillingCity() {
        return this.billing.city;
    }

    public String getBillingProvince() {
        return this.billing.province;
    }

    public String getBillingPostal() {
        return this.billing.postal;
    }

    public String getBillingCountry() {
        return this.billing.country;
    }

    public String getShippingAddress() {
        return this.shipping.address;
    }

    public String getShippingCity() {
        return this.shipping.city;
    }

    public String getShippingProvince() {
        return this.shipping.province;
    }

    public String getShippingPostal() {
        return this.shipping.postal;
    }

    public String getShippingCountry() {
        return this.shipping.country;
    }

    public String getBillingPhone() {
        return this.merchandise.billingPhone;
    }

    public String getShippingPhone() {
        return this.merchandise.shippingPhone;
    }

    public String getShippingName() {
        return this.merchandise.shippingName;
    }

    public String getShippingType() {
        if (this.merchandise.shippingType != null) {
            return this.merchandise.shippingType.value;
        }
        return null;
    }

    public String getTransactionSource() {
        if (this.merchandise.transactionSource != null) {
            return this.merchandise.transactionSource.name();
        }
        return null;
    }

    public String getIssuerBin() {
        return this.issuer.bin;
    }

    public String getIssuerName() {
        return this.issuer.name;
    }

    public String getIssuerPhone() {
        return this.issuer.phone;
    }

    public String getIP() {
        return this.network.ip;
    }

    public String getUserAgent() {
        return this.network.userAgent;
    }

    public String getAcceptedLanguages() {
        return this.network.language;
    }

    public String getProxiedIP() {
        return this.network.proxiedIP;
    }
}
