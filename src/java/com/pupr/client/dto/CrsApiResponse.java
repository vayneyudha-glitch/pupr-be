package com.pupr.client.dto;

/**
 * Generic API response wrapper used by PUPR-CRS for all REST endpoints.
 *
 * @param <T> the type of the embedded data payload
 */
public class CrsApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public CrsApiResponse() {
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CrsApiResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
