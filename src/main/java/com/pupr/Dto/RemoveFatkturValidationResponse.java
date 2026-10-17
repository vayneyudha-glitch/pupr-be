package com.pupr.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveFatkturValidationResponse {

    private String usedTrx;
    private String usedTrxNo;
    private boolean isDeleted;
}
