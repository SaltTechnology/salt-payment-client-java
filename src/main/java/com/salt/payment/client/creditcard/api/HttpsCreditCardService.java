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

import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;

/**
 * The implementation of the {@link CreditCardService} that issues creditcard
 * requests through https.
 * <p>
 * This class is thread-safe.
 * 
 * @immutable
 * @since JSE5
 */
public final class HttpsCreditCardService extends AbstractCreditCardService implements
        CreditCardService {
    private static final MarketSegment DEFAULT_MARKET_SEGMENT = MarketSegment.INTERNET;

    // --------------------------------------------------------------------------
    // Fields
    // --------------------------------------------------------------------------
    private final Merchant merchant;

    /**
     * Creates a new instance. Secured by default.
     * 
     * @param merchant
     *            the merchant issuing the requests.
     * @param url
     *            the creditcard gateway url to send requests to. Not null.
     */
    public HttpsCreditCardService(Merchant merchant, String url) {
        super(url);
        this.merchant = merchant;
    }

    /**
     * Creates an instance.
     * 
     * @param merchant
     *            the merchant issuing the requests.
     * @param url
     *            the creditcard gateway url to send requests to. Not null.
     * @param secured
     *            true if using https, otherwise http is used
     */
    public HttpsCreditCardService(Merchant merchant, String url, boolean secured) {
        super(url, secured);
        this.merchant = merchant;
    }

    /**
     * Creates an instance. Secured by default.
     * 
     * @param merchantId
     *            the id of the merchant issueing the requests
     * @param apiToken
     *            the apiToken of the merchant issueing the requests
     * @param marketSegment
     *            the market segment of all requests. Not null.
     * @param url
     *            the creditcard gateway url to send requests to. Not null.
     */
    @Deprecated
    public HttpsCreditCardService(int merchantId, String apiToken, MarketSegment marketSegment,
            String url) {
        super(url);
        this.merchant = new Merchant(merchantId, apiToken);
    }

    /**
     * Creates an instance.
     * 
     * @param merchantId
     *            the id of the merchant issueing the requests
     * @param apiToken
     *            the apiToken of the merchant issueing the requests
     * @param marketSegment
     *            the market segment of all requests. Not null.
     * @param url
     *            the creditcard gateway url to send requests to. Not null.
     * @param secured
     *            true if using https, otherwise http is used
     */
    @Deprecated
    public HttpsCreditCardService(int merchantId, String apiToken, MarketSegment marketSegment,
            String url, boolean secured) {
        super(url, secured);
        this.merchant = new Merchant(merchantId, apiToken);
    }

    /**
     * Creates an instance with default {@link MarketSegment.INTERNET}. Secured
     * by default.
     * 
     * @param merchantId
     *            the id of the merchant issueing the requests
     * @param apiToken
     *            the apiToken of the merchant issueing the requests
     * @param url
     *            the creditcard gateway url to send requests to. Not null.
     */
    @Deprecated
    public HttpsCreditCardService(int merchantId, String apiToken, String url) {
        this(merchantId, apiToken, MarketSegment.INTERNET, url);
    }

    @Override
    protected void appendHeader(StringBuilder req, String requestCode) {
        super.appendHeader(req, requestCode);
        this.appendMerchantId(req, this.merchant.getMerchantId());
        this.appendApiToken(req, this.merchant.getApiToken());
        this.appendParam(req, "marketSegmentCode", DEFAULT_MARKET_SEGMENT.toCode());

        if (this.merchant.getStoreId() != null) {
            this.appendParam(req, "storeId", this.merchant.getStoreId());
        }
    }

    /**
     * @return the Merchant issuing the requests.
     */
    public Merchant getMerchant() {
        return this.merchant;
    }

    /**
     * @return the apiToken of the merchant issueing the requests
     */
    @Deprecated
    public String getApiToken() {
        return this.merchant.getApiToken();
    }

    /**
     * @return the market segment of all the requests
     */
    @Deprecated
    public MarketSegment getMarketSegment() {
        return DEFAULT_MARKET_SEGMENT;
    }

    /**
     * @return the id of the merchant issueing the requests
     */
    @Deprecated
    public int getMerchantId() {
        return this.merchant.getMerchantId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#installmentPurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * java.util.Date, int, com.admeris.creditcard.api.VerificationRequest,
     * java.lang.Integer)
     */
    public CreditCardReceipt installmentPurchase(String orderId, CreditCard creditCard,
            long perInstallmentAmount, Date startDate, int totalNumberInstallments,
            VerificationRequest verificationRequest) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        final StringBuilder req = new StringBuilder();
        try {
            // create the request string
            this.appendHeader(req, "installmentPurchase");
            this.appendOrderId(req, orderId);
            this.appendCreditCard(req, creditCard);
            this.appendAmount(req, perInstallmentAmount);
            this.appendStartDate(req, startDate);
            this.appendTotalNumberInstallments(req, totalNumberInstallments);
            this.appendVerificationRequest(req, verificationRequest);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#recurringPurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * java.util.Date, java.util.Date,
     * com.admeris.creditcard.api.VerificationRequest,
     */
    public CreditCardReceipt recurringPurchase(String orderId, CreditCard creditCard,
            long perPaymentAmount, Date startDate, Date endDate, Schedule schedule,
            VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.recurringPurchase(new PeriodicPurchaseInfo(null, null, schedule,
                perPaymentAmount, orderId, null, startDate, endDate, null), creditCard, null,
                verificationRequest, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#recurringPurchase(
     * java.lang.String, java.lang.String, long, java.util.Date, java.util.Date,
     * com.admeris.creditcard.api.PeriodicPurchaseInfo.Schedule,
     * com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt recurringPurchase(String orderId, String storageTokenId,
            long perPaymentAmount, Date startDate, Date endDate, Schedule schedule,
            VerificationRequest verificationRequest) {
        if (storageTokenId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.recurringPurchase(new PeriodicPurchaseInfo(null, null, schedule,
                perPaymentAmount, orderId, null, startDate, endDate, null), null, storageTokenId,
                verificationRequest, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#recurringPurchase(
     * com.admeris.creditcard.api.PeriodicPurchaseInfo,
     * com.admeris.creditcard.api.CreditCard, java.lang.String,
     * com.admeris.creditcard.api.VerificationRequest,
     * com.admeris.creditcard.api.PurchaseCardRequest)
     */
    public CreditCardReceipt recurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest) {
        return this.recurringPurchase(periodicPurchaseInfo, creditCard, storageTokenId,
                verificationRequest, null);
    }

    /**
     * Latest recurringPurchase method
     * 
     * @param periodicPurchaseInfo
     * @param creditCard
     * @param storageTokenId
     * @param verificationRequest
     * @param purchaseCard
     * @return
     */
    public CreditCardReceipt recurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest,
            PurchaseCardRequest purchaseCard) {
        if (periodicPurchaseInfo.getOrderId() == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        if (creditCard == null && storageTokenId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST,
                    "at least one of creditCard or storageTokenId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "recurringPurchase");
            this.appendOperationType(req, "create");
            this.appendPeriodicPurchaseInfo(req, periodicPurchaseInfo);
            if (creditCard != null) {
                this.appendCreditCard(req, creditCard);
            }
            if (storageTokenId != null) {
                this.appendStorageTokenId(req, storageTokenId);
            }
            this.appendVerificationRequest(req, verificationRequest);
            this.appendPurchaseCard(req, purchaseCard);

            // render indicator
            for (CreditCardIndicator indicator : super.indicatorList) {
                this.appendIndicator(req, indicator);
            }

        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt holdRecurringPurchase(Long recurringPurchaseId) {
        return this.updateRecurringPurchase(new PeriodicPurchaseInfo(recurringPurchaseId,
                PeriodicPurchaseInfo.State.ON_HOLD), null, null, null);
    }

    public CreditCardReceipt resumeRecurringPurchase(Long recurringPurchaseId) {
        return this.updateRecurringPurchase(new PeriodicPurchaseInfo(recurringPurchaseId,
                PeriodicPurchaseInfo.State.IN_PROGRESS), null, null, null);
    }

    public CreditCardReceipt cancelRecurringPurchase(Long recurringPurchaseId) {
        return this.updateRecurringPurchase(new PeriodicPurchaseInfo(recurringPurchaseId,
                PeriodicPurchaseInfo.State.CANCELLED), null, null, null);
    }

    public CreditCardReceipt queryRecurringPurchase(Long recurringPurchaseId) {
        if (recurringPurchaseId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "recurringPurchaseId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "recurringPurchase");
            this.appendOperationType(req, "query");
            this.appendTransactionId(req, recurringPurchaseId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /**
     * Executes the created recurring transaction
     * 
     * @param recurringPurchasId
     * @param cvv2
     */
    public CreditCardReceipt executeRecurringPurchase(Long recurringPurchaseId, String cvv2) {
        if (recurringPurchaseId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "recurringPurchaseId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "recurringPurchase");
            this.appendOperationType(req, "execute");
            if (cvv2 != null) {
                this.appendParam(req, "cvv2", cvv2);
            }
            this.appendTransactionId(req, recurringPurchaseId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.admeris.creditcard.api.CreditCardService#updateRecurringPurchase(
     * java.lang.Long, com.admeris.creditcard.api.CreditCard, java.lang.Long,
     * come.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt updateRecurringPurchase(Long recurringPurchaseId,
            CreditCard creditCard, Long perPaymentAmount, VerificationRequest verificationRequest) {
        return this.updateRecurringPurchaseHelper(new PeriodicPurchaseInfo(recurringPurchaseId,
                null, perPaymentAmount), creditCard, null, verificationRequest, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admer.creditcard.api.CreditCardService#updateRecurringPurchase(
     * java.lang.Long, java.lang.String, java.lang.Long,
     * com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt updateRecurringPurchase(Long recurringPurchaseId,
            String storageTokenId, Long perPaymentAmount, VerificationRequest verificationRequest) {
        return this.updateRecurringPurchaseHelper(new PeriodicPurchaseInfo(recurringPurchaseId,
                null, perPaymentAmount), null, storageTokenId, verificationRequest, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.admeris.creditcard.api.CreditCardService#updateRecurringPurchase(
     * com.admeris.creditcard.api.PeriodicPurchaseInfo,
     * com.admeris.creditcard.api.CreditCard, java.lang.String,
     * com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt updateRecurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest) {
        return this.updateRecurringPurchaseHelper(periodicPurchaseInfo, creditCard, storageTokenId,
                verificationRequest, null);
    }

    /**
     * Latest updateRecurringPurchase method
     * 
     * @param periodicPurchaseInfo
     * @param creditCard
     * @param storageTokenId
     * @param verificationRequest
     * @param purchaseCard
     * @return
     */
    public CreditCardReceipt updateRecurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest,
            PurchaseCardRequest purchaseCard) {
        return this.updateRecurringPurchaseHelper(periodicPurchaseInfo, creditCard, storageTokenId,
                verificationRequest, purchaseCard);
    }

    private CreditCardReceipt updateRecurringPurchaseHelper(
            PeriodicPurchaseInfo periodicPurchaseInfo, CreditCard creditCard,
            String storageTokenId, VerificationRequest verificationRequest,
            PurchaseCardRequest purchaseCard) {

        if (periodicPurchaseInfo.getPeriodicTransactionId() == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "recurringPurchaseId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "recurringPurchase");
            this.appendOperationType(req, "update");
            this.appendTransactionId(req, periodicPurchaseInfo.getPeriodicTransactionId());
            this.appendPeriodicPurchaseInfo(req, periodicPurchaseInfo);
            if (creditCard != null) {
                this.appendCreditCard(req, creditCard);
            }
            if (storageTokenId != null) {
                this.appendStorageTokenId(req, storageTokenId);
            }
            if (verificationRequest != null) {
                this.appendVerificationRequest(req, verificationRequest);
            }
            this.appendPurchaseCard(req, purchaseCard);

            // render indicator
            for (CreditCardIndicator indicator : super.indicatorList) {
                this.appendIndicator(req, indicator);
            }
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#refund(long,
     * java.lang.String, java.lang.String, long)
     */
    public CreditCardReceipt refund(long purchaseId, String purchaseOrderId, String refundOrderId,
            long amount) {
        if (purchaseOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "purchaseOrderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "refund");
            this.appendTransactionId(req, purchaseId);
            this.appendTransactionOrderId(req, purchaseOrderId);
            this.appendOrderId(req, refundOrderId);
            this.appendAmount(req, amount);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditccard.api.CreditCardService#preAuth(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt preAuth(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.preAuthHelper(orderId, creditCard, amount, verificationRequest, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService@preAuth(
     * java.lang.String, java.lang.String, long,
     * com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt preAuth(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest) {
        if (storageTokenId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.preAuthHelper(orderId, storageTokenId, amount, verificationRequest, false);
    }

    /*
     * Helper method for preAuth() to handle usage of real CC and storageTokenId
     * specifier
     */
    private CreditCardReceipt preAuthHelper(String orderId, Object creditCardSpecifier,
            long amount, VerificationRequest verificationRequest, boolean isActualCreditCard) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "preAuth");
            this.appendOrderId(req, orderId);
            // real CC, or using storage token
            if (isActualCreditCard) {
                this.appendCreditCard(req, (CreditCard) creditCardSpecifier);
            } else {
                this.appendStorageTokenId(req, creditCardSpecifier.toString());
            }
            this.appendAmount(req, amount);
            this.appendVerificationRequest(req, verificationRequest);

            // render indicator
            for (CreditCardIndicator indicator : super.indicatorList) {
                this.appendIndicator(req, indicator);
            }
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#capture( long,
     * java.lang.String, long)
     */
    public CreditCardReceipt capture(long purchaseId, String purchaseOrderId, long amount) {
        if (purchaseOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "purchaseOrderId is required", null);
        }
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "capture");
            this.appendTransactionId(req, purchaseId);
            this.appendTransactionOrderId(req, purchaseOrderId);
            this.appendAmount(req, amount);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt singleCredit(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.singleCreditHelper(orderId, creditCard, amount, verificationRequest, true);
    }

    public CreditCardReceipt singleCredit(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest) {
        if (storageTokenId == null || storageTokenId.length() <= 0) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.singleCreditHelper(orderId, storageTokenId, amount, verificationRequest, false);
    }

    /*
     * Helper method for singleCredit() to handle usage of real CC and
     * storageTokenId specifier.
     */
    private CreditCardReceipt singleCreditHelper(String orderId, Object creditCardSpecifier,
            long amount, VerificationRequest verificationRequest, boolean isActualCreditCard) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "singleCredit");
            this.appendOrderId(req, orderId);
            // real CC, or using storage token
            if (isActualCreditCard) {
                this.appendCreditCard(req, (CreditCard) creditCardSpecifier);
            } else {
                this.appendStorageTokenId(req, creditCardSpecifier.toString());
            }
            this.appendAmount(req, amount);
            this.appendVerificationRequest(req, verificationRequest);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#singlePurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * com.admeris.creditcard.api.VerificationRequest, java.lang.Integer)
     */
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.singlePurchaseHelper(orderId, creditCard, amount, verificationRequest, null,
                true, false, null);
    }

    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest, boolean addToStorage, String secureTokenId) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.singlePurchaseHelper(orderId, creditCard, amount, verificationRequest, null,
                true, addToStorage, secureTokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#singlePurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * com.admeris.creditcard.api.VerificationRequest,
     * com.admeris.creditcard.api.PurchaseCardRequest)
     */
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.singlePurchaseHelper(orderId, creditCard, amount, verificationRequest,
                purchaseCard, true, false, null);
    }

    // Make single purchase and add credit card to secure storage with
    // secureTokenId
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard,
            String secureTokenId) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.singlePurchaseHelper(orderId, creditCard, amount, verificationRequest,
                purchaseCard, true, true, secureTokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#singlePurchase(
     * java.lang.String, java.lang.String, long,
     * com.admeris.creditcard.api.VerificationRequest, java.lang.Integer)
     */
    public CreditCardReceipt singlePurchase(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest) {
        if (storageTokenId == null || storageTokenId.length() <= 0) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.singlePurchaseHelper(orderId, storageTokenId, amount, verificationRequest,
                null, false, false, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#singlePurchase(
     * java.lang.String, java.lang.String, long,
     * com.admeris.creditcard.api.VerificationRequest,
     * com.admeris.creditcard.api.PurchaseCardRequest)
     */
    public CreditCardReceipt singlePurchase(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard) {
        if (storageTokenId == null || storageTokenId.length() <= 0) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.singlePurchaseHelper(orderId, storageTokenId, amount, verificationRequest,
                purchaseCard, false, false, null);
    }

    /*
     * Helper method for singlePurchase() to handle usage of real CC and
     * storageTokenId specifier.
     */
    private CreditCardReceipt singlePurchaseHelper(String orderId, Object creditCardSpecifier,
            long amount, VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard,
            boolean isActualCreditCard, boolean addToStorage, String secureTokenId) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "singlePurchase");
            this.appendOrderId(req, orderId);
            // real CC, or using storage token
            if (isActualCreditCard) {
                if (addToStorage) {
                    this.appendStorageFlag(req, true);
                    this.appendStorageTokenId(req, secureTokenId);
                }

                this.appendCreditCard(req, (CreditCard) creditCardSpecifier);
            } else {
                this.appendStorageTokenId(req, creditCardSpecifier.toString());
            }
            this.appendAmount(req, amount);
            this.appendVerificationRequest(req, verificationRequest);
            this.appendPurchaseCard(req, purchaseCard);

            // render indicator
            for (CreditCardIndicator indicator : super.indicatorList) {
                this.appendIndicator(req, indicator);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#forcePurchase(
     * java.lang.String, com.admeris.creditcard.api.CreditCard, long,
     * java.lang.String, com.admeris.creditcard.api.VerificationRequest)
     */
    public CreditCardReceipt forcePurchase(String orderId, CreditCard creditCard, long amount,
            String approvalCode, VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.forcePurchaseHelper(orderId, creditCard, amount, approvalCode,
                verificationRequest, true);
    }

    /*
     * Helper method for singlePurchase() to handle usage of real CC and
     * storageTokenId specifier.
     */
    private CreditCardReceipt forcePurchaseHelper(String orderId, Object creditCardSpecifier,
            long amount, String approvalCode, VerificationRequest verificationRequest,
            boolean isActualCreditCard) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "force");
            this.appendOrderId(req, orderId);
            if (isActualCreditCard == true) {
                this.appendCreditCard(req, (CreditCard) creditCardSpecifier);
            } else {
                this.appendStorageTokenId(req, creditCardSpecifier.toString());
            }
            this.appendAmount(req, amount);
            this.appendApprovalCode(req, approvalCode);
            this.appendVerificationRequest(req, verificationRequest);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt reverseTransaction(long transactionId, String transactionOrderId) {
        if (transactionOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "transactionOrderId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "reversal");
            this.appendTransactionId(req, transactionId);
            this.appendTransactionOrderId(req, transactionOrderId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt lodgingCheckin(String orderId, CreditCard creditCard, long amount,
            LodgingRequest lodging, VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "lodging");
            this.appendOperationType(req, "checkin");
            this.appendOrderId(req, orderId);
            this.appendCreditCard(req, creditCard);
            this.appendAmount(req, amount);
            this.appendLodging(req, lodging);
            this.appendVerificationRequest(req, verificationRequest);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt lodgingIncremental(long transactionId, String orderId, long amount,
            LodgingRequest lodging) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }

        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "lodging");
            this.appendOperationType(req, "incremental");
            this.appendTransactionId(req, transactionId);
            this.appendOrderId(req, orderId);
            this.appendAmount(req, amount);
            this.appendLodging(req, lodging);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt lodgingCheckout(long transactionId, String orderId, long amount,
            LodgingRequest lodging) {
        if (orderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "orderId is required", null);
        }

        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "lodging");
            this.appendOperationType(req, "checkout");
            this.appendTransactionId(req, transactionId);
            this.appendAmount(req, amount);
            this.appendOrderId(req, orderId);
            this.appendLodging(req, lodging);

        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.admeris.creditcard.api.CreditCardService#verifyCreditCard(com.admeris
     * .creditcard.api.CreditCard,
     * com.admeris.creditcard.api.VerificationRequest, java.lang.Integer)
     */
    public CreditCardReceipt verifyCreditCard(CreditCard creditCard,
            VerificationRequest verificationRequest) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.verifyCreditCardHelper(creditCard, verificationRequest, true, false, null);
    }

    // Verify credit card and add credit card to secure storage with
    // secureTokenId
    public CreditCardReceipt verifyCreditCard(CreditCard creditCard,
            VerificationRequest verificationRequest, String secureTokenId) {
        if (creditCard == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "creditCard is required", null);
        }
        return this.verifyCreditCardHelper(creditCard, verificationRequest, true, true,
                secureTokenId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#verifyCreditCard(
     * java.lang.String, com.admeris.creditcard.api.VerificationRequest,
     * java.lang.Integer)
     */
    public CreditCardReceipt verifyCreditCard(String storageTokenId,
            VerificationRequest verificationRequest) {
        if (storageTokenId == null || storageTokenId.length() <= 0) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        return this.verifyCreditCardHelper(storageTokenId, verificationRequest, false, false, null);
    }

    /*
     * Helper method for verifyCreditCard() to handle usage of real CC and
     * storageTokenId specifier.
     */
    private CreditCardReceipt verifyCreditCardHelper(Object creditCardSpecifier,
            VerificationRequest verificationRequest, boolean isActualCreditCard,
            boolean addToStorage, String secureTokenId) {
        if (verificationRequest == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "verificationRequest is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "verifyCreditCard");
            if (isActualCreditCard) {
                if (addToStorage) {
                    this.appendStorageFlag(req, true);
                    this.appendStorageTokenId(req, secureTokenId);
                }

                this.appendCreditCard(req, (CreditCard) creditCardSpecifier);
            } else {
                this.appendStorageTokenId(req, creditCardSpecifier.toString());
            }
            this.appendVerificationRequest(req, verificationRequest);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.admeris.creditcard.api.CreditCardService#voidTransaction(long,
     * java.lang.String, java.lang.Integer)
     */
    public CreditCardReceipt voidTransaction(long transactionId, String transactionOrderId) {
        if (transactionOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "transactionOrderId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "void");
            this.appendTransactionId(req, transactionId);
            this.appendTransactionOrderId(req, transactionOrderId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt verifyTransaction(long transactionId, String transactionOrderId) {
        if (transactionOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "transactionOrderId is required",
                    null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "verifyTransaction");
            this.appendTransactionId(req, transactionId);
            this.appendTransactionOrderId(req, transactionOrderId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt verifyTransaction(long transactionId) {
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "verifyTransaction");
            this.appendTransactionId(req, transactionId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public CreditCardReceipt verifyTransaction(String transactionOrderId) {
        if (transactionOrderId == null) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, "transactionOrderId is required",
                    null);
        }
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "verifyTransaction");
            this.appendTransactionOrderId(req, transactionOrderId);
        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

    public StorageReceipt addToStorage(String storageTokenId, PaymentProfile paymentProfile) {
        if (paymentProfile == null) {
            return new StorageReceipt(REQ_INVALID_REQUEST, "paymentProfile is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "secureStorage");
            this.appendOperationType(req, "create");
            this.appendStorageTokenId(req, storageTokenId);
            this.appendPaymentProfile(req, paymentProfile);
        } catch (Exception e) {
            return new StorageReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendStorageRequest(req);
    }

    public StorageReceipt deleteFromStorage(String storageTokenId) {
        if (storageTokenId == null) {
            return new StorageReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "secureStorage");
            this.appendOperationType(req, "delete");
            this.appendStorageTokenId(req, storageTokenId);
        } catch (Exception e) {
            return new StorageReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendStorageRequest(req);
    }

    public StorageReceipt queryStorage(String storageTokenId) {
        if (storageTokenId == null) {
            return new StorageReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "secureStorage");
            this.appendOperationType(req, "query");
            this.appendStorageTokenId(req, storageTokenId);
        } catch (Exception e) {
            return new StorageReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendStorageRequest(req);
    }

    public StorageReceipt updateStorage(String storageTokenId, PaymentProfile paymentProfile) {
        if (storageTokenId == null) {
            return new StorageReceipt(REQ_INVALID_REQUEST, "storageTokenId is required", null);
        }
        if (paymentProfile == null) {
            return new StorageReceipt(REQ_INVALID_REQUEST, "paymentProfile is required", null);
        }
        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "secureStorage");
            this.appendOperationType(req, "update");
            this.appendStorageTokenId(req, storageTokenId);
            this.appendPaymentProfile(req, paymentProfile);
        } catch (Exception e) {
            return new StorageReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.sendStorageRequest(req);
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

    public CreditCardReceipt updateFraud(Long transactionId, String fraudSessionId, String auth) {

        // create the request string
        final StringBuilder req = new StringBuilder();
        try {
            this.appendHeader(req, "fraudUpdate");
            this.appendTransactionId(req, transactionId);
            this.appendParam(req, "fraudSessionId", fraudSessionId);
            this.appendParam(req, "auth", auth);

        } catch (Exception e) {
            return new CreditCardReceipt(REQ_INVALID_REQUEST, e.toString(), null);
        }
        return this.send(req);
    }

}
