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
 * The possible market segments for a transaction.
 * 
 * @since JSE5
 */
public enum MarketSegment {
    INTERNET("I"), MAIL_OR_TELEPHONE_ORDER("M"), RETAIL("G");

    /**
     * Gets the market segment from its code.
     * 
     * @param code
     *            the code of the market segment to get. Returns null if null.
     * @return the market segment or null if the code does not correspond to any
     *         market segment
     */
    public static MarketSegment fromCode(String code) {
        if (code != null) {
            for (final MarketSegment ms : MarketSegment.values()) {
                if (ms.toCode().equals(code)) {
                    return ms;
                }
            }
        }
        return null;
    }

    private String code;

    private MarketSegment(String code) {
        this.code = code;
    }

    /**
     * @return the code representing this market segment
     */
    public String toCode() {
        return this.code;
    }
}
