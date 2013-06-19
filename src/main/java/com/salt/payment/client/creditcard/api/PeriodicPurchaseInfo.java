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

/**
 * Periodic Purchase Info
 * 
 * 
 * @since JSE5
 */
public final class PeriodicPurchaseInfo {
    /** ScheduleType = monthly, weekly, daily etc */
    public static enum ScheduleType {
        MONTH(0), WEEK(1), DAY(2);

        public static ScheduleType fromCode(Short code) {
            if (code == null) {
                return null;
            }
            for (final ScheduleType s : ScheduleType.values()) {
                if (code.equals(s.toCode())) {
                    return s;
                }
            }
            throw new IllegalArgumentException(String.format(
                    "the code [%s] does not correspond to any ScheduleType", code));
        }

        private short code;

        private ScheduleType(int code) {
            this.code = (short) code;
        }

        public Short toCode() {
            return this.code;
        }
    }

    /** Schedule = type + interval length) */
    public static class Schedule {
        private ScheduleType scheduleType;
        private Short intervalLength;

        public Schedule(ScheduleType type, Short intervalLength) {
            this.scheduleType = type;
            this.intervalLength = intervalLength;
        }

        public ScheduleType getScheduleType() {
            return this.scheduleType;
        }

        public Short getIntervalLength() {
            return this.intervalLength;
        }
    }

    /**
     * State of the Periodic Purchase, not to be confused with state of any
     * associated transactions.
     */
    public static enum State {
        NEW(0), IN_PROGRESS(1), COMPLETE(2), ON_HOLD(3), CANCELLED(4), ERROR(5);

        public static State fromCode(Short code) {
            if (code == null) {
                return null;
            }
            for (final State s : State.values()) {
                if (code.equals(s.toCode())) {
                    return s;
                }
            }
            throw new IllegalArgumentException(String.format(
                    "the code [%s] does not correspond to any State", code));
        }

        private short code;

        private State(int code) {
            this.code = (short) code;
        }

        public Short toCode() {
            return this.code;
        }
    }

    public enum ExecutionType {
        MANUAL(1), AUTO(2);
        private final int value;

        ExecutionType(int value) {
            this.value = value;
        }

        public final int getValue() {
            return this.value;
        }
    }

    private Long periodicTransactionId = null;
    private Long lastPaymentId = null;
    private State state = null;
    private Schedule schedule = null;
    private Long perPaymentAmount = null;
    private String orderId = null;
    private String customerId = null;
    private Date startDate = null;
    private Date endDate = null;
    private Date nextPaymentDate = null;
    private ExecutionType executionType = null;
    /** 0 or empty = no limit **/
    private Integer installmentNumber = 0;

    // TODO add installment number

    public PeriodicPurchaseInfo() {

    }

    public PeriodicPurchaseInfo(Long periodicTransactionId, State state) {
        this.periodicTransactionId = periodicTransactionId;
        this.state = state;
    }

    public PeriodicPurchaseInfo(Long periodicTransactionId, State state, Date nextPaymentDate,
            Long lastPaymentId) {
        this.periodicTransactionId = periodicTransactionId;
        this.state = state;
        this.nextPaymentDate = nextPaymentDate;
        this.lastPaymentId = lastPaymentId;
    }

    public PeriodicPurchaseInfo(Long periodicTransactionId, State state, Long perPaymentAmount) {
        this.periodicTransactionId = periodicTransactionId;
        this.state = state;
        this.perPaymentAmount = perPaymentAmount;
    }

    public PeriodicPurchaseInfo(Long periodicTransactionId, State state, Schedule schedule,
            Long perPaymentAmount, String orderId, String customerId, Date startDate, Date endDate,
            Date nextPaymentDate) {
        this.periodicTransactionId = periodicTransactionId;
        this.state = state;
        this.schedule = schedule;
        this.perPaymentAmount = perPaymentAmount;
        this.orderId = orderId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nextPaymentDate = nextPaymentDate;
    }

    public Long getPeriodicTransactionId() {
        return this.periodicTransactionId;
    }

    public State getState() {
        return this.state;
    }

    public Schedule getSchedule() {
        return this.schedule;
    }

    public Long getPerPaymentAmount() {
        return this.perPaymentAmount;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public Date getNextPaymentDate() {
        return this.nextPaymentDate;
    }

    public Long getLastPaymentId() {
        return this.lastPaymentId;
    }

    public ExecutionType getExecutionType() {
        return executionType;
    }

    public Integer getInstallmentNumber() {
        return installmentNumber;
    }

    public void setExecutionType(ExecutionType executionType) {
        this.executionType = executionType;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder str = new StringBuilder();
        str.append("[");
        str.append("periodicTransactionId=").append(this.periodicTransactionId).append(",");
        str.append("state=").append(this.state).append(",");
        str.append("scheduleType=").append(this.schedule.getScheduleType()).append(",");
        str.append("scheduleLength=").append(this.schedule.getIntervalLength()).append(",");
        str.append("perPaymentAmount=").append(this.perPaymentAmount).append(",");
        str.append("orderId=").append(this.orderId).append(",");
        str.append("startDate=").append(this.startDate).append(",");
        str.append("endDate=").append(this.endDate).append(",");
        str.append("nextPaymentDate=").append(this.nextPaymentDate).append(",");
        str.append("lastPaymentId=").append(this.lastPaymentId);
        str.append("executionType=").append(this.executionType);
        str.append("]");
        return str.toString();
    }

    public void setPeriodicTransactionId(Long periodicTransactionId) {
        this.periodicTransactionId = periodicTransactionId;
    }

    public void setLastPaymentId(Long lastPaymentId) {
        this.lastPaymentId = lastPaymentId;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setPerPaymentAmount(Long perPaymentAmount) {
        this.perPaymentAmount = perPaymentAmount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNextPaymentDate(Date nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }
}
