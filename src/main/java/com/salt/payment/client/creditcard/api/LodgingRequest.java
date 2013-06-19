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

import java.util.Date;

public class LodgingRequest {

    public enum Field {
        checkinTime("lodging_checkinTime"), checkoutTime("lodging_checkoutTime"), extraCharge(
                "lodging_extraCharge"), dayOfStay("lodging_dayOfStay"), roomNumber(
                "lodging_roomNumber"), roomRate("lodging_roomRate");

        private final String value;

        Field(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public enum Type {
        CHECKIN, INCREMENTAL, CHECKOUT;
    }

    public enum ExtraCharge {
        None(" "), Restaurant("1"), GiftShop("2"), MiniBar("3"), Telephone("4"), Laundry("5"), Other(
                "6");
        private final String value;

        ExtraCharge(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    private Type type;
    private Date checkinTime;
    private Date checkoutTime;
    private ExtraCharge extraCharge;
    private Integer dayOfStay = 0;
    private Integer roomNumber;
    private Long roomRate;

    public void setCheckin(Date checkinTime, int dayOfStay, ExtraCharge extraCharge,
            Integer roomNumber, Long roomRate) {
        type = Type.CHECKIN;
        this.checkinTime = checkinTime;
        this.dayOfStay = dayOfStay;
        this.extraCharge = extraCharge;
        this.roomNumber = roomNumber;
        this.roomRate = roomRate;
    }

    public void incremental(int incrementalStay) {
        this.dayOfStay = incrementalStay;
        type = Type.INCREMENTAL;
    }

    public void checkout(Date checkoutTime, ExtraCharge extraCharge) {
        this.checkoutTime = checkoutTime;
        this.extraCharge = extraCharge;
        type = Type.CHECKOUT;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(Date checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(Date checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public ExtraCharge getExtraCharge() {
        return extraCharge;
    }

    public void setExtraCharge(ExtraCharge extraCharge) {
        this.extraCharge = extraCharge;
    }

    public Integer getDayOfStay() {
        return dayOfStay;
    }

    public void setDayOfStay(Integer dayOfStay) {
        this.dayOfStay = dayOfStay;
    }

    public Long getRoomRate() {
        return roomRate;
    }

    public void setRoomRate(Long roomRate) {
        this.roomRate = roomRate;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }
}
