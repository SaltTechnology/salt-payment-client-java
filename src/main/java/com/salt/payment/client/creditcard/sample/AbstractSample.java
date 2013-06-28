package com.salt.payment.client.creditcard.sample;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 6/27/13
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */


import com.salt.payment.client.creditcard.api.CreditCard;
import com.salt.payment.client.creditcard.api.HttpsCreditCardService;
import com.salt.payment.client.creditcard.api.Merchant;
import com.salt.payment.client.creditcard.api.VerificationRequest;

import java.lang.*;
import java.lang.Exception;import java.lang.IllegalStateException;import java.lang.Integer;import java.lang.String;import java.lang.System;import java.util.Properties;

public abstract class AbstractSample {
    protected String propertiesFile = "merchant.properties";

    protected String url;
    protected String apiToken;
    protected String storageTokenId;
    protected int merchantId;
    protected String refundOrderId;

    protected String orderId;

    protected HttpsCreditCardService httpsCreditCardService;
    protected CreditCard creditCard;
    protected VerificationRequest verificationRequest;

    public AbstractSample(){
        retrieveMerchantKeys();

        Merchant merchant = new Merchant(merchantId, apiToken);

        httpsCreditCardService = new HttpsCreditCardService(merchant, url);
    }

    protected void retrieveMerchantKeys() {
        Properties merchantProp = new Properties();
        try {
            merchantProp.load(getClass().getClassLoader().getResourceAsStream(propertiesFile));


            //get the property values
            apiToken = merchantProp.getProperty("merchant.apiToken");
            merchantId = Integer.parseInt(merchantProp.getProperty("merchant.Id"));
            storageTokenId = merchantProp.getProperty("merchant.storageToken");
            orderId = merchantProp.getProperty("merchant.orderId");
            url = merchantProp.getProperty("sold.gateway.ur");
            refundOrderId = merchantProp.getProperty("refund.OrderId");

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    protected void printMerchantKeys() {
        java.lang.System.out.println("apiToken: " + apiToken);
        System.out.println("merchantId: " + merchantId);
    }
}
