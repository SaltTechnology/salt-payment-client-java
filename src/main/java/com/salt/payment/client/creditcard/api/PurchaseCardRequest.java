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

import java.math.BigDecimal;

/**
 * Not used at this time.
 *
 */
public class PurchaseCardRequest {
    private String customerCode;
    private BigDecimal salesTax;
    private String invoice;
    private String tranCode;
    private String skuNumber;

    public PurchaseCardRequest(String customerCode, BigDecimal salesTax) {
        this.customerCode = customerCode;
        this.salesTax = salesTax;
    }

    /**
     * @return the invoice
     */
    public String getInvoice() {
        return invoice;
    }

    /**
     * @param invoice
     *            the invoice to set
     */
    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    /**
     * @return the tranCode
     */
    public String getTranCode() {
        return tranCode;
    }

    /**
     * @param tranCode
     *            the tranCode to set
     */
    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    /**
     * @return the skuNumber
     */
    public String getSkuNumber() {
        return skuNumber;
    }

    /**
     * @param skuNumber
     *            the skuNumber to set
     */
    public void setSkuNumber(String skuNumber) {
        this.skuNumber = skuNumber;
    }

    /**
     * @return the customerCode
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * @return the salesTax
     */
    public BigDecimal getSalesTax() {
        return salesTax;
    }
}
