package com.pupr.client.dto;

/**
 * Response payload returned by PUPR-CRS after creating a budget transaction.
 */
public class CrsTransactionResponse {

    private boolean success;
    private String message;
    private String transactionId;
    private String status;

    public CrsTransactionResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CrsTransactionResponse{" +
                "success=" + success +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
