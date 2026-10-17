package com.pupr.Transaction;

import javax.persistence.Id;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.pupr.Dto.SignOff;
import com.pupr.Master.MasterPegawai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RINGKASAN_TRANSACTION")
public class Ringkasan extends SignOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String masterTransactionId;
    private String bapId;
    private String bapNo;
    private String noSirup;
    private String sumberAnggaranDesc;
    private String location;
    private String spkNo;
    private String spkDate;
    private String jobDesc;
    private String vendorId;
    private String dayEstimate;
    private String startDate;
    private String endDate;
    private String maintenanceTime;

    private String paymentId;
    private String paymentNo;
    private Date paymentDate;
    private String dpaDate;
    private String addendumDetail;
    private String namaPegawai;

    private String pekerjaan;

    @ManyToOne(fetch = FetchType.LAZY)
    private MasterPegawai pegawai;

    private Date tglStartPekerjaan;
    private Date tglEndPekerjaan;
    private String mitraId;
    private String mitraName;
    private String mitraPimpinan;
    private String mitraNoRekening;
}
