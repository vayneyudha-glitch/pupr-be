package com.pupr.Transaction;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pupr.Dto.Pengajuan;
import com.pupr.Dto.SignOff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bap_transaction")
public class Bap extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String masterTransactionId;
    private String bapNo;
    
    @Temporal(TemporalType.DATE)
    private Date bapDate;
    
    private String bapDateText;
    private BigDecimal bapAmount;
    private BigDecimal contrackAmount;
    private String paymentMethodId;
    private String paymentMethodDesc;
    private String hibahNo;
    
    @Temporal(TemporalType.DATE)
    private Date hibahDate;
    
    private String namePengajuan;
    private String phoneNumberPengajuan;

    @Embedded
    private Pengajuan pengajuan;

    private Boolean validItemanis;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @ManyToOne
    @JoinColumn(name = "ringkasan_id")
    private Ringkasan ringkasan;

    @ManyToOne
    @JoinColumn(name = "kwitansi_id")
    private Kwitansi kwitansi;

    @ManyToOne
    @JoinColumn(name = "spp_id")
    private Spp spp;

    @ManyToOne
    @JoinColumn(name = "sp2d_id")
    private Sp2D sp2D;

    @ManyToOne
    @JoinColumn(name = "spm_id")
    private Spm spm;

    @ManyToOne
    @JoinColumn(name = "validasi_id")
    private Validasi validasi;

    @ManyToOne
    @JoinColumn(name = "rekom_id")
    private Rekom rekom;
}