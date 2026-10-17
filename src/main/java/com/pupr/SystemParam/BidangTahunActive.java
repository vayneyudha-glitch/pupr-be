package com.pupr.SystemParam;

import javax.persistence.Entity;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SYSTEM_BIDANG_PARAMS")
public class BidangTahunActive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bidangCode;
    private String bidangDesc;
    private String tahunActive;
}
