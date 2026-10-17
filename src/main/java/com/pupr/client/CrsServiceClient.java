/*
 * OpenFeign client for PUPR-CRS service communication.
 */
package com.pupr.client;

import com.pupr.client.dto.CrsApiResponse;
import com.pupr.client.dto.CrsDataRequest;
import com.pupr.client.dto.CrsDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OpenFeign client for synchronous communication with the PUPR-CRS service.
 * <p>
 * All calls are routed via Eureka service discovery using the service name "PUPR-CRS".
 * No hardcoded hostnames, IPs, or gateway URLs are used.
 */
@FeignClient(
        name = "PUPR-CRS",
        contextId = "crsServiceClient"
)
public interface CrsServiceClient {

    /**
     * Retrieve a data record by its unique identifier.
     *
     * @param id the record identifier
     * @return API response wrapping the data
     */
    @GetMapping("/api/v1/data/{id}")
    CrsApiResponse<CrsDataResponse> getDataById(@PathVariable("id") String id);

    /**
     * Create a new data record.
     *
     * @param request the payload containing the data to create
     * @return API response wrapping the created record
     */
    @PostMapping("/api/v1/data")
    CrsApiResponse<CrsDataResponse> createData(@RequestBody CrsDataRequest request);
}
