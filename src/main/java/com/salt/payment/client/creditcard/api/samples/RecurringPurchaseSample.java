package com.salt.payment.client.creditcard.api.samples;

import java.util.Date;

import com.salt.payment.client.creditcard.api.AvsRequest;
import com.salt.payment.client.creditcard.api.CreditCard;
import com.salt.payment.client.creditcard.api.CreditCardReceipt;
import com.salt.payment.client.creditcard.api.Cvv2Request;
import com.salt.payment.client.creditcard.api.HttpsCreditCardService;
import com.salt.payment.client.creditcard.api.Merchant;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.ScheduleType;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.State;
import com.salt.payment.client.creditcard.api.VerificationRequest;

/**
 * Recurring payments are useful when your customer is billed periodically, or when splitting payment into a number of separate payments.
 *
 */
public class RecurringPurchaseSample {

    final private static String URL = "https://test.salt.com/gateway/creditcard/processor.do";
    final private static String API_TOKEN = "";
    final private static int MERCHANT_ID = 300;

    private HttpsCreditCardService httpsCreditCardService;
    private CreditCard creditCard;
    private VerificationRequest verificationRequest;

    private final String orderId = "orderid";
    private long transactionId = -1;

    public RecurringPurchaseSample(){
        Merchant merchant = new Merchant(MERCHANT_ID, API_TOKEN);
        httpsCreditCardService = new HttpsCreditCardService(merchant, URL);
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
        }

        httpsCreditCardService.executeRecurringPurchase(transactionId, "cvv");

        httpsCreditCardService.holdRecurringPurchase(transactionId);

        httpsCreditCardService.resumeRecurringPurchase(transactionId);

        httpsCreditCardService.updateRecurringPurchase(info, creditCard, null, verificationRequest);

        httpsCreditCardService.cancelRecurringPurchase(transactionId);
    }

}
