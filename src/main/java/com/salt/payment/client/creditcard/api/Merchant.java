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
 * A threadsafe, immutable value object representing a Merchant.
 * 
 * 
 */
public final class Merchant {
    private final int merchantId;
    private final String apiToken;
    private final String storeId;

    /**
     * Create a new merchant with the provided ID, API token, and no store ID.
     * 
     * @param merchantId
     *            The ID of the merchant issuing the requests.
     * @param apiToken
     *            The API token of the merchant issuing the requests.
     * 
     * @throws IllegalArgumentException
     *             If apiToken is null.
     */
    public Merchant(int merchantId, String apiToken) {
        this(merchantId, apiToken, null);
    }

    /**
     * Create a new merchant with the provided ID, API token, and store ID.
     * 
     * @param merchantId
     *            The ID of the merchant issuing the requests.
     * @param apiToken
     *            The API token of the merchant issuing the requests.
     * @param storeId
     *            The merchant's store ID.
     * 
     * @throws IllegalArgumentException
     *             If apiToken is null.
     */

    public Merchant(int merchantId, String apiToken, String storeId) {
        if (apiToken == null) {
            throw new IllegalArgumentException("apiToken must not be null");
        }

        this.merchantId = merchantId;
        this.apiToken = apiToken;
        this.storeId = storeId;
    }

    public int getMerchantId() {
        return this.merchantId;
    }

    public String getApiToken() {
        return this.apiToken;
    }

    public String getStoreId() {
        return this.storeId;
    }
}
