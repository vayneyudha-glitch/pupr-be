package com.pupr.Dto.TransactionResponse;

import com.pupr.Master.MasterMitra;
import com.pupr.Transaction.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BapResponse {
    private Bap bap;
    private Master master;
    private Ringkasan ringkasan;
    private Kwitansi kwitansi;
    private MasterMitra masterMitra;
    private Spp spp;
    private Spm spm;
    private Sp2D sp2d;

}
