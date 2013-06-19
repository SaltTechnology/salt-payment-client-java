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
 * Requests that CVV2 should be verified.
 * 
 * @since JSE5
 */
public enum Cvv2Request {
    /**
     * CVV2 value is deliberately bypassed or is not provided by the merchant.
     */
    CVV2_NOT_SUBMITTED(0),
    /**
     * Card holder states that the CVV2 value is present on the card and can be
     * verified.
     */
    CVV2_PRESENT(1),
    /**
     * Card holder states that the CVV2 value is present on the card but it is
     * illegible.
     */
    CVV2_PRESENT_BUT_ILLEGIBLE(2),
    /**
     * Card holder states that the card has not CVV2 value.
     */
    CARD_HAS_NO_CVV2(9);

    /**
     * Gets the enum from the code.
     * 
     * @param code
     *            the code. If null, then return null.
     * @return the enum having the specified <code>code</code> number, or null
     *         if code is null or unrecognized
     */
    public static Cvv2Request fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (final Cvv2Request s : Cvv2Request.values()) {
            if (code.equals(s.toCode())) {
                return s;
            }
        }
        return null;
    }

    private short code;

    private Cvv2Request(int code) {
        this.code = (short) code;
    }

    public Short toCode() {
        return this.code;
    }
}
