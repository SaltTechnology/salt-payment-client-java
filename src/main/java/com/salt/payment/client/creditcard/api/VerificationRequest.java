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
 * A value object requesting that the {@link CreditCard} be verified by
 * indicating the creditcard elements, such as AVS and CVV2, that should be
 * verified.
 * 
 * @immutable
 * @since JSE5
 */
public class VerificationRequest {
    private final AvsRequest avsRequest;
    private final Cvv2Request cvv2Request;
    private final AdvancedRiskProfile advancedRiskProfile;

    /**
     * Creates an instance.
     * 
     * @param avsRequest
     *            indicates how to verify the address. If null, then ignore the
     *            address (street/zip) of the creditCard and don't verify it.
     * @param cvv2Request
     *            indicates how to verify the CVV2. If null, then ignore the
     *            CVV2 value of the creditCard and don't verify it.
     */
    public VerificationRequest(AvsRequest avsRequest, Cvv2Request cvv2Request) {
        this(avsRequest, cvv2Request, null);
    }

    /**
     * Creates an instance.
     * 
     * @param avsRequest
     *            indicates how to verify the address. If null, then ignore the
     *            address (street/zip) of the creditCard and don't verify it.
     * @param cvv2Request
     *            indicates how to verify the CVV2. If null, then ignore the
     *            CVV2 value of the creditCard and don't verify it.
     * @param advancedRiskProfile
     *            indicates how to verify the AdvancedRiskProfile. If null, then
     *            ignore the AdvancedRiskProfile specific fields and dont verify
     *            them.
     */
    public VerificationRequest(AvsRequest avsRequest, Cvv2Request cvv2Request,
            AdvancedRiskProfile advancedRiskProfile) {
        this.avsRequest = avsRequest;
        this.cvv2Request = cvv2Request;
        this.advancedRiskProfile = advancedRiskProfile;
    }

    /**
     * @return the {@link AvsRequest} or null if AVS is not enabled
     */
    public AvsRequest getAvsRequest() {
        return this.avsRequest;
    }

    /**
     * @return the {@link Cvv2Request} or null if CVV2 is not enabled
     */
    public Cvv2Request getCvv2Request() {
        return this.cvv2Request;
    }

    /**
     * @return the {@link AdvancedRiskProfile} or null if AdvancedRiskProfile is
     *         not enabled
     */
    public AdvancedRiskProfile getAdvancedRiskProfile() {
        return advancedRiskProfile;
    }

    public boolean isAvsEnabled() {
        return this.avsRequest != null;
    }

    public boolean isCvv2Enabled() {
        return this.cvv2Request != null;
    }

    public boolean isAdvancedRiskEnabled() {
        return this.advancedRiskProfile != null;
    }
}
