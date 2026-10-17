package com.pupr.Dto.Param;

import com.pupr.Dto.SignOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TugasanParamReq extends SignOff {

    private Long id;
    private String tugasanKode;
    private String tugasanName;
    private String urusanKode;
}
