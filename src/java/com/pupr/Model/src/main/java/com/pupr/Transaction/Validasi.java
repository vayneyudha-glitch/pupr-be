package com.pupr.Transaction;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VALIDATION_TRANSACTION")
public class Validasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String masterTransactionId;
    private String bapId;
    private String bapNo;
    private String spmNo;
    private String uraian;

    private String vadlidBy1;
    private Date createdTimeValid1;
    private String vadlidBy2;
    private Date createdTimeValid2;
}
