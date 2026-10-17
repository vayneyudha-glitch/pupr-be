package com.pupr.Dto.TransactionResponse;

import com.pupr.Master.MasterMitra;
import com.pupr.Transaction.Bap;
import com.pupr.Transaction.Kwitansi;
import com.pupr.Transaction.Master;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KwitansiResponse {
    private Bap bap;
    private Master master;
    private Kwitansi kwitansi;
    private MasterMitra masterMitra;
}
