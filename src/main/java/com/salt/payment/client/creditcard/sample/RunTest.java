package com.salt.payment.client.creditcard.sample;

/**
 * Just a main method to run the other Sample classes from.
 * These samples use our testsite, which uses a self signed cert.  If you run into an SSL 
 * error, you may need to use the included InstallCert.java in order to trust our cert.
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
