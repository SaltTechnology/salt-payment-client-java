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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import static com.salt.payment.client.creditcard.api.CreditCardService.*;

/**
 * The receipt returned from processing a gateway request.
 * 
 * @since JSE5
 */
public abstract class AbstractReceipt {
    protected Map<String, String> params = null;
    protected boolean approved = false;
    protected Long transactionId = null;
    protected String orderId = null;
    protected Date processedDateTime = null;
    protected String errorCode = null;
    protected String errorMessage = null;
    protected String debugMessage = null;
    protected String response = null;

    private AbstractReceipt() {
    }

    /**
     * Caller-side error constructor.
     * 
     * @param errorCode
     *            error Code
     * @param errorMessage
     *            error Message
     * @param debugMessage
     *            debug Message, if applicable
     */
    public AbstractReceipt(Integer errorCode, String errorMessage, String debugMessage) {
        // just set the error and date info, the rest is null
        this.approved = false;
        this.errorCode = errorCode != null ? errorCode.toString() : null;
        this.errorMessage = errorMessage;
        this.debugMessage = debugMessage;
        this.processedDateTime = new Date();

        // use this data to create an equivalent response
        StringBuilder str = new StringBuilder();
        str.append("APPROVED=").append(this.approved);
        str.append("\nERROR_CODE=").append(this.errorCode != null ? this.errorCode : "");
        str.append("\nERROR_MESSAGE=").append(this.errorMessage != null ? this.errorMessage : "");
        str.append("\nDEBUG_MESSAGE=").append(this.debugMessage != null ? this.debugMessage : "");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
        str.append("\nPROCESSED_DATE=").append(dateFormat.format(this.processedDateTime));
        str.append("\nPROCESSED_TIME=").append(timeFormat.format(this.processedDateTime));
        this.response = str.toString();
    }

    /**
     * Creates an instance by parsing the response from the gateway.
     * 
     * @param response
     *            the response from the gateway to parse. Not null.
     */
    @SuppressWarnings("unchecked")
    public AbstractReceipt(String response) {
        if (response == null || response.length() <= 0) {
            // null response, null receipt
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage = "null or empty response received.";
            this.debugMessage = null;
            this.processedDateTime = new Date();
            return;
        }
        this.response = response;
        final Properties p = new Properties();
        try {
            p.load(new ByteArrayInputStream(response.getBytes()));
        } catch (final IOException e) {
            // this should never occur because we're reading from a string
            // some sort of formatting error, maybe?
            this.errorCode = (new Integer(REQ_RESPONSE_ERROR)).toString();
            this.errorMessage = "could not parse response.";
            this.debugMessage = e.toString();
            this.processedDateTime = new Date();
            return;
        }
        this.params = new HashMap<String, String>();
        for (final Entry entry : p.entrySet()) {
            this.params.put((String) entry.getKey(), (String) entry.getValue());
        }
        // parse the parameters
        try {
            this.approved = this.parseBoolean("APPROVED");
            this.transactionId = this.parseLong("TRANSACTION_ID");
            this.orderId = this.params.get("ORDER_ID");
            final String processedDate = this.params.get("PROCESSED_DATE");
            final String processedTime = this.params.get("PROCESSED_TIME");
            if (!Utils.isEmpty(processedDate) && !Utils.isEmpty(processedTime)) {
                this.processedDateTime =
                        new SimpleDateFormat("yyMMddHHmmss").parse(processedDate + processedTime);
            } else {
                this.processedDateTime = null;
            }
            this.errorCode = this.params.get("ERROR_CODE");
            this.errorMessage = this.params.get("ERROR_MESSAGE");
            this.debugMessage = this.params.get("DEBUG_MESSAGE");
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
     * @return a message for the merchant to debug programming errors
     */
    public String getDebugMessage() {
        return this.debugMessage;
    }

    /**
     * @return the errorCode if the request was declined, or null if the request
     *         was approved
     */
    public String getErrorCode() {
        return this.errorCode;
    }

    /**
     * @return the errorMessage if the request was declined, or null if the
     *         request was approved
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * @return the merchant assigned orderId for a purchase or refund
     */
    public String getOrderId() {
        return this.orderId;
    }

    /**
     * @return the response parameters from the gateway
     */
    public Map<String, String> getParams() {
        return this.params;
    }

    /**
     * @return the copy of the datetime of when the request was processed
     */
    public Date getProcessedDateTime() {
        return this.processedDateTime;
    }

    /**
     * @return the actual response from the creditcard gateway
     */
    public String getResponse() {
        return this.response;
    }

    /**
     * @return the id of the purchase or refund transaction, or null if not
     *         created or not applicable to the request type
     */
    public Long getTransactionId() {
        return this.transactionId;
    }

    /**
     * Returns true if the request was approved, otherwise the request was
     * declined due to some error.
     * 
     * @return true if the request was approved, false if declined
     */
    public boolean isApproved() {
        return this.approved;
    }

    protected Boolean parseBoolean(String paramName) {
        final String value = this.params.get(paramName);
        return Utils.isEmpty(value) ? null : Boolean.valueOf(value);
    }

    protected Integer parseInteger(String paramName) {
        final String value = this.params.get(paramName);
        return Utils.isEmpty(value) ? null : Integer.valueOf(value);
    }

    protected Long parseLong(String paramName) {
        final String value = this.params.get(paramName);
        return Utils.isEmpty(value) ? null : Long.valueOf(value);
    }

    protected Short parseShort(String paramName) {
        final String value = this.params.get(paramName);
        return Utils.isEmpty(value) ? null : Short.valueOf(value);
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        if (this.params != null) {
            // will not be null only if a resp string was processed
            for (final Entry<String, String> entry : this.params.entrySet()) {
                if (str.length() != 0) {
                    str.append("\n");
                }
                str.append(entry.getKey()).append("=").append(entry.getValue());
            }
        } else {
            // otherwise, an error receipt
            str.append(this.response);
        }
        return str.toString();
    }
}
