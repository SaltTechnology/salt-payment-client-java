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
 * An object containing a customer's CreditCard and CustomerProfile
 */
public class PaymentProfile {
    private CreditCard creditCard;
    private CustomerProfile customerProfile;

    public PaymentProfile(CreditCard creditCard, CustomerProfile customerProfile) {
        this.creditCard = creditCard;
        this.customerProfile = customerProfile;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }

    public CustomerProfile getCustomerProfile() {
        return this.customerProfile;
    }

    public void setCreditCard(CreditCard newCreditCard) {
        this.creditCard = newCreditCard;
    }

    public void setCustomerProfile(CustomerProfile newCustomerProfile) {
        this.customerProfile = newCustomerProfile;
    }

    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("[CreditCard]");
        str.append(this.creditCard).append("\n");
        str.append("[CustomerProfile]");
        str.append(this.customerProfile).append("\n");
        return str.toString();
    }
}
