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
 * A creditcard for making creditcard purchases.
 * 
 * @immutable
 * @since JSE5
 */
public class USDebitCard {

    private String magneticData;
    private String customerNumber;
    private String pinBlock;
    private String dukptValue;
    private String requestKeyPTR;
    private String accountType;

    public enum Field {
        manageticData, customerNumber, pinBlock, dukptValue, requestKeyPTR, accountType;
    }

    public USDebitCard() {

    }

    public USDebitCard(String magneticData, String pinBlock, String dukptValue,
            String requestKeyPTR, String accountType) {
        this.magneticData = magneticData;
        this.pinBlock = pinBlock;
        this.dukptValue = dukptValue;
        this.requestKeyPTR = requestKeyPTR;
        this.accountType = accountType;

    }

    public enum AccountType {
        Checking("0"), Savings("1");
        private final String value;

        AccountType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum KeyPTR {
        DES("D"), TripleDES("T");
        private final String value;

        KeyPTR(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public String getMagneticData() {
        return magneticData;
    }

    public void setMagneticData(String magneticData) {
        this.magneticData = magneticData;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getPinBlock() {
        return pinBlock;
    }

    public void setPinBlock(String pinBlock) {
        this.pinBlock = pinBlock;
    }

    public String getDukptValue() {
        return dukptValue;
    }

    public void setDukptValue(String dukptValue) {
        this.dukptValue = dukptValue;
    }

    public String getRequestKeyPTR() {
        return requestKeyPTR;
    }

    public void setRequestKeyPTR(String requestKeyPTR) {
        this.requestKeyPTR = requestKeyPTR;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
