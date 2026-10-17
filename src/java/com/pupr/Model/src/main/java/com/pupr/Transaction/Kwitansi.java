package com.pupr.Transaction;

import javax.persistence.Id;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.pupr.Dto.SignOff;
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
@Table(name = "KWITANSI_TRANSACTION")
public class Kwitansi extends SignOff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bapId;
    private String masterTransactionId;
    private String bapNo;
    private String description;
    private String kepalaBidangNo;
    private String kepalaBidangName;
    private String ppkNo;
    private String ppkName;
    private String pptkNo;
    private String pptkName;
    private String bppNo;
    private String bppName;
    private String direkturNo;
    private String direkturName;
    private Date kwitansiDate;
}
