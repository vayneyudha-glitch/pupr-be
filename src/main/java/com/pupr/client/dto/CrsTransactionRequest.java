package com.pupr.client.dto;

import java.math.BigDecimal;

/**
 * Request payload for creating a budget transaction in PUPR-CRS.
 * Maps to the BudgetTransactionController's transaction creation endpoint.
 */
public class CrsTransactionRequest {

    private String bapId;
    private String bapNo;
    private String masterTransactionId;
    private BigDecimal amount;
    private String description;
    private String transactionDate;

    public CrsTransactionRequest() {
    }

    public String getBapId() {
        return bapId;
    }

    public void setBapId(String bapId) {
        this.bapId = bapId;
    }

    public String getBapNo() {
        return bapNo;
    }

    public void setBapNo(String bapNo) {
        this.bapNo = bapNo;
    }

    public String getMasterTransactionId() {
        return masterTransactionId;
    }

    public void setMasterTransactionId(String masterTransactionId) {
        this.masterTransactionId = masterTransactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "CrsTransactionRequest{" +
                "bapId='" + bapId + '\'' +
                ", bapNo='" + bapNo + '\'' +
                ", masterTransactionId='" + masterTransactionId + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
