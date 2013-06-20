package com.salt.payment.client.creditcard.api.samples;

/**
 * Just a main method to run the other Sample classes from.
 */
public class RunTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SinglePurchaseSample singlePurchaseSample = new SinglePurchaseSample();
        
        SecureStorageSample storageSample = new SecureStorageSample();
        
        RecurringPurchaseSample recurringPurchaseSample = new RecurringPurchaseSample();
    }

}
