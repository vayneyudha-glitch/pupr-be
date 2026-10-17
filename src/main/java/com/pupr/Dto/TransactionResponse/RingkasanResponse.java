package com.pupr.Dto.TransactionResponse;

import com.pupr.Master.MasterMitra;
import com.pupr.Transaction.Bap;
import com.pupr.Transaction.Master;
import com.pupr.Transaction.Ringkasan;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RingkasanResponse {
    private Bap bap;
    private Master master;
    private Ringkasan ringkasan;
    private MasterMitra masterMitra;
}
