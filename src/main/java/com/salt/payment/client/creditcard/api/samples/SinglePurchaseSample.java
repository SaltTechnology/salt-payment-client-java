package com.salt.payment.client.creditcard.api.samples;

import com.salt.payment.client.creditcard.api.AvsRequest;
import com.salt.payment.client.creditcard.api.CreditCard;
import com.salt.payment.client.creditcard.api.CreditCardReceipt;
import com.salt.payment.client.creditcard.api.Cvv2Request;
import com.salt.payment.client.creditcard.api.HttpsCreditCardService;
import com.salt.payment.client.creditcard.api.Merchant;
import com.salt.payment.client.creditcard.api.VerificationRequest;

/**
 * Performs a single purchase.
 *
 */
public final class SinglePurchaseSample {
	
	final private static String URL = "https://test.salt.com/gateway/creditcard/processor.do";
	final private static String API_TOKEN = "";
	final private static int MERCHANT_ID = 300;
	
	private HttpsCreditCardService httpsCreditCardService;
	private CreditCard creditCard;
	private VerificationRequest verificationRequest;
	
	//this ID is assigned to the order by the merchant
	private final String orderId = "orderid";
	//this ID is assing to a refund request by the merchant
	private final String refundOrderId = "refundid";
	
	private long transactionId = -1;
	
	public SinglePurchaseSample(){
		Merchant merchant = new Merchant(MERCHANT_ID, API_TOKEN);
		httpsCreditCardService = new HttpsCreditCardService(merchant, URL);
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