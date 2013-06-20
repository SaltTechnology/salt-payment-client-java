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

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.salt.payment.client.creditcard.api.CreditCardService.*;

/**
 * The receipt returned from processing a creditcard request.
 * 
 * @since JSE5
 */
public class CreditCardReceipt extends AbstractReceipt {
    private ApprovalInfo approvalInfo = null;
    private AvsResponse avsResponse = null;
    private Cvv2Response cvv2Response = null;
    private PeriodicPurchaseInfo periodicPurchaseInfo = null;

    private String sanitizedCardNumber = null;
    private short storageTokenExpiryDate;
    private String responseHash = null;
    private Integer cardBrand = null;
    private String storageTokenId = null;

    private Integer fraudScore = null;
    private String fraudDecision = null;
    private String fraudSessionId = null;

    public CreditCardReceipt(Integer errorCode, String errorMessage, String debugMessage) {
        super(errorCode, errorMessage, debugMessage);
    }

    /**
     * Creates an instance by parsing the response from the gateway.
     * 
     * @param response
     *            the response from the gateway to parse. Not null.
     */
    public CreditCardReceipt(String response) {
        super(response);
        if (response == null || response.length() <= 0) {
            // null response, null receipt
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage = "null or empty response received.";
            this.debugMessage = null;
            this.processedDateTime = new Date();
            return;
        }
        // parse the CC-specific parameters
        try {
            // parse the approval info
            if (this.isApproved()) {
                this.approvalInfo =
                        new ApprovalInfo(this.parseLong("AUTHORIZED_AMOUNT"),
                                this.params.get("APPROVAL_CODE"),
                                this.parseInteger("TRACE_NUMBER"),
                                this.params.get("REFERENCE_NUMBER"));
            } else {
                this.approvalInfo = null;
            }
            // parse the AVS response
            final Boolean avsResponseAvailable = this.parseBoolean("AVS_RESPONSE_AVAILABLE");
            if (avsResponseAvailable != null && avsResponseAvailable) {
                this.avsResponse =
                        new AvsResponse(this.params.get("AVS_RESPONSE_CODE"),
                                this.parseBoolean("STREET_MATCHED"),
                                this.parseBoolean("ZIP_MATCHED"), this.params.get("ZIP_TYPE"),
                                this.params.get("AVS_ERROR_CODE"),
                                this.params.get("AVS_ERROR_MESSAGE"));
            } else {
                this.avsResponse = null;
            }
            // parse the CVV2 response
            final Boolean cvv2ResponseAvailable = this.parseBoolean("CVV2_RESPONSE_AVAILABLE");
            if (cvv2ResponseAvailable != null && cvv2ResponseAvailable) {
                this.cvv2Response =
                        new Cvv2Response(this.params.get("CVV2_RESPONSE_CODE"),
                                this.params.get("CVV2_RESPONSE_MESSAGE"));
            } else {
                this.cvv2Response = null;
            }
            // parse periodic purchase info
            final Long periodicPurchaseId = this.parseLong("PERIODIC_TRANSACTION_ID");
            if (periodicPurchaseId != null) {
                final PeriodicPurchaseInfo.State periodicPurchaseState =
                        PeriodicPurchaseInfo.State.fromCode(this
                                .parseShort("PERIODIC_TRANSACTION_STATE"));
                final String nextPaymentDateString = this.params.get("PERIODIC_NEXT_PAYMENT_DATE");
                Date nextPaymentDate = null;
                if (!Utils.isEmpty(nextPaymentDateString)) {
                    nextPaymentDate =
                            new SimpleDateFormat(AbstractCreditCardService.DATE_FORMAT)
                                    .parse(nextPaymentDateString);
                }
                final Long lastPaymentId =
                        this.params.get("PERIODIC_LAST_PAYMENT_ID") != null ? this
                                .parseLong("PERIODIC_LAST_PAYMENT_ID") : null;
                this.periodicPurchaseInfo =
                        new PeriodicPurchaseInfo(periodicPurchaseId, periodicPurchaseState,
                                nextPaymentDate, lastPaymentId);
            } else {
                this.periodicPurchaseInfo = null;
            }

            // Parse newely added fields
            sanitizedCardNumber = this.params.get("CARD_NUMBER");
            storageTokenExpiryDate = this.parseShort("STORAGE_TOKEN_EXPIRY");
            responseHash = this.params.get("RESPONSE_HASH");
            cardBrand = this.parseInteger("CARD_BRAND");
            storageTokenId = this.params.get("STORAGE_TOKEN_ID");

            fraudScore = this.parseInteger("FRAUD_SCORE");
            fraudDecision = this.params.get("FRAUD_DECISION");
            fraudSessionId = this.params.get("FRAUD_SESSION_ID");

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
     * @return the info upon approval
     */
    public ApprovalInfo getApprovalInfo() {
        return this.approvalInfo;
    }

    /**
     * @return the AVS response from processing the purchase, or null if AVS was
     *         not enabled or no response
     */
    public AvsResponse getAvsResponse() {
        return this.avsResponse;
    }

    /**
     * @return the CVV2 response from processing the purchase, or null if CVV2
     *         was not enabled or no response
     */
    public Cvv2Response getCvv2Response() {
        return this.cvv2Response;
    }

    /**
     * @return periodic purchase info (if applicable)
     */
    public PeriodicPurchaseInfo getPeriodicPurchaseInfo() {
        return this.periodicPurchaseInfo;
    }

    public String getSanitizedCardNumber() {
        return sanitizedCardNumber;
    }

    public short getStorageTokenExpiryDate() {
        return storageTokenExpiryDate;
    }

    public String getResponseHash() {
        return responseHash;
    }

    public Integer getCardBrand() {
        return cardBrand;
    }

    public String getStorageTokenId() {
        return storageTokenId;
    }

    public Integer getFraudScore() {
        return fraudScore;
    }

    public String getFraudDecision() {
        return fraudDecision;
    }

    public String getFraudSessionId() {
        return fraudSessionId;
    }
}
