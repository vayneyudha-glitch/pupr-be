package com.pupr.Dto.TransactionRequest;

public class SyncBidangRequest {

    private String bidangCode;

    public SyncBidangRequest() {
    }

    public SyncBidangRequest(String bidangCode) {
        this.bidangCode = bidangCode;
    }

    public String getBidangCode() {
        return bidangCode;
    }

    public void setBidangCode(String bidangCode) {
        this.bidangCode = bidangCode;
    }
}
