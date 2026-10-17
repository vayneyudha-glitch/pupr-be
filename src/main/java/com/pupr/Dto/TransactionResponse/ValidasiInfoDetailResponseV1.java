package com.pupr.Dto.TransactionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidasiInfoDetailResponseV1 {

    private String sumberAnggaranDesc;
    private String dpaDate;
    private String dpaNo;
    private String noSirup;

    private String programDesc;
    private String kegiatanDesc;
    private String subKegitanDesc;
    private String pekerjaan;
    private String location;

    private String tglSpk;
    private String spkNo;
    private String addendumDetail;

    private String corporateName;
    private String corporateAddress;
    private String corporateHeadOffice;
    private String corporateNpwp;
    private String corporateAccountumber;//Rekening Perusahaan
    private String corporateBankName;
    private BigDecimal spkAmount;
    private BigDecimal bapAmount;

    private String reciptDescription; //Uraian Kwitansi

    private String hibahNo;
}
