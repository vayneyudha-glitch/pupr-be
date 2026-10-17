package com.pupr.Transaction;

import javax.persistence.Id;

import javax.persistence.Table;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.ManyToOne;

import javax.persistence.FetchType;

import com.pupr.Dto.SignOff;
import com.pupr.Master.MasterPegawai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ringkasan_transaction")
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

    @Temporal(TemporalType.DATE)
    private Date paymentDate;

    private String dpaDate;
    private String addendumDetail;
    private String namaPegawai;
    private String pekerjaan;

    @ManyToOne
    @JoinColumn(name = "pegawai_id")
    private MasterPegawai pegawai;

    @Temporal(TemporalType.DATE)
    private Date tglStartPekerjaan;

    @Temporal(TemporalType.DATE)
    private Date tglEndPekerjaan;

    private String mitraId;
    private String mitraName;
    private String mitraPimpinan;
    private String mitraNoRekening;
}