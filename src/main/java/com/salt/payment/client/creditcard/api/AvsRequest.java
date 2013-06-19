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
 * Requests that AVS should be performed. Note that zip may also refer to postal
 * code.
 * 
 * @since JSE5
 */
public enum AvsRequest {
    /** Full AVS by verifying both street and zip. */
    VERIFY_STREET_AND_ZIP(0),
    /** Verify the zip only. */
    VERIFY_ZIP_ONLY(1);

    /**
     * Gets the enum from the code.
     * 
     * @param code
     *            the code. If null, then return null.
     * @return the enum having the specified <code>code</code> number, or null
     *         if code is null or unrecognized
     */
    public static AvsRequest fromCode(Short code) {
        if (code == null) {
            return null;
        }
        for (final AvsRequest s : AvsRequest.values()) {
            if (code.equals(s.toCode())) {
                return s;
            }
        }
        return null;
    }

    private short code;

    private AvsRequest(int code) {
        this.code = (short) code;
    }

    public Short toCode() {
        return this.code;
    }
}
