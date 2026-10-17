package com.pupr.Dto.TransactionResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PekerjaanResponseDto {

    private String id;
    private String bidangCode;
    private String code;
    private String description;
    private String dpaValue;
    private Date dpaDate;
    private String noDpa;
    private String tahunAnggaran;
    private String anggaranDesc;
    private String categoryType;
    private Boolean isActive;
    private int sortOrder;
    private String additionalInfo;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Fields from DocumentMaster (program to belanja)
    private String documentMasterId;
    private String programCode;
    private String programDescription;
    private String programCodeRef;
    private String programDescRef;
    private String kegiatanCodeRef;
    private String kegiatanDescRef;
    private String subKegiatanCodeRef;
    private String subKegiatanDescRef;
    private String belanjaCodeRef;
    private String belanjaDescRef;
    private BigDecimal belanjaValue;
}