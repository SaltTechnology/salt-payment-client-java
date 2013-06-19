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
 * Indicates that the request data may have already been processed, but there
 * was an error retrieving or interpreting the response. This is a fatal error
 * that must be handled by somehow rolling back the previous request (ie. void
 * the request) or, if overwriting is possible, issue the request again to
 * overwrite it.
 * 
 * @since JSE5
 */
public class ResponseProcessingException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 9058733640779633279L;

    /**
     * Creates a new instance.
     */
    public ResponseProcessingException() {
        super();
    }

    /**
     * Creates a new instance with an error message.
     */
    public ResponseProcessingException(String msg) {
        super(msg);
    }

    /**
     * Creates a new instance with an error message and a cause.
     */
    public ResponseProcessingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
