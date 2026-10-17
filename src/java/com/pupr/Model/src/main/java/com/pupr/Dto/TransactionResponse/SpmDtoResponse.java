package com.pupr.Dto.TransactionResponse;

import com.pupr.Transaction.Spm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpmDtoResponse extends Spm {
    private String bidang;
    private String pekerjaan;
}
