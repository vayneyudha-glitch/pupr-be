package com.pupr.Master;

import javax.persistence.Id;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "MASTER_VENDOR")
public class MasterMitra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String corporateName;
    private String address;
    private String headOffice;
    private String npwp;
    private String rekeningNumber;
    private String bankAccountName;
    private String bankAccountType;
}
