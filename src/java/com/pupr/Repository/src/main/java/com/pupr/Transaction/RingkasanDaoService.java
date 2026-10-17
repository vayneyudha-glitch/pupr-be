package com.pupr.Transaction;


import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RingkasanDaoService {
    Ringkasan submit (Ringkasan ringkasan);
    void removeById (Long id);
    Ringkasan findById(Long id);
    Ringkasan findByBapNo (String bapNo);
    Ringkasan findByBastpNo (String noTrx);

    List<Ringkasan> findAll();
    List<Ringkasan> findDataLImit1 (Pageable pageable,String startDate,String endDate);
    List<Ringkasan> findDataByBapId (String bapId);
    List<Ringkasan> findDataMasterTrxId (String masterTrxId);
    List<Ringkasan> findDataRingkasanBapNoLike (String BapNp);
}
