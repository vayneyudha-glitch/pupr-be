package com.pupr.Dto.TransactionResponse;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidasiResponseListV1 {
    private Long id;
    private String validDate1;
    private String spmNo;
    private String uraian;
}
