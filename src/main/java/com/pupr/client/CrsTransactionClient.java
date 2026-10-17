/*
 * OpenFeign client for PUPR-CRS budget transaction communication.
 */
package com.pupr.client;

import com.pupr.client.dto.CrsTransactionRequest;
import com.pupr.client.dto.CrsTransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OpenFeign client for synchronous communication with the PUPR-CRS
 * budget-transaction endpoint.
 * <p>
 * All calls are routed via Eureka service discovery using the service name "PUPR-CRS".
 */
@FeignClient(
        name = "PUPR-CRS",
        contextId = "crsTransactionClient"
)
public interface CrsTransactionClient {

    /**
     * Submit a budget transaction to the CRS service.
     *
     * @param request the transaction payload
     * @return the created transaction response
     */
    @PostMapping("/api/v1/crs/transactions")
    CrsTransactionResponse createTransaction(@RequestBody CrsTransactionRequest request);
}
