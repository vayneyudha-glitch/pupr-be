package com.pupr.HistoryTransaction;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.ObjectInputStream;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECAP_SUMMARY_TRANSACTION")
public class RecapSummaryTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tahunAnggaran;
    private BigDecimal master;
    private BigDecimal bap;
    private BigDecimal ringkasan;
    private BigDecimal npd;
    private BigDecimal kwitansi;
    private BigDecimal spp;
    private BigDecimal spm;
    private BigDecimal sp2d;
}
