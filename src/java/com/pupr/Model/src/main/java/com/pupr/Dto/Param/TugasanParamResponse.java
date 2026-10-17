package com.pupr.Dto.Param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TugasanParamResponse {

    private String tugasanKode;
    private String tugasanName;
    private String urusanKode;
}
