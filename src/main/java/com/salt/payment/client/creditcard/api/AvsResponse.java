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
 * A value object containing the results of AVS.
 * 
 * @Immutable
 * @since JSE5
 */
public class AvsResponse {
    private final String avsResponseCode;
    private final Boolean streetMatched;
    private final Boolean zipMatched;
    private final String zipType;
    private final String avsErrorCode;
    private final String avsErrorMessage;

    /**
     * Creates an instance.
     * 
     * @param avsResponseCode
     *            standard alphabetic response code
     * @param streetMatched
     *            whether or not the street matched
     * @param zipMatched
     *            whether or not the zip matched
     * @param zipType
     *            zip/postal code
     * @param avsErrorCode
     *            the avs error code, if any
     * @param avsErrorMessage
     *            the avs error message, if any
     */
    public AvsResponse(String avsResponseCode, Boolean streetMatched, Boolean zipMatched,
            String zipType, String avsErrorCode, String avsErrorMessage) {
        this.avsResponseCode = avsResponseCode;
        this.streetMatched = streetMatched;
        this.zipMatched = zipMatched;
        this.zipType = zipType;
        this.avsErrorCode = avsErrorCode;
        this.avsErrorMessage = avsErrorMessage;
    }

    /**
     * @return the standard alphabetic AVS response code
     */
    public String getAvsResponseCode() {
        return this.avsResponseCode;
    }

    /**
     * @return the errorCode that prevented AVS from being performed, or null if
     *         no error
     */
    public String getAvsErrorCode() {
        return this.avsErrorCode;
    }

    /**
     * @return the errorMessage that prevented AVS from being performed, or null
     *         if no error
     */
    public String getAvsErrorMessage() {
        return this.avsErrorMessage;
    }

    /**
     * @return the type of the zip, either zip or postal code, or null if there
     *         was an AVS error
     */
    public String getZipType() {
        return this.zipType;
    }

    /**
     * @return true if AVS was performed, false if there was an error that
     *         prevented AVS from being performed
     */
    public boolean isAvsPerformed() {
        return this.avsErrorCode == null && this.avsErrorMessage == null;
    }

    /**
     * @return true if the street format is valid, false if it's invalid because
     *         it's in the wrong format
     */
    public boolean isStreetFormatValid() {
        return this.streetMatched != null;
    }

    /**
     * @return true if the street format is valid and matches
     */
    public boolean isStreetFormatValidAndMatched() {
        return this.isStreetFormatValid() && this.streetMatched;
    }

    /**
     * @return true if the zip/postal code format is valid, false if it's
     *         invalid because it's in the wrong format
     */
    public boolean isZipFormatValid() {
        return this.zipMatched != null;
    }

    /**
     * @return true if the zip/postal code format is valid and matches
     */
    public boolean isZipFormatValidAndMatched() {
        return this.isZipFormatValid() && this.zipMatched;
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
        str.append("avsResponseCode=").append(this.avsResponseCode).append(",");
        str.append("streetMatched=").append(this.streetMatched).append(",");
        str.append("zipMatched=").append(this.zipMatched).append(",");
        str.append("zipType=").append(this.zipType).append(",");
        str.append("avsErrorCode=").append(this.avsErrorCode).append(",");
        str.append("avsErrorMessage=").append(this.avsErrorMessage).append("");
        str.append("]");
        return str.toString();
    }
}
