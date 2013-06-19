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
 * The info upon approval.
 * 
 * @immutable
 * @since JSE5
 */
public final class ApprovalInfo {
    private final String approvalCode;
    private final String referenceNumber;
    private final Integer traceNumber;
    private final Long authorizedAmount;

    /**
     * Creates an instance.
     * 
     * @param authorizedAmount
     * @param approvalCode
     * @param traceNumber
     * @param referenceNumber
     * @throws IllegalArgumentException
     *             if approvalCode is null
     */
    public ApprovalInfo(Long authorizedAmount, String approvalCode, Integer traceNumber,
            String referenceNumber) {
        this.authorizedAmount = authorizedAmount;
        this.approvalCode = approvalCode;
        this.referenceNumber = referenceNumber;
        this.traceNumber = traceNumber;
    }

    public String getApprovalCode() {
        return this.approvalCode;
    }

    /**
     * @return the amount that was authorized upon approval
     */
    public long getAuthorizedAmount() {
        return this.authorizedAmount;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public Integer getTraceNumber() {
        return this.traceNumber;
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
        str.append("approvalCode=").append(this.approvalCode).append(",");
        str.append("referenceNumber=").append(this.referenceNumber).append(",");
        str.append("traceNumber=").append(this.traceNumber).append(",");
        str.append("authorizedAmount=").append(this.authorizedAmount);
        str.append("]");
        return str.toString();
    }
}
