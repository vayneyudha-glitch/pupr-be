package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SpmDaoServices {
    Spm submitData(Spm paylaodData);

    void RemoveById(Long id);

    List<Spm> findAllData();
    Optional<Spm> findById(Long id);
    Spm findByBapNo(String bapNo);
    List<Spm>findBySpmNoV2 (String spmNo);
    Spm findBySpmNo(String spmNo);
    List<Spm> findSppByDatePeriode(Pageable pageable, String startDate, String endDate);
    List<Spm> findSppBySpmDatePeriode(Pageable pageable, String startDate, String endDate);
}
