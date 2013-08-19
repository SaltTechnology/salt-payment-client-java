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

import com.salt.payment.client.creditcard.api.PeriodicPurchaseInfo.Schedule;

import java.util.Date;

/**
 * The interface for performing creditcard requests. All requests will return a
 * receipt, which must be checked to see if the request was approved or if there
 * was an error.
 * 
 * @since JSE5
 */
public interface CreditCardService {
    // error codes
    public final static int REQ_MALFORMED_URL = -1;
    public final static int REQ_POST_ERROR = -2;
    public final static int REQ_RESPONSE_ERROR = -4;
    public final static int REQ_CONNECTION_FAILED = -5;
    public final static int REQ_INVALID_REQUEST = -6;

    /**
     * Issues a request for an installment purchase to be made by monthly
     * installments.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this request.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the installments. Not null.
     * @param perInstallmentAmount
     *            the amount of each installment in pennies. The currency is in
     *            the merchant's default currency.
     * @param startDate
     *            the date of when to issue the first installment. The
     *            installment may be issued at any time of the day. If this date
     *            is null or in the past, then the first installment will be
     *            issued immediately.
     * @param totalNumberInstallments
     *            the total number of installments of the purchase
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the installment purchase receipt
     */
    public CreditCardReceipt installmentPurchase(String orderId, CreditCard creditCard,
            long perInstallmentAmount, Date startDate, int totalNumberInstallments,
            VerificationRequest verificationRequest);

    /**
     * Issues a request for a recurring purchase to be made by the specified
     * schedule.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this
     *            transaction. Not null.
     * @param creditCard
     *            the creditcard used for making the payments. Not null.
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param startDate
     *            the date of when to issue the first payment. The payment may
     *            be issued at any time of the day. If this date is null or in
     *            the past, then the first payment will be issued immediately.
     * @param endDate
     *            the date of when to end the payments. The payment will not be
     *            issued if it falls on this date. If this date is null, then
     *            the payments will never end.
     * @param schedule
     *            the schedule on which to issue payments; if null then will
     *            default to a once-monthly schedule
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the recurring purchase receipt
     */
    public CreditCardReceipt recurringPurchase(String orderId, CreditCard creditCard,
            long perPaymentAmount, Date startDate, Date endDate, Schedule schedule,
            VerificationRequest verificationRequest);

    /**
     * Issues a request for a recurring purchase to be made by the specified
     * schedule.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this
     *            transaction. Not null.
     * @param storageTokenid
     *            specifies the stored credit card to be used. Not null.
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param startDate
     *            the date of when to issue the first payment. The payment may
     *            be issued at any time of the day. If this date is null or in
     *            the past, then the first payment will be issued immediately.
     * @param endDate
     *            the date of when to end the payments. The payment will not be
     *            issued if it falls on this date. If this date is null, then
     *            the payments will never end.
     * @param schedule
     *            the schedule on which to issue payments; if null then will
     *            default to a once-monthly schedule
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the recurring purchase receipt
     */
    public CreditCardReceipt recurringPurchase(String orderId, String storageTokenId,
            long perPaymentAmount, Date startDate, Date endDate, Schedule schedule,
            VerificationRequest verificationRequest);

    /**
     * Issues a request for a recurring purchase to be made by the specified PP
     * order definition.
     * 
     * @param periodicPurchaseInfo
     *            defines the Periodic Purchase params
     * @param creditCard
     *            the creditcard used for making the payments. Not null.
     * @param storageTokenid
     *            specifies the stored credit card to be used. Not null.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the recurring purchase receipt
     */
    public CreditCardReceipt recurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest);

    /**
     * Issues a request for a recurring purchase to be made by the specified PP
     * order definition.
     * 
     * @param periodicPurchaseInfo
     *            defines the Periodic Purchase params
     * @param creditCard
     *            the creditcard used for making the payments. Not null.
     * @param storageTokenid
     *            specifies the stored credit card to be used. Not null.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @param purchaseCard
     *            defines the Purchase Card params
     * @return the recurring purchase receipt
     */
    public CreditCardReceipt recurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest,
            PurchaseCardRequest purchaseCard);

    /**
     * Execute the specified recurring purchase.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to execute.
     * @param cvv2
     *            Optional. Verifies that the credit card is still valid upon
     *            execution of the recurring purchase.
     */
    public CreditCardReceipt executeRecurringPurchase(Long recurringPurchaseId, String cvv2);

    /**
     * Puts a recurring purchase on hold, if it is already on hold nothing will
     * happen.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to put on hold
     */
    public CreditCardReceipt holdRecurringPurchase(Long recurringPurchaseId);

    /**
     * Resumes a recurring purchase that was on hold, if it is not on hold
     * nothing will happen.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to resume
     */
    public CreditCardReceipt resumeRecurringPurchase(Long recurringPurchaseId);

    /**
     * Cancels a recurring purchase, if it is already cancelled or completed
     * nothing will happen.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to cancel
     */
    public CreditCardReceipt cancelRecurringPurchase(Long recurringPurchaseId);

    /**
     * Returns current information about the specified recurring purchase.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to query.
     */
    public CreditCardReceipt queryRecurringPurchase(Long recurringPurchaseId);

    /**
     * Updates the details of a recurring purchase.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to query.
     * @param creditCard
     *            the creditcard used for making the payments.
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     */
    public CreditCardReceipt updateRecurringPurchase(Long recurringPurchaseId,
            CreditCard creditCard, Long perPaymentAmount, VerificationRequest verificationRequest);

    /**
     * Updates the details of a recurring purchase.
     * 
     * @param recurringPurchaseId
     *            ID of recurring purchase to query.
     * @param storageTokenId
     *            specifies the stored creditcard used for making the payments.
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     */
    public CreditCardReceipt updateRecurringPurchase(Long recurringPurchaseId,
            String storageTokenId, Long perPaymentAmount, VerificationRequest verificationRequest);

    /**
     * Updates the details of a recurring purchase.
     * 
     * @param periodicPurchaseInfo
     *            describes the updates to the PP order to make
     * @param creditCard
     *            the creditcard used for making the payments. If provided,
     *            supersedes storageTokenId
     * @param storageTokenId
     *            specifies the stored creditcard used for making the payments.
     *            Superseded by creditCard
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     */
    public CreditCardReceipt updateRecurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest);

    /**
     * Updates the details of a recurring purchase.
     * 
     * @param periodicPurchaseInfo
     *            describes the updates to the PP order to make
     * @param creditCard
     *            the creditcard used for making the payments. If provided,
     *            supersedes storageTokenId
     * @param storageTokenId
     *            specifies the stored creditcard used for making the payments.
     *            Superseded by creditCard
     * @param perPaymentAmount
     *            the amount of each payment in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @param purchaseCard
     *            describes the updates made to the purchaseCard
     */
    public CreditCardReceipt updateRecurringPurchase(PeriodicPurchaseInfo periodicPurchaseInfo,
            CreditCard creditCard, String storageTokenId, VerificationRequest verificationRequest,
            PurchaseCardRequest purchaseCard);

    /**
     * Refunds the creditcard purchase with the specified
     * <code>purchaseId</code>.
     * 
     * @param purchaseId
     *            the id of the purchase to refund
     * @param purchaseOrderId
     *            the order id previously assigned to the purchase that is to be
     *            refunded. This is an extra check to prevent inadvertant
     *            refunding of a purchase. Not null.
     * @param refundOrderId
     *            the merchant assigned id to attached to this refund request.
     *            Will be generated if null.
     * @param amount
     *            the amount in pennies to refund. The currency is in the
     *            merchant's default currency.
     * @return the refund receipt
     */
    public CreditCardReceipt refund(long purchaseId, String purchaseOrderId, String refundOrderId,
            long amount);

    /**
     * Performs a pre authorization to verify the provided
     * <code>creditCard</code> of the specified <code>amount</code>.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     */
    public CreditCardReceipt preAuth(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest);

    /**
     * Performs a pre authorization to verify the provided
     * <code>storageTokenId</code> of the specified <code>amount</code>.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param storageTokenId
     *            the storageTokenId used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     */
    public CreditCardReceipt preAuth(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest);

    /**
     * Captures the funds after a transaction has been pre authorized.
     * 
     * @param purchaseId
     *            the Admeris transactionId that is associated with the pre
     *            authorized purchase.
     * @param purchaseOrderId
     *            the orderId previously assigned to the preAuth transaction
     *            that is to be captured. This is an extra check to provide in
     *            adverdent capturing of funds. Not null.
     */
    public CreditCardReceipt capture(long purchaseId, String purchaseOrderId, long amount);

    /**
     * Performs a single credit on the given <code>creditCard</code> of the
     * specified <code>amount</code>.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount to be credited in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the credit receipt
     */
    public CreditCardReceipt singleCredit(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest);

    /**
     * Performs a single credit on the given <code>storageTokenId</code> of the
     * specified <code>amount</code>.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param storageTokenId
     *            the tokenId used for making the credit. Not null.
     * @param amount
     *            the amount to be credited in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the credit receipt
     */
    public CreditCardReceipt singleCredit(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest);

    /**
     * Performs a single purchase on the given <code>creditCard</code> of the
     * specified <code>amount</code>. Combines the preAuth and capture into one
     * step
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the purchase receipt
     */
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest);

    /**
     * Performs a single purchase on the given <code>creditCard</code> of the
     * specified <code>amount</code>. Combines the preAuth and capture into one
     * step
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @param purchaseCard
     *            the purchase card details
     * @return the purchase receipt
     */
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard);

    /**
     * Performs a single purchase on the given <code>creditCard</code> of the
     * specified <code>amount</code>. Store credit card into secure storage with
     * <code>secureTokenId</code>.
     * 
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @param purchaseCard
     *            the purchase card details
     * @param secureTokenId
     *            secure storage token
     * @return the purchase receipt
     */
    public CreditCardReceipt singlePurchase(String orderId, CreditCard creditCard, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard,
            String secureTokenId);

    /**
     * Performs a single purchase on the given <code>storageTokenId</code> of
     * the specified <code>amount</code>. Combines the preAuth and capture into
     * one step
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param storageTokenId
     *            specifies the stored credit card used for making the purchase.
     *            Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the purchase receipt
     */
    public CreditCardReceipt singlePurchase(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest);

    /**
     * Performs a single purchase on the given <code>storageTokenId</code> of
     * the specified <code>amount</code>. Combines the preAuth and capture into
     * one step
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param storageTokenId
     *            specifies the stored credit card used for making the purchase.
     *            Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @param purchaseCard
     *            the purchase card details
     * @return the purchase receipt
     */
    public CreditCardReceipt singlePurchase(String orderId, String storageTokenId, long amount,
            VerificationRequest verificationRequest, PurchaseCardRequest purchaseCard);

    /**
     * This method will force a purchase transaction without verifying the
     * credit card.
     * 
     * @param orderId
     *            the merchant assigned orderId to associate with this purchase.
     *            Not null.
     * @param creditCard
     *            the creditcard used for making the purchase. Not null.
     * @param amount
     *            the amount of the purchase in pennies. The currency is in the
     *            merchant's default currency.
     * @param approvalCode
     * 
     * @param verificationRequest
     *            indicates how to verify the creditcard. If null, then no extra
     *            creditcard verification will be performed.
     * @return the purchase receipt
     */
    public CreditCardReceipt forcePurchase(String orderId, CreditCard creditCard, long amount,
            String approvalCode, VerificationRequest verificationRequest);

    /**
     * This method will provide the ability to perform a partial void.
     * 
     * @param transactionId
     *            Admeris generated transaction Id of the original transaction.
     * @param transactionOrderId
     *            the merchant assigned orderId of the the original transaction.
     *            Not null.
     */
    public CreditCardReceipt reverseTransaction(long transactionId, String transactionOrderId);

    /**
     * This method will only verify the <code>creditCard</code>. No purchases
     * will be made against the creditcard.
     * 
     * @param creditCard
     *            the creditcard to verify. Not null.
     * @param verificationRequest
     *            indicates how to verify the creditcard. Not null.
     * @return the creditcard verification receipt
     */
    public CreditCardReceipt verifyCreditCard(CreditCard creditCard,
            VerificationRequest verificationRequest);

    /**
     * This method will only verify the <code>creditCard</code>. No purchases
     * will be made against the creditcard. Store credit card into secure
     * storage with <code>secureTokenId</code>.
     * 
     * @param creditCard
     *            the creditcard to verify. Not null.
     * @param verificationRequest
     *            indicates how to verify the creditcard. Not null.
     * @param secureTokenId
     *            secure storage token
     * @return the creditcard verification receipt
     */
    public CreditCardReceipt verifyCreditCard(CreditCard creditCard,
            VerificationRequest verificationRequest, String secureTokenId);

    /**
     * This method will only verify the stored <code>creditCard</code>
     * associated with the <code>storageTokenid</code>. No purchase will be made
     * against the creditcard.
     * 
     * @param storageTokenid
     *            specifies the stored credit card to be verified. Not null.
     * @param verificationRequest
     *            indicates how to verify the creditcard. Not null. Note that
     *            you cannot perform CVV2 verification against a stored credit
     *            card, as CVV2 is never stored.
     * @return the creditcard verification receipt
     */
    public CreditCardReceipt verifyCreditCard(String storageTokenId,
            VerificationRequest verificationRequest);

    /**
     * Voids the transaction having the specified <code>transactionId</code>.
     * 
     * @param transactionId
     *            the id of the transaction to void
     * @param transactionOrderId
     *            the order id previously assigned to the transaction that is to
     *            be voided. This is an extra check to prevent inadvertant
     *            voiding of a transaction. Not null.
     * @return the void receipt
     */
    public CreditCardReceipt voidTransaction(long transactionId, String transactionOrderId);

    /**
     * Verifies the transaction having the specified <code>transactionId</code>.
     * 
     * @param transactionId
     *            the id of the transaction to verify
     * @param transactionOrderId
     *            the order id previously assigned to the transaction that is to
     *            be voided. This is an extra check.
     * @return the verification receipt
     */
    public CreditCardReceipt verifyTransaction(Long transactionId, String transactionOrderId);

    /**
     * Adds a payment profile to secure storage
     * 
     * @param storageTokenId
     *            storage token Id to use to identify the storage record. If
     *            null, then one will be auto-generated and returned in the
     *            receipt
     * @param paymentProfile
     *            the data to store
     * 
     * @return the result of the addition
     */
    public StorageReceipt addToStorage(String storageTokenId, PaymentProfile paymentProfile);

    /**
     * Permenantly deletes a payment profile from secure storage
     * 
     * @param storageTokenId
     *            storage token Id of the storage record to delete
     * 
     * @return the result of the deletion
     */
    public StorageReceipt deleteFromStorage(String storageTokenId);

    /**
     * Retrieves a payment profile from secure storage
     * 
     * @param storageTokenId
     *            storage token Id of the storage record to retrieve
     * 
     * @return the result of the retrieval
     */
    public StorageReceipt queryStorage(String storageTokenId);

    /**
     * Updates an existing payment profile in secure storage
     * 
     * @param storageTokenId
     *            storage token Id of the storage record to update
     * @param paymentProfile
     *            the data to store, will overwrite existing data
     * 
     * @return the result of the update
     */
    public StorageReceipt updateStorage(String storageTokenId, PaymentProfile paymentProfile);
}
