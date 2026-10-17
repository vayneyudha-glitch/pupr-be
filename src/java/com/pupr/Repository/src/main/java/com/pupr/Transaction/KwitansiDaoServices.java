package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KwitansiDaoServices {
    Kwitansi submit (Kwitansi kwitansi);
    void removeById(Long id);
    Kwitansi findById(Long id);
    Kwitansi findByBapNumber(String bapNo);

    List<Kwitansi> findAllDataByTahun(String tahun);
    List<Kwitansi>  findByBapId(String bapId);
    List<Kwitansi> findDataLImit1 (Pageable pageable,String startDate, String endDate);
    List<Kwitansi>  findByBapNoLike(String bapNo);
}
