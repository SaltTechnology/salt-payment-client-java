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
public enum CreditCardIndicator {

    PartialAuthIndicator(1), RecuringMOTOIndicator(2), EcommerceIndicator(3), SingleMOTOIndicator(4), SecureCodeIndicator(
            5);

    /**
     * Gets the enum from the code.
     * 
     * @param code
     *            the code. If null, then return null.
     * @return the enum having the specified <code>code</code> number, or null
     *         if code is null or unrecognized
     */
    public static CreditCardIndicator fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (final CreditCardIndicator s : CreditCardIndicator.values()) {
            if (code.equals(s.toCode())) {
                return s;
            }
        }
        return null;
    }

    private int code;

    private CreditCardIndicator(int code) {
        this.code = code;
    }

    public Integer toCode() {
        return this.code;
    }
}
