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

import static com.salt.payment.client.creditcard.api.CreditCardService.REQ_RESPONSE_ERROR;

import java.util.Date;

/**
 * The receipt returned from processing a debitcard request.
 * 
 * @since JSE5
 */
public class DebitCardReceipt extends AbstractReceipt {
    private ApprovalInfo approvalInfo = null;

    public DebitCardReceipt(Integer errorCode, String errorMessage, String debugMessage) {
        super(errorCode, errorMessage, debugMessage);
    }

    /**
     * Creates an instance by parsing the response from the gateway.
     * 
     * @param response
     *            the response from the gateway to parse. Not null.
     */
    @SuppressWarnings("unchecked")
    public DebitCardReceipt(String response) {
        super(response);
        if (response == null || response.length() <= 0) {
            // null response, null receipt
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage = "null or empty response received.";
            this.debugMessage = null;
            this.processedDateTime = new Date();
            return;
        }
        // parse the debit card specific parameters
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
            // TODO parse other debit card responses here

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

}
