package com.pupr.Transaction;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.ManyToOne;

import javax.persistence.FetchType;

import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.Table;

import com.pupr.Dto.Pengajuan;
import com.pupr.Dto.SignOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BAP_TRANSACTION")
public class Bap extends SignOff {

    @Transient
    private static final String SEQEUNCE_NAME = "user_sequence";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String masterTransactionId;
    private String bapNo;
    private Date bapDate;
    private String bapDateText;
    private BigDecimal bapAmount;
    private BigDecimal contrackAmount;
    private String paymentMethodId;
    private String paymentMethodDesc;
    private String hibahNo;
    private Date hibahDate;
    private String namePengajuan;
    private String phoneNumberPengajuan;
    @Embedded
    private Pengajuan pengajuan;
    private Boolean validItemanis;
    @ManyToOne(fetch = FetchType.LAZY)
    private Master master;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ringkasan ringkasan;
    @ManyToOne(fetch = FetchType.LAZY)
    private Kwitansi kwitansi;
    @ManyToOne(fetch = FetchType.LAZY)
    private Spp spp;
    @ManyToOne(fetch = FetchType.LAZY)
    private Sp2D sp2D;
    @ManyToOne(fetch = FetchType.LAZY)
    private Spm spm;
    @ManyToOne(fetch = FetchType.LAZY)
    private Validasi validasi;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rekom rekom;


}
