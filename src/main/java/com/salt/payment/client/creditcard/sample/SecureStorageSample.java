package com.salt.payment.client.creditcard.sample;

import com.salt.payment.client.creditcard.api.*;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.ScheduleType;
import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.State;

import java.util.Date;

/**
 * When a user is added to secure storage, we can retrieve their credit card/personal info from there
 *
 */
public class SecureStorageSample extends AbstractSample{

    public static void main(String []args){
        SecureStorageSample secureStorageSample = new SecureStorageSample();
        secureStorageSample.makeSecureStorage();
    }

    public void makeSecureStorage(){
        creditCard = new CreditCard(4242424242424242L, (short)1231);

        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setFirstName("firstName");
        customerProfile.setLastName("lastName");
        customerProfile.setAddress1("address1");
        customerProfile.setPostal("postal");
        customerProfile.setPhoneNumber("phone");

        PaymentProfile paymentProfile = new PaymentProfile(creditCard, customerProfile);

        StorageReceipt receipt = httpsCreditCardService.addToStorage(storageTokenId, paymentProfile);

        if (receipt.isApproved()){
            //success
        }

        //make a purchase
        VerificationRequest verificationRequest = new VerificationRequest(AvsRequest.VERIFY_STREET_AND_ZIP, Cvv2Request.CVV2_PRESENT);
        CreditCardReceipt purchaseReceipt = httpsCreditCardService.singlePurchase("order id", storageTokenId, 1000, verificationRequest); //10 dollars
        System.out.println(purchaseReceipt.getResponse());

        //make a recurring purchase
        Schedule schedule = new Schedule(ScheduleType.DAY, (short)5);

        //periodic transaction ID is used only when repeating existing purchases, not applicable to new recurring purchases
        PeriodicPurchaseInfo info = new PeriodicPurchaseInfo(0L, State.NEW, schedule, 1000L, "order id", "customer id", new Date(), new Date(), new Date());

        //supply CreditCard or storage token ID - in this case we are not using secure storage so using CreditCard
        httpsCreditCardService.recurringPurchase(info, null, storageTokenId, verificationRequest);
    }
}
