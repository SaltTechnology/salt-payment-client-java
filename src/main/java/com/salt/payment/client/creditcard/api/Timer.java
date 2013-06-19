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
 * A timer that is used to check whether a specified number of milliseconds has
 * passed.
 * 
 * @immutable
 * @since JSE5
 */
final class Timer {
    /**
     * Starts a new Timer and returns it.
     * 
     * @param timeoutMs
     *            the number of milliseconds after which the Timer will timeout.
     *            If negative, then the Timer would timeout immediately.
     * @return the Timer that has been started with the specified timeout time
     */
    public static Timer start(long timeoutMs) {
        return new Timer(timeoutMs);
    }

    private final long startedTime;
    private final long timeoutMs;

    /**
     * Creates a new Timer and starts it.
     * 
     * @param timeoutMs
     *            the number of milliseconds after which the Timer will timeout.
     *            If negative, then the Timer would timeout immediately.
     */
    private Timer(long timeoutMs) {
        this.startedTime = System.currentTimeMillis();
        this.timeoutMs = timeoutMs < 0 ? 0 : timeoutMs;
    }

    /**
     * @return the remaining number of milliseconds before timeout
     */
    public long getRemainingMs() {
        final long result = this.timeoutMs - (System.currentTimeMillis() - this.startedTime);
        return result < 0 ? 0 : result;
    }

    /**
     * Returns the remaining number of milliseconds before timeout. If the
     * remaining number of milliseconds is less than <code>minResult</code> then
     * <code>minResult</code> will be returned.
     * 
     * @param minResult
     *            the minimum value that this method can return
     * @return the remaining number of milliseconds
     */
    public long getRemainingMs(long minResult) {
        final long result = this.getRemainingMs();
        return result < minResult ? minResult : result;
    }

    /**
     * Returns the remaining time rounded to the nearest number of seconds
     * before timeout.
     * 
     * @return the remaining seconds
     */
    public long getRemainingSeconds() {
        return Math.round(this.getRemainingMs() / 1000.0);
    }

    /**
     * @return true if the timer has timedout
     */
    public boolean isTimedOut() {
        return this.getRemainingMs() <= 0;
    }
}
