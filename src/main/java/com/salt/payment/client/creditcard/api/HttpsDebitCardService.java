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

import static com.salt.payment.client.creditcard.api.CreditCardService.REQ_INVALID_REQUEST;

/**
 * Builds the server calls for debit card transactions (purchase, refund, and batch close)
 *
 */
public class HttpsDebitCardService extends AbstractCreditCardService {
    // --------------------------------------------------------------------------
    // Fields
    // --------------------------------------------------------------------------
    private final int merchantId;
    private final String apiToken;
    private final MarketSegment marketSegment;

    public enum RequestCode {
        singleDebitPurchase, singleDebitRefund, batch;
    }

    /**
     * Creates an instance. Secured by default.
     * 
     * @param merchant
     *            the id of the merchant issuing the requests
     * @param apiToken
     *            the apiToken of the merchant issuing the requests
     * @param marketSegment
     *            the market segment of all requests. Not null.
     * @param url
     *            the gateway url to send requests to. Not null.
     */
    public HttpsDebitCardService(int merchantId, String apiToken, MarketSegment marketSegment,
            String url) {
        super(url);
        this.merchantId = merchantId;
        this.apiToken = apiToken;
        this.marketSegment = marketSegment;
    }

    /**
     * Creates an instance.
     * 
     * @param merchant
     *            the id of the merchant issuing the requests
     * @param apiToken
     *            the apiToken of the merchant issuing the requests
     * @param marketSegment
     *            the market segment of all requests. Not null.
     * @param url
     *            the gateway url to send requests to. Not null.
     * @param secured
     *            true if using https, otherwise http is used
     */
    public HttpsDebitCardService(int merchantId, String apiToken, MarketSegment marketSegment,
            String url, boolean secured) {
        super(url, secured);
        this.merchantId = merchantId;
        this.apiToken = apiToken;
        this.marketSegment = marketSegment;
    }

    /**
     * Creates an instance with default {@link MarketSegment.INTERNET}. Secured
     * by default.
     * 
     * @param merchant
     *            the id of the merchant issuing the requests
     * @param apiToken
     *            the apiToken of the merchant issuing the requests
     * @param url
     *            the gateway url to send requests to. Not null.
     */
    public HttpsDebitCardService(int merchantId, String apiToken, String url) {
        this(merchantId, apiToken, MarketSegment.RETAIL, url);
    }

    @Override
    protected void appendHeader(StringBuilder req, String requestCode) {
        super.appendHeader(req, requestCode);
        this.appendMerchantId(req, this.merchantId);
        this.appendApiToken(req, this.apiToken);
        this.appendParam(req, "marketSegmentCode", this.marketSegment.toCode());
    }

    protected void appendDebitCard(StringBuilder req, USDebitCard debitCard) {
        if (debitCard.getAccountType() != null) {
            this.appendParam(req, USDebitCard.Field.accountType.name(), debitCard.getAccountType());
        }
        if (debitCard.getCustomerNumber() != null) {
            this.appendParam(req, USDebitCard.Field.customerNumber.name(),
                    debitCard.getCustomerNumber());
        }
        if (debitCard.getMagneticData() != null) {
            this.appendParam(req, USDebitCard.Field.manageticData.name(),
                    debitCard.getMagneticData());
        }
        if (debitCard.getDukptValue() != null) {
            this.appendParam(req, USDebitCard.Field.dukptValue.name(), debitCard.getDukptValue());
        }
        if (debitCard.getPinBlock() != null) {
            this.appendParam(req, USDebitCard.Field.pinBlock.name(), debitCard.getPinBlock());
        }
        if (debitCard.getRequestKeyPTR() != null) {
            this.appendParam(req, USDebitCard.Field.requestKeyPTR.name(),
                    debitCard.getRequestKeyPTR());
        }
    }

    /**
     * @return the apiToken of the merchant issuing the requests
     */
    public String getApiToken() {
        return this.apiToken;
    }

    /**
     * @return the market segment of all the requests
     */
    public MarketSegment getMarketSegment() {
        return this.marketSegment;
    }

    /**
     * @return the id of the merchant issuing the requests
     */
    public int getMerchantId() {
        return this.merchantId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#singlePurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * com.admeris.creditcard.api.VerificationRequest, java.lang.Integer)
     */
    public DebitCardReceipt singlePurchase(String orderId, USDebitCard debitCard, Long amount,
            Long surchargeAmount, Long cashbackAmount) {
        if (debitCard == null) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        if (orderId == null) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, RequestCode.singleDebitPurchase.name());
            this.appendOrderId(req, orderId);
            this.appendDebitCard(req, debitCard);
            this.appendAmount(req, amount);
            this.appendParam(req, "surchargeAmount", surchargeAmount);
            this.appendParam(req, "cashbackAmount", cashbackAmount);
            // render indicator
            for (CreditCardIndicator indicator : super.indicatorList) {
                this.appendIndicator(req, indicator);
            }
        } catch (Exception e) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendDebit(req);
    }

    public DebitCardReceipt singleCredit(String orderId, USDebitCard debitCard, Long amount) {
        if (debitCard == null) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        if (orderId == null) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, RequestCode.singleDebitRefund.name());
            this.appendOrderId(req, orderId);
            this.appendDebitCard(req, debitCard);
            this.appendAmount(req, amount);

        } catch (Exception e) {
            return new DebitCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendDebit(req);
    }

    public CreditCardReceipt closeBatch() {

        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "batch");
            this.appendOperationType(req, "close");
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }
}
