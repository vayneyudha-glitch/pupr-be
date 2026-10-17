package com.pupr.Transaction;

import com.pupr.Dto.SignOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kwitansi_transaction")
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

    @Temporal(TemporalType.DATE)
    private Date kwitansiDate;
}