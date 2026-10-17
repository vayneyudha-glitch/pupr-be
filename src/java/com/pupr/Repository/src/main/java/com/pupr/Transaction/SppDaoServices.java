package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SppDaoServices {
    Spp submitData(Spp paylaodData);

    void removeDataById(Long id);
    Optional <Spp> findById(Long id);
    List<Spp> findAllData();
    List<Spp> findByBapNo(String bapNo);
    Spp findBySppNo(String sppNo);

    List<Spp> findBySppNoV2(String sppNo);
    List<Spp> findSppByDatePeriode(Pageable pageable,String startDate,String endDate);
    List<Spp> findSppBySppDate(Pageable pageable,String startDate,String endDate);
}
