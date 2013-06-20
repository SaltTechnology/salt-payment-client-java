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

import java.util.Hashtable;

/**
 * Returned after any call to the Gateway.  The applicable methods will depend on the type of call made
 *
 */
public class TransactionResponse {
    
    //the unformatted response data provided by the Gateway
    private String data;

    private String cardType = "";
    private double txnAmount = 0.0;
    private long txnId = 0;
    private String txnType = "";
    private String txnDate = "";
    private int respCode = -1;
    private String isoCode = "";
    private String authCode = "";
    private String message = "";
    private boolean isComplete = false;
    private boolean isTimeout = false;

    public TransactionResponse(String data) {
        this.data = data;
        this.format();
    }

    private void format() {
        Hashtable<String, String> ht = new Hashtable<String, String>();
        String param[] = this.data.trim().split(";");
        for (int i = 0; i < param.length; i++) {
            String key[] = param[i].split("=");
            if (key.length == 2) {
                ht.put(key[0], key[1]);
            }
        }
        // get response

        if (ht.containsKey("cardType")) {
            this.cardType = (String) ht.get("cardType");
        }
        if (ht.containsKey("txnId")) {
            try {
                this.txnId = Long.parseLong((String) ht.get("txnId"));
            } catch (NumberFormatException ex) {
                // If this happens txnId is a string --> will default to 0
            }
        }
        if (ht.containsKey("txnAmount")) {
            try {
                this.txnAmount = Double.parseDouble((String) ht.get("txnAmount"));
            } catch (NumberFormatException ex) {
                // If this happens, txnAmount is a string --> will default to
                // 0.0
            }
        }
        if (ht.containsKey("txnType")) {
            this.txnType = (String) ht.get("txnType");
        }
        if (ht.containsKey("txnDate")) {
            this.txnDate = (String) ht.get("txnDate");
        }
        if (ht.containsKey("respCode")) {
            try {
                this.respCode = Integer.parseInt((String) ht.get("respCode"));
            } catch (NumberFormatException ex) {
                // If this happens, respCode is a string --> will default to -1
            }
        }
        if (ht.containsKey("isoCode")) {
            this.isoCode = (String) ht.get("isoCode");
        }
        if (ht.containsKey("authCode")) {
            this.authCode = (String) ht.get("authCode");
        }
        if (ht.containsKey("message")) {
            this.message = (String) ht.get("message");
        }
        if (ht.containsKey("isComplete")) {
            this.isComplete = Boolean.parseBoolean((String) ht.get("isComplete"));
        }
        if (ht.containsKey("isTimeout")) {
            this.isTimeout = Boolean.parseBoolean((String) ht.get("isTimeout"));
        }
    }

    public String getRespData() {
        return this.data;
    }

    public String getCardType() {
        return this.cardType;
    }

    public double getTxnAmount() {
        return this.txnAmount;
    }

    public long getTxnId() {
        return this.txnId;
    }

    public String getTxnType() {
        return this.txnType;
    }

    public String getTxnDate() {
        return this.txnDate;
    }

    public int getRespCode() {
        return this.respCode;
    }

    public String getIsoCode() {
        return this.isoCode;
    }

    public String getAuthCode() {
        return this.authCode;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isTimeout() {
        return this.isTimeout;
    }

    public boolean isComplete() {
        return this.isComplete;
    }
}// end class
