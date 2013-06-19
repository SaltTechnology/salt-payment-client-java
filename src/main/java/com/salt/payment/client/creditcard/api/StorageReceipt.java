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

import java.util.Date;

import static com.salt.payment.client.creditcard.api.CreditCardService.*;

/**
 * The receipt returned from processing a secure storage request.
 * 
 * @since JSE5
 */
public class StorageReceipt extends AbstractReceipt {
    private PaymentProfile paymentProfile = null;
    private String storageTokenId = null;

    public StorageReceipt(Integer errorCode, String errorMessage, String debugMessage) {
        super(errorCode, errorMessage, debugMessage);
    }

    /**
     * Creates an instance by parsing the response from the gateway.
     * 
     * @param response
     *            the response from the gateway to parse. Not null.
     */
    public StorageReceipt(String response) {
        super(response);
        if (response == null || response.length() <= 0) {
            // null response, null receipt
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage = "null or empty response received.";
            this.debugMessage = null;
            this.processedDateTime = new Date();
            return;
        }
        // parse the storage-specific parameters
        try {
            // storage token ID
            this.storageTokenId = this.params.get("STORAGE_TOKEN_ID");

            // make sure profile available
            final Boolean paymentProfileAvailable = this.parseBoolean("PAYMENT_PROFILE_AVAILABLE");
            // parse the profile
            if (paymentProfileAvailable != null && paymentProfileAvailable) {
                // parse the CreditCard
                CreditCard creditCard = null;
                final Boolean creditCardAvailable = this.parseBoolean("CREDIT_CARD_AVAILABLE");
                if (creditCardAvailable != null && creditCardAvailable) {
                    String sanitized = this.params.get("CREDIT_CARD_NUMBER");
                    sanitized = sanitized.replaceAll("\\*", "");
                    creditCard =
                            new CreditCard(new Long(sanitized), this.parseShort("EXPIRY_DATE"));
                }
                // parse the Customer Profile
                CustomerProfile profile = null;
                final Boolean customerProfileAvailable =
                        this.parseBoolean("CUSTOMER_PROFILE_AVAILABLE");
                if (customerProfileAvailable != null && customerProfileAvailable) {
                    profile = new CustomerProfile();
                    profile.setLegalName(this.params.get("CUSTOMER_PROFILE_LEGAL_NAME"));
                    profile.setTradeName(this.params.get("CUSTOMER_PROFILE_TRADE_NAME"));
                    profile.setWebsite(this.params.get("CUSTOMER_PROFILE_WEBSITE"));
                    profile.setFirstName(this.params.get("CUSTOMER_PROFILE_FIRST_NAME"));
                    profile.setLastName(this.params.get("CUSTOMER_PROFILE_LAST_NAME"));
                    profile.setPhoneNumber(this.params.get("CUSTOMER_PROFILE_PHONE_NUMBER"));
                    profile.setFaxNumber(this.params.get("CUSTOMER_PROFILE_FAX_NUMBER"));
                    profile.setAddress1(this.params.get("CUSTOMER_PROFILE_ADDRESS1"));
                    profile.setAddress2(this.params.get("CUSTOMER_PROFILE_ADDRESS2"));
                    profile.setCity(this.params.get("CUSTOMER_PROFILE_CITY"));
                    profile.setProvince(this.params.get("CUSTOMER_PROFILE_PROVINCE"));
                    profile.setPostal(this.params.get("CUSTOMER_PROFILE_POSTAL"));
                    profile.setCountry(this.params.get("CUSTOMER_PROFILE_COUNTRY"));
                }
                this.paymentProfile = new PaymentProfile(creditCard, profile);
            } else {
                this.paymentProfile = null;
            }
        } catch (final Exception e) {
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage =
                    "could not parse response, one or more fields were in an invalid format.";
            this.debugMessage = e.toString();
            this.processedDateTime = new Date();
            return;
        }
    }

    /**
     * @return profile info
     */
    public PaymentProfile getPaymentProfile() {
        return this.paymentProfile;
    }

    /**
     * @return storage token ID
     */
    public String getStorageTokenId() {
        return this.storageTokenId;
    }
}
