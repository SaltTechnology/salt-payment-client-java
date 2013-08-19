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

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import static com.salt.payment.client.creditcard.api.CreditCardService.*;

/**
 * Sends an HTTPS POST request to the SALT POST API.
 * @since JSE5
 */
public abstract class AbstractCreditCardService {
    // --------------------------------------------------------------------------
    // Statics
    // --------------------------------------------------------------------------
    /** The default minimum timeout when reading the response is {@value} ms. */
    private static final int DEFAULT_MIN_RESPONSE_READ_TIMEOUT_MS = 30000;
    /**
     * The number of milliseconds on top of the specified request timeoutMs
     * before the connection will timeout. This is to ensure that there is
     * enough time for the request to process before timing the connection.
     */
    public static final int CONNECTION_TIMEOUT_MS_BUFFER = 60000;
    /** The format to use for sending dates (ISO 8601 format). */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /** Helper class used in receiving the server response */
    protected static final class SendResult {
        public String response;
        public Integer errorCode;
        public String errorMessage;
        public String debugMessage;

        public SendResult(String response, Integer errorCode, String errorMessage,
                String debugMessage) {
            this.response = response;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
            this.debugMessage = debugMessage;
        }
    }

    // --------------------------------------------------------------------------
    // Fields
    // --------------------------------------------------------------------------
    private final String url;
    private final boolean secured;
    protected ArrayList<CreditCardIndicator> indicatorList = new ArrayList<CreditCardIndicator>();

    /**
     * Creates an instance with the creditcard gateway url. Secured by default.
     * 
     * @param url
     *            the url to the creditcard gateway. Not null.
     */
    public AbstractCreditCardService(String url) {
        this.url = url;
        this.secured = true;
    }

    /**
     * Creates an instance with the creditcard gateway url.
     * 
     * @param url
     *            the url to the creditcard gateway. Not null.
     * @param secured
     *            true if using https, otherwise http is used
     */
    public AbstractCreditCardService(String url, boolean secured) {
        this.url = url;
        this.secured = secured;
    }

    /**
     * Called after receiving the response.
     * 
     * @param response
     *            the received response
     */
    protected void afterReceivingResponse(String response) {

    }

    protected void appendAmount(StringBuilder req, Long amount) {
        this.appendParam(req, "amount", amount);
    }

    protected void appendApiToken(StringBuilder req, String apiToken) {
        this.appendParam(req, "apiToken", apiToken);
    }

    protected void appendApprovalCode(StringBuilder req, String approvalCode) {
        this.appendParam(req, "approvalCode", approvalCode);
    }

    protected void appendCreditCard(StringBuilder req, CreditCard creditCard) {
        if (creditCard != null) {
            this.appendParam(req, "creditCardNumber", creditCard.getCreditCardNumber());
            this.appendParam(req, "expiryDate", creditCard.getExpiryDate());
            this.appendParam(req, "magneticData", creditCard.getMagneticData());
            this.appendParam(req, "cvv2", creditCard.getCvv2());
            this.appendParam(req, "street", creditCard.getStreet());
            this.appendParam(req, "zip", creditCard.getZip());
            this.appendParam(req, "secureCode", creditCard.getSecureCode());
            this.appendParam(req, "cardHolderName", creditCard.getCardHolderName());
        }
    }

    protected void appendDate(StringBuilder req, String name, Date date) {
        if (date != null) {
            this.appendParam(req, name, new SimpleDateFormat(DATE_FORMAT).format(date));
        }
    }

    protected void appendEndDate(StringBuilder req, Date endDate) {
        this.appendDate(req, "endDate", endDate);
    }

    protected void appendHeader(StringBuilder req, String requestCode) {
        if (requestCode == null) {
            throw new IllegalArgumentException("requestCode is required");
        }
        this.appendParam(req, "requestCode", requestCode);
    }

    protected void appendIndicator(StringBuilder req, CreditCardIndicator indicator) {
        this.appendParam(req, indicator.name(), "1");
    }

    protected void appendLodging(StringBuilder req, LodgingRequest lodging) {
        if (lodging != null) {
            if (lodging.getCheckinTime() != null) {
                this.appendParam(req, LodgingRequest.Field.checkinTime.getValue(),
                        new SimpleDateFormat(DATE_FORMAT).format(lodging.getCheckinTime()));
            }
            if (lodging.getCheckoutTime() != null) {
                this.appendParam(req, LodgingRequest.Field.checkoutTime.getValue(),
                        new SimpleDateFormat(DATE_FORMAT).format(lodging.getCheckoutTime()));
            }
            if (lodging.getDayOfStay() != null) {
                this.appendParam(req, LodgingRequest.Field.dayOfStay.getValue(),
                        lodging.getDayOfStay());
            }
            if (lodging.getExtraCharge() != null) {
                this.appendParam(req, LodgingRequest.Field.extraCharge.getValue(), lodging
                        .getExtraCharge().getValue());
            }
            if (lodging.getRoomNumber() != null) {
                this.appendParam(req, LodgingRequest.Field.roomNumber.getValue(),
                        lodging.getRoomNumber());
            }
            if (lodging.getRoomRate() != null) {
                this.appendParam(req, LodgingRequest.Field.roomRate.getValue(),
                        lodging.getRoomRate());
            }
        }
    }

    protected void appendOperationType(StringBuilder req, String type) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        this.appendParam(req, "operationCode", type);
    }

    protected void appendPeriodicPurchaseState(StringBuilder req, PeriodicPurchaseInfo.State state) {
        if (state == null) {
            throw new IllegalArgumentException("state is required");
        }
        this.appendParam(req, "periodicPurchaseStateCode", state.toCode());
    }

    protected void appendPeriodicPurchaseSchedule(StringBuilder req,
            PeriodicPurchaseInfo.Schedule schedule) {
        if (schedule == null) {
            throw new IllegalArgumentException("schedule is required");
        }
        this.appendParam(req, "periodicPurchaseScheduleTypeCode", schedule.getScheduleType()
                .toCode());
        this.appendParam(req, "periodicPurchaseIntervalLength", schedule.getIntervalLength());
    }

    protected void appendPeriodicPurchaseInfo(StringBuilder req,
            PeriodicPurchaseInfo periodicPurchaseInfo) {
        if (periodicPurchaseInfo.getPerPaymentAmount() != null) {
            this.appendAmount(req, periodicPurchaseInfo.getPerPaymentAmount());
        }
        if (periodicPurchaseInfo.getState() != null) {
            this.appendPeriodicPurchaseState(req, periodicPurchaseInfo.getState());
        }
        if (periodicPurchaseInfo.getSchedule() != null) {
            this.appendPeriodicPurchaseSchedule(req, periodicPurchaseInfo.getSchedule());
        }
        if (periodicPurchaseInfo.getOrderId() != null) {
            this.appendOrderId(req, periodicPurchaseInfo.getOrderId());
        }
        if (periodicPurchaseInfo.getCustomerId() != null) {
            this.appendParam(req, "customerId", periodicPurchaseInfo.getCustomerId());
        }
        if (periodicPurchaseInfo.getStartDate() != null) {
            this.appendStartDate(req, periodicPurchaseInfo.getStartDate());
        }
        if (periodicPurchaseInfo.getEndDate() != null) {
            this.appendEndDate(req, periodicPurchaseInfo.getEndDate());
        }
        if (periodicPurchaseInfo.getNextPaymentDate() != null) {
            this.appendDate(req, "nextPaymentDate", periodicPurchaseInfo.getNextPaymentDate());
        }
        if (periodicPurchaseInfo.getLastPaymentId() != null) {
            this.appendParam(req, "lastPaymentId", periodicPurchaseInfo.getLastPaymentId());
        }
        if (periodicPurchaseInfo.getExecutionType() != null) {
            this.appendParam(req, "periodicPurchaseExecutionType", periodicPurchaseInfo
                    .getExecutionType().getValue());
        }
    }

    protected void appendPurchaseCard(StringBuilder req, PurchaseCardRequest purchaseCard) {
        if (purchaseCard != null) {
            this.appendParam(req, "pcii_indicator", "true");
            this.appendParam(req, "pcii_customerCode", purchaseCard.getCustomerCode());
            this.appendParam(req, "pcii_salesTax", purchaseCard.getSalesTax().toString());
            if (purchaseCard.getInvoice() != null) {
                this.appendParam(req, "pcii_invoice", purchaseCard.getInvoice());
            }
            if (purchaseCard.getSkuNumber() != null) {
                this.appendParam(req, "pcii_skuNumber", purchaseCard.getSkuNumber());
            }
            if (purchaseCard.getTranCode() != null) {
                this.appendParam(req, "pcii_tranCode", purchaseCard.getTranCode());
            }
        }
    }

    protected void appendMerchantId(StringBuilder req, Integer merchantId) {
        this.appendParam(req, "merchantId", merchantId);
    }

    protected void appendMerchantId(StringBuilder req, String merchantId) {
        this.appendParam(req, "merchantId", merchantId);
    }

    protected void appendOrderId(StringBuilder req, String orderId) {
        this.appendParam(req, "orderId", orderId);
    }

    protected void appendStorageFlag(StringBuilder req, boolean b) {
        this.appendParam(req, "addToStorage", b);
    }

    protected void appendParam(StringBuilder str, String name, Object value) {
        if (str == null) {
            throw new IllegalArgumentException("str is required");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        if (value != null) {
            if (str.length() != 0) {
                str.append("&");
            }
            str.append(name).append("=").append(value);
        }
    }

    protected void appendPaymentProfile(StringBuilder req, PaymentProfile paymentProfile) {
        if (paymentProfile == null) {
            return;
        } else {
            if (paymentProfile.getCreditCard() != null) {
                this.appendCreditCard(req, paymentProfile.getCreditCard());
            }
            if (paymentProfile.getCustomerProfile() != null) {
                this.appendParam(req, "profileLegalName", paymentProfile.getCustomerProfile()
                        .getLegalName());
                this.appendParam(req, "profileTradeName", paymentProfile.getCustomerProfile()
                        .getTradeName());
                this.appendParam(req, "profileEmail", paymentProfile.getCustomerProfile()
                        .getWebsite());
                this.appendParam(req, "profileFirstName", paymentProfile.getCustomerProfile()
                        .getFirstName());
                this.appendParam(req, "profileLastName", paymentProfile.getCustomerProfile()
                        .getLastName());
                this.appendParam(req, "profilePhoneNumber", paymentProfile.getCustomerProfile()
                        .getPhoneNumber());
                this.appendParam(req, "profileFaxNumber", paymentProfile.getCustomerProfile()
                        .getFaxNumber());
                this.appendParam(req, "profileAddress1", paymentProfile.getCustomerProfile()
                        .getAddress1());
                this.appendParam(req, "profileAddress2", paymentProfile.getCustomerProfile()
                        .getAddress2());
                this.appendParam(req, "profileCity", paymentProfile.getCustomerProfile().getCity());
                this.appendParam(req, "profileProvince", paymentProfile.getCustomerProfile()
                        .getProvince());
                this.appendParam(req, "profilePostal", paymentProfile.getCustomerProfile()
                        .getPostal());
                this.appendParam(req, "profileCountry", paymentProfile.getCustomerProfile()
                        .getCountry());
            }
        }
    }

    protected void appendAdvancedRisk(StringBuilder req, AdvancedRiskProfile advancedRiskProfile) {
        if (advancedRiskProfile == null) {
            return;
        } else {
            this.appendParam(req, "verifier", AdvancedRiskProfile.HANDLE);
            this.appendParam(req, "customerDomain", advancedRiskProfile.getEmailDomain());
            this.appendParam(req, "customerUsernameHash", advancedRiskProfile.getUsernameHash());
            this.appendParam(req, "customerPasswordHash", advancedRiskProfile.getPasswordHash());
            this.appendParam(req, "customerEmailHash", advancedRiskProfile.getEmailHash());
            this.appendParam(req, "customerPhone", advancedRiskProfile.getPhone());
            this.appendParam(req, "customerName", advancedRiskProfile.getCustomerName());
            this.appendParam(req, "customerAnid", advancedRiskProfile.getAnid());
            this.appendParam(req, "customerGender", advancedRiskProfile.getGender());
            this.appendParam(req, "customerDriverLicense", advancedRiskProfile.getDriverLicense());
            this.appendParam(req, "customerUniqueId", advancedRiskProfile.getUniqueId());
            this.appendParam(req, "fraudSessionId", advancedRiskProfile.getFraudSessionId());
            this.appendParam(req, "transactionSource", advancedRiskProfile.getTransactionSource());

            this.appendParam(req, "billingPhone", advancedRiskProfile.getBillingPhone());
            this.appendParam(req, "shippingPhone", advancedRiskProfile.getShippingPhone());
            this.appendParam(req, "shippingName", advancedRiskProfile.getShippingName());
            this.appendParam(req, "shippingType", advancedRiskProfile.getShippingType());

            this.appendParam(req, "billingAddress", advancedRiskProfile.getBillingAddress());
            this.appendParam(req, "billingCity", advancedRiskProfile.getBillingCity());
            this.appendParam(req, "billingProvince", advancedRiskProfile.getBillingProvince());
            this.appendParam(req, "billingPostal", advancedRiskProfile.getBillingPostal());
            this.appendParam(req, "billingCountry", advancedRiskProfile.getBillingCountry());
            this.appendParam(req, "shippingAddressLine", advancedRiskProfile.getShippingAddress());
            this.appendParam(req, "shippingCity", advancedRiskProfile.getShippingCity());
            this.appendParam(req, "shippingProvince", advancedRiskProfile.getShippingProvince());
            this.appendParam(req, "shippingPostal", advancedRiskProfile.getShippingPostal());
            this.appendParam(req, "shippingCountry", advancedRiskProfile.getShippingCountry());
            this.appendParam(req, "browserUserAgent", advancedRiskProfile.getUserAgent());
            this.appendParam(req, "browserLanguage", advancedRiskProfile.getAcceptedLanguages());
            this.appendParam(req, "networkIP", advancedRiskProfile.getIP());
            this.appendParam(req, "networkProxiedIP", advancedRiskProfile.getProxiedIP());
            this.appendParam(req, "issuerBIN", advancedRiskProfile.getIssuerBin());
            this.appendParam(req, "issuerName", advancedRiskProfile.getIssuerName());
            this.appendParam(req, "issuerPhone", advancedRiskProfile.getIssuerPhone());
        }
    }

    protected void appendStartDate(StringBuilder req, Date startDate) {
        this.appendDate(req, "startDate", startDate);
    }

    protected void appendStorageTokenId(StringBuilder req, String storageTokenId) {
        if (storageTokenId != null) {
            this.appendParam(req, "storageTokenId", storageTokenId);
        }
    }

    protected void appendTotalNumberInstallments(StringBuilder req, Integer totalNumberInstallments) {
        this.appendParam(req, "totalNumberInstallments", totalNumberInstallments);
    }

    protected void appendTransactionId(StringBuilder req, Long transactionId) {
        this.appendParam(req, "transactionId", transactionId);
    }

    protected void appendTransactionOrderId(StringBuilder req, String transactionOrderId) {
        this.appendParam(req, "transactionOrderId", transactionOrderId);
    }

    protected void appendVerificationRequest(StringBuilder req, VerificationRequest vr) {
        if (vr != null) {
            this.appendParam(req, "avsRequestCode", vr.isAvsEnabled() ? vr.getAvsRequest().toCode()
                    : null);
            this.appendParam(req, "cvv2RequestCode", vr.isCvv2Enabled() ? vr.getCvv2Request()
                    .toCode() : null);
            this.appendAdvancedRisk(req, vr.isAdvancedRiskEnabled() ? vr.getAdvancedRiskProfile()
                    : null);
        }
    }

    /**
     * Called before sending the request.
     * 
     * @param request
     *            the request to send
     */
    protected void beforeSendingRequest(String request) {

    }

    /**
     * @return The number of milliseconds on top of the specified request
     *         timeoutMs before the connection will timeout. This is to ensure
     *         that there is enough time for the request to process before
     *         timing the connection.
     */
    protected int getConnectionTimeoutMsBuffer() {
        return CONNECTION_TIMEOUT_MS_BUFFER;
    }

    /**
     * @return the minimum timeout when reading the response
     */
    protected int getMinResponseReadTimeoutMs() {
        return DEFAULT_MIN_RESPONSE_READ_TIMEOUT_MS;
    }

    private String getSupportedProtocol() {
        return this.secured ? "https" : "http";
    }

    /**
     * @return the url of the creditcard gateway
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @return true if using https, otherwise http is used
     */
    public boolean isSecured() {
        return this.secured;
    }

    /**
     * Sends the <code>request</code> to the creditcard gateway and returns
     * receipt.
     * 
     * @param request
     *            the request to send
     * @return the receipt of the request, containing details as to whether or
     *         not the request was approved
     */
    protected CreditCardReceipt send(StringBuilder request) {
        CreditCardReceipt receipt = null;
        SendResult result = this.doSend(request);
        if (result != null) { 
            if (result.errorCode != null) {
                receipt =
                        new CreditCardReceipt(result.errorCode, result.errorMessage,
                                result.debugMessage);
            } else {
                receipt = new CreditCardReceipt(result.response);
            }
        }
        return receipt;
    }

    protected DebitCardReceipt sendDebit(StringBuilder request) {
        DebitCardReceipt receipt = null;
        SendResult result = this.doSend(request);
        if (result != null) {
            if (result.errorCode != null) {
                receipt =
                        new DebitCardReceipt(result.errorCode, result.errorMessage,
                                result.debugMessage);
            } else {
                receipt = new DebitCardReceipt(result.response);
            }
        }
        return receipt;
    }

    protected StorageReceipt sendStorageRequest(StringBuilder request) {
        StorageReceipt receipt = null;
        SendResult result = this.doSend(request);
        if (result != null) {
            if (result.errorCode != null) {
                receipt =
                        new StorageReceipt(result.errorCode, result.errorMessage,
                                result.debugMessage);
            } else {
                receipt = new StorageReceipt(result.response);
            }
        }
        return receipt;
    }

    protected SendResult doSend(StringBuilder request) {
        if (request == null) {
            return new SendResult(null, REQ_INVALID_REQUEST, "a request string is required", null);
        }
        // expect: total txn time within CONNECTION_TIMEOUT_MS_BUFFER
        final Timer timer = Timer.start(this.getConnectionTimeoutMsBuffer());
        if (timer.isTimedOut()) {
            return new SendResult(null, REQ_CONNECTION_FAILED,
                    "timed out while connecting to the credit card gateway.", null);
        }

        final URL ccUrl;
        // make sure the url is using a valid protocol
        try {
            ccUrl = new URL(this.url);
        } catch (final MalformedURLException e) {
            return new SendResult(null, REQ_MALFORMED_URL, String.format(
                    "the protocol of the specified url [%s] is invalid", this.url), e.toString());
        }
        // make sure the url is using a supported protocol
        if (!ccUrl.getProtocol().equals(this.getSupportedProtocol())) {
            System.out.println("protocol not supported");
            return new SendResult(null, REQ_CONNECTION_FAILED, String.format(
                    "the protocol [%s] is not supported", ccUrl.getProtocol()), null);
        }

        URLConnection c = null;
        try {
            // open the connection
            c = ccUrl.openConnection();
            // allow at least DEFAULT_MIN_RESPONSE_READ_TIMEOUT_MS to connect
            final int remainingTimeMsBeforeSend =
                    (int) timer.getRemainingMs(this.getMinResponseReadTimeoutMs());
            c.setConnectTimeout(remainingTimeMsBeforeSend);
            c.setDoOutput(true);
            if (this.secured) {
                ((HttpsURLConnection) c).setRequestMethod("POST");
            } else {
                ((HttpURLConnection) c).setRequestMethod("POST");
            }

            try {
                final String reqStr = request.toString();
                this.beforeSendingRequest(reqStr);
                // send the request
                Utils.copy(reqStr, new OutputStreamWriter(c.getOutputStream()));
            } catch (final Exception e) {
                return new SendResult(null, REQ_POST_ERROR,
                        "error attempting to send POST request", e.toString());
            }

            // get the response
            try {
                // allow at least DEFAULT_MIN_RESPONSE_READ_TIMEOUT_MS for
                // response
                final int remainingTimeMsAfterSend =
                        (int) timer.getRemainingMs(this.getMinResponseReadTimeoutMs());
                c.setReadTimeout(remainingTimeMsAfterSend);
                final String respStr =
                        Utils.copyToString(new InputStreamReader(c.getInputStream()));
                this.afterReceivingResponse(respStr);
                return new SendResult(respStr, null, null, null);
            } catch (final Exception e) {
                e.printStackTrace();
                return new SendResult(null, REQ_RESPONSE_ERROR,
                        "error receiving response after request was sent", e.toString());
            }
        } catch (Exception e) {
            return new SendResult(null, REQ_CONNECTION_FAILED,
                    "error connecting to the credit card gateway.", e.toString());
        } finally {
            if (c != null) {
                if (this.secured) {
                    ((HttpsURLConnection) c).disconnect();
                } else {
                    ((HttpURLConnection) c).disconnect();
                }
            }
        }
    }
}
