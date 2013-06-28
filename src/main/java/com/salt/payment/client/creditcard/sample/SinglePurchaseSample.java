package com.salt.payment.client.creditcard.sample;

import com.salt.payment.client.creditcard.api.*;

/**
 * Performs a single purchase.
 *
 */
public class SinglePurchaseSample extends AbstractSample{
    //this ID is assing to a refund request by the merchant
    private final String refundOrderId = "refundid";

    private long transactionId = -1;


    public static void main(String[] args) {
        SinglePurchaseSample singlePurchaseSample = new SinglePurchaseSample();
        singlePurchaseSample.makeSinglePurchase();
    }

    public void makeSinglePurchase(){
        creditCard = new CreditCard(4242424242424242L, (short)1231);
        verificationRequest = new VerificationRequest(AvsRequest.VERIFY_STREET_AND_ZIP, Cvv2Request.CVV2_PRESENT);

        purchaseTransaction();

        closeBatch();
        updateFraud();
        verifyTransaction();
    }

    public void purchaseTransaction(){
        CreditCardReceipt receipt = httpsCreditCardService.singlePurchase(orderId, creditCard, 1000, verificationRequest); //10 dollars
        if (receipt != null && receipt.getTransactionId() != null){
            transactionId = receipt.getTransactionId();
        }
        System.out.println(receipt.getResponse());
    }

    public void voidTransaction(){
        httpsCreditCardService.voidTransaction(transactionId, orderId);
    }

    public void refundTransaction(){
        httpsCreditCardService.refund(transactionId, orderId, refundOrderId, 1000); //10 dollars
    }

    public void validateCard(){
        httpsCreditCardService.verifyCreditCard(creditCard, verificationRequest);
    }

    public void closeBatch(){
        httpsCreditCardService.closeBatch();
    }

    public void updateFraud(){
        httpsCreditCardService.updateFraud(transactionId, "fraud id", "auth");
    }

    public void verifyTransaction(){
        httpsCreditCardService.verifyTransaction(transactionId);
    }

}