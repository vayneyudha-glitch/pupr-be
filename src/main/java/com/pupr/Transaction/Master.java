package com.pupr.Transaction;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.pupr.Dto.SignOff;
import com.pupr.Master.MasterMitra;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MASTER_TRANSACTION")
public class Master extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bidang;
    private String anggaran;
    private String kategory;
    private String kodeProgram;
    private String programDesc;
    private String kodeKegiatan;
    private String kegiatanDesc;
    private String kodeSubKegiatan;
    private String subKegitanDesc;
    private String kodeBelanja;
    private String belanjaDesc;
    private String pekerjaan;
    private String pekerjaanId;
    private String noDpa;
    private String tglDpa;
    private BigDecimal nilaiDpa;
    private Integer tahun;
    private Integer no;
    private String spkNo;
    private BigDecimal nilaiSpk;
    private String tglSpk;
    private Date tglSpkForm;
    private String version;
    private String tglStartPekerjaan;
    private String tglEndPekerjaan;
    private String mitraId;
    private String mitraName;
    private String mitraPimpinan;
    private String mitraNoRekening;
}
