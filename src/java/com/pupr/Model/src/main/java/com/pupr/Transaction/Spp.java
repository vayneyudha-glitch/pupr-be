package com.pupr.Transaction;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;

import com.pupr.Dto.SignOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SPP_TRANSACTION")
public class Spp extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String masterTransactionId;
    private String bapId;
    private String bapNo;
    private String ringkasanId;
    private String kwitansiId;
    private String mitraId;

    private String sppNo;
    private Date sppDate;
    private String sppDateText;
    private String phoNo;
    private Date phoDate;

    @Column(name = "\"desc\"")
    private String desc;
    private BigDecimal bapAmount;

}
