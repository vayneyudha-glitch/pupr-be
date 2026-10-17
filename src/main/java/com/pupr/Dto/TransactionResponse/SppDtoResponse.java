package com.pupr.Dto.TransactionResponse;

import com.pupr.Transaction.Spp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SppDtoResponse  {
    private Spp spp;
    private String bidang;
    private String pekerjaan;
    private Boolean isSpm;
}
