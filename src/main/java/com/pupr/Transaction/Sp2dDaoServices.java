package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface Sp2dDaoServices {
    Sp2D submitData(Sp2D paylaodData);

    void RemoveById(Long id);
    List<Sp2D> findAllData();
    Optional<Sp2D> findById(Long id);
    Sp2D findByBapNo(String bapNo);
    Sp2D findBySp2dNo(String sp2dNo);
    List<Sp2D> findSp2dByDatePeriode(Pageable pageable, String startDate, String endDate);
}
