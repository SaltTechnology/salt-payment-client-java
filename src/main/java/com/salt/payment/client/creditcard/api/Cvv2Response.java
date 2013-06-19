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
 * A value object containing the results of cvv2 verification.
 * 
 * @Immutable
 * @since JSE5
 */
public class Cvv2Response {
    private final String code;
    private final String message;

    /**
     * Creates an instance.
     * 
     * @param code
     * @param message
     */
    public Cvv2Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return the cvv2 response code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return the cvv2 response message
     */
    public String getMessage() {
        return this.message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("[");
        str.append("code=").append(this.code).append(",");
        str.append("message=").append(this.message).append("");
        str.append("]");
        return str.toString();
    }
}
