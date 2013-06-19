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
 * A creditcard for making creditcard purchases.
 * 
 * @immutable
 * @since JSE5
 */
public class CreditCard {
    private String cardHolderName;
    private final Long creditCardNumber;
    private final Short expiryDate;
    private final String magneticData;
    private final String cvv2;
    private final String street;
    private final String zip;
    private final String secureCode;

    /**
     * Creates a creditcard with keyed-in creditcard number and expiryDate.
     * 
     * @param creditCardNumber
     *            the keyed creditcard number
     * @param expiryDate
     *            the keyed expiry date
     */
    public CreditCard(long creditCardNumber, short expiryDate) {
        this(creditCardNumber, expiryDate, null, null, null, null);
    }

    /**
     * Creates a creditcard with keyed-in creditcard number and expiryDate.
     * Additional cvv2 and avs verification info are optional.
     * 
     * @param creditCardNumber
     *            the keyed creditcard number
     * @param expiryDate
     *            the keyed expiry date
     * @param cvv2
     *            the cvv2 on the creditcard
     * @param street
     *            the street of the creditcard holder
     * @param zip
     *            the zip of the creditcard holder
     */
    public CreditCard(long creditCardNumber, short expiryDate, String cvv2, String street,
            String zip) {
        this(creditCardNumber, expiryDate, cvv2, street, zip, null);
    }

    /**
     * Creates a creditcard with keyed-in creditcard number and expiryDate.
     * Additional cvv2 and avs verification and VBV/SecureCode are optional.
     * 
     * @param creditCardNumber
     *            the keyed creditcard number
     * @param expiryDate
     *            the keyed expiry date
     * @param cvv2
     *            the cvv2 on the creditcard
     * @param street
     *            the street of the creditcard holder
     * @param zip
     *            the zip of the creditcard holder
     * @param secureCode
     *            the optional Verified By Visa CAVV value or the MasterCard
     *            SecureCode value for cardholder authentication
     */
    private CreditCard(long creditCardNumber, short expiryDate, String cvv2, String street,
            String zip, String secureCode) {
        this.creditCardNumber = creditCardNumber;
        this.expiryDate = expiryDate;
        this.cvv2 = cvv2;
        this.street = street;
        this.zip = zip;
        this.magneticData = null;
        this.secureCode = secureCode;
    }

    /**
     * Creates a creditcard with swiped magneticData.
     * 
     * @param magneticData
     *            the magnetic data on the creditcard
     */
    public CreditCard(String magneticData) {
        this(magneticData, null, null, null, null);
    }

    /**
     * Creates a creditcard with swiped magneticData. Additional cvv2 and avs
     * verification info are optional.
     * 
     * @param magneticData
     *            the magnetic data on the creditcard. Not null.
     * @param cvv2
     *            the cvv2 on the creditcard
     * @param street
     *            the street of the creditcard holder
     * @param zip
     *            the zip of the creditcard holder
     */
    public CreditCard(String magneticData, String cvv2, String street, String zip) {
        this(magneticData, cvv2, street, zip, null);
    }

    /**
     * Creates a creditcard with swiped magneticData. Additional cvv2 and avs
     * verification, and VBV/SecureCode info are optional.
     * 
     * @param magneticData
     *            the magnetic data on the creditcard. Not null.
     * @param cvv2
     *            the cvv2 on the creditcard
     * @param street
     *            the street of the creditcard holder
     * @param zip
     *            the zip of the creditcard holder
     * @param secureCode
     *            the optional Verified By Visa CAVV value or the MasterCard
     *            SecureCode value for cardholder authentication
     */
    private CreditCard(String magneticData, String cvv2, String street, String zip,
            String secureCode) {
        if (magneticData == null) {
            throw new IllegalArgumentException("magneticData must not be null");
        }
        this.magneticData = magneticData;
        this.cvv2 = cvv2;
        this.street = street;
        this.zip = zip;
        this.creditCardNumber = null;
        this.expiryDate = null;
        this.secureCode = secureCode;
    }

    public Long getCreditCardName() {
        return this.creditCardNumber;
    }

    public Long getCreditCardNumber() {
        return this.creditCardNumber;
    }

    public String getCvv2() {
        return this.cvv2;
    }

    public Short getExpiryDate() {
        return this.expiryDate;
    }

    public String getMagneticData() {
        return this.magneticData;
    }

    /**
     * @return the optional Verified By Visa CAVV value or the MasterCard
     *         SecureCode value for cardholder authentication
     */
    public String getSecureCode() {
        return this.secureCode;
    }

    public String getStreet() {
        return this.street;
    }

    public String getZip() {
        return this.zip;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    /**
     * @return true if the creditcard pan was obtained by swiping, otherwise it
     *         was keyed in
     */
    public boolean isSwiped() {
        return this.magneticData != null;
    }

    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        if (this.creditCardNumber != null) {
            str.append("CREDIT_CARD_NUMBER").append("=").append(this.creditCardNumber).append("\n");
        }
        if (this.expiryDate != null) {
            str.append("EXPIRY_DATE").append("=").append(this.expiryDate).append("\n");
        }
        if (this.cvv2 != null) {
            str.append("CVV2").append("=").append(this.cvv2).append("\n");
        }
        if (this.street != null) {
            str.append("STREET").append("=").append(this.street).append("\n");
        }
        if (this.zip != null) {
            str.append("ZIP").append("=").append(this.zip).append("\n");
        }
        if (this.secureCode != null) {
            str.append("SECURE_CODE").append("=").append(this.secureCode).append("\n");
        }
        return str.toString();
    }
}
