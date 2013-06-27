package com.salt.payment.client.creditcard.sample;

import com.salt.payment.client.creditcard.api.*;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.ScheduleType;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.State;

import java.util.Date;

/**
 * Recurring payments are useful when your customer is billed periodically, or when splitting payment into a number of separate payments.
 *
 */
public class RecurringPurchaseSample extends AbstractSample {

    private long transactionId = -1;

    public static void main(String []args){
        System.out.println("Running Recuuring Purchase");
        com.salt.payment.client.creditcard.sample.RecurringPurchaseSample recurringPurchaseSample = new com.salt.payment.client.creditcard.sample.RecurringPurchaseSample();
    }

    public RecurringPurchaseSample(){

        retrieveMerchantKeys();

        Merchant merchant = new Merchant(merchantId, apiToken);
        httpsCreditCardService = new HttpsCreditCardService(merchant, url);
        creditCard = new CreditCard(4242424242424242L, (short)1231);

        verificationRequest = new VerificationRequest(AvsRequest.VERIFY_STREET_AND_ZIP, Cvv2Request.CVV2_PRESENT);

        sampleRecurringPurchaseTransaction();
    }

    public void sampleRecurringPurchaseTransaction(){
        Schedule schedule = new Schedule(ScheduleType.DAY, (short)5);

        //periodic transaction ID is used only when repeating existing purchases, not applicable to new recurring purchases
        PeriodicPurchaseInfo info = new PeriodicPurchaseInfo(0L, State.NEW, schedule, 1000L, orderId, "customer id", new Date(), new Date(), new Date());

        //supply CreditCard or storage token ID - in this case we are not using secure storage so using CreditCard
        CreditCardReceipt receipt = httpsCreditCardService.recurringPurchase(info, creditCard, null, verificationRequest);
        if (receipt != null && receipt.getTransactionId() != null){
            transactionId = receipt.getTransactionId();
            System.out.println(receipt.getResponse());
        }

        httpsCreditCardService.executeRecurringPurchase(transactionId, "cvv");

        httpsCreditCardService.holdRecurringPurchase(transactionId);

        httpsCreditCardService.resumeRecurringPurchase(transactionId);

        httpsCreditCardService.updateRecurringPurchase(info, creditCard, null, verificationRequest);

        httpsCreditCardService.cancelRecurringPurchase(transactionId);
    }

}
