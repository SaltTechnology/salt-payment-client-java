package com.salt.payment.client.creditcard.sample;

import java.util.Date;

import com.salt.payment.client.creditcard.api.AvsRequest;
import com.salt.payment.client.creditcard.api.CreditCard;
import com.salt.payment.client.creditcard.api.CreditCardReceipt;
import com.salt.payment.client.creditcard.api.CustomerProfile;
import com.salt.payment.client.creditcard.api.Cvv2Request;
import com.salt.payment.client.creditcard.api.HttpsCreditCardService;
import com.salt.payment.client.creditcard.api.Merchant;
import com.salt.payment.client.creditcard.api.PaymentProfile;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.ScheduleType;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.State;
import com.salt.payment.client.creditcard.api.StorageReceipt;
import com.salt.payment.client.creditcard.api.VerificationRequest;

/**
 * When a user is added to secure storage, we can retrieve their credit card/personal info from there
 *
 */
public class SecureStorageSample {

    final private static String URL = "https://test.salt.com/gateway/creditcard/processor.do";
    final private static String API_TOKEN = "";
    final private static int MERCHANT_ID = 300;
    final private static String STORAGE_TOKEN_ID = "yourtoken";

    private HttpsCreditCardService httpsCreditCardService;
    private CreditCard creditCard;

    public SecureStorageSample(){

        Merchant merchant = new Merchant(MERCHANT_ID, API_TOKEN);

        httpsCreditCardService = new HttpsCreditCardService(merchant, URL);
        creditCard = new CreditCard(4242424242424242L, (short)1231);

        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setFirstName("firstName");
        customerProfile.setLastName("lastName");
        customerProfile.setAddress1("address1");
        customerProfile.setPostal("postal");
        customerProfile.setPhoneNumber("phone");

        PaymentProfile paymentProfile = new PaymentProfile(creditCard, customerProfile);

        StorageReceipt receipt = httpsCreditCardService.addToStorage(STORAGE_TOKEN_ID, paymentProfile);
        
        if (receipt.isApproved()){
            //success
        }
        
        //make a purchase
        VerificationRequest verificationRequest = new VerificationRequest(AvsRequest.VERIFY_STREET_AND_ZIP, Cvv2Request.CVV2_PRESENT);
        CreditCardReceipt purchaseReceipt = httpsCreditCardService.singlePurchase("order id", STORAGE_TOKEN_ID, 1000, verificationRequest); //10 dollars
        System.out.println(purchaseReceipt.getResponse());
        
        //make a recurring purchase
        Schedule schedule = new Schedule(ScheduleType.DAY, (short)5);
        
        //periodic transaction ID is used only when repeating existing purchases, not applicable to new recurring purchases
        PeriodicPurchaseInfo info = new PeriodicPurchaseInfo(0L, State.NEW, schedule, 1000L, "order id", "customer id", new Date(), new Date(), new Date());
        
        //supply CreditCard or storage token ID - in this case we are not using secure storage so using CreditCard 
        httpsCreditCardService.recurringPurchase(info, null, STORAGE_TOKEN_ID, verificationRequest);
    }
}
