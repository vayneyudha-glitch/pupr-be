package com.pupr.MasterParam;

import javax.persistence.Entity;

import javax.persistence.GenerationType;

import javax.persistence.GeneratedValue;

import javax.persistence.Id;
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
@Table(name = "MASTER_TUGASAN_PARAM")
public class MasterTugasanParam extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tugasanKode;
    private String tugasanName;
    private String urusanKode;
    private Boolean isActive;
}
