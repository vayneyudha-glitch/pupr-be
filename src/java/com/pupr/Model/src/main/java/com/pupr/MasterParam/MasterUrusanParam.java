package com.pupr.MasterParam;

import javax.persistence.Id;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.ManyToOne;

import javax.persistence.FetchType;

import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "MASTER_URUSAN_PARAM")
public class MasterUrusanParam extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urusanKode;
    private String urusanName;
    private String moduleCode;
    @ManyToOne(fetch = FetchType.LAZY)
    private MasterTugasanParam tugasanParam;
}
