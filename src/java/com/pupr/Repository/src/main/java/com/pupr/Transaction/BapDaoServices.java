package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface BapDaoServices {
    void submit (Bap payload);

    void removeById(Long id);
    Bap findById (Long id);
    Bap findByBapNo (String bapNo);
    Bap findBAPByBapNoDateParam (String bapNo,String dateParam);
    List<Bap> findAll ();
    List<Bap> findDataLImit1 (Pageable pageable,String startDate,String endDate);
    List<Bap> findDataBapDate(Pageable pageable,String startDate,String endDate);
    List<Bap> findDataByBapNoLimit (String bapNo,Pageable pageable);
    List<Bap> findByBapNoV2 (String bapNo,String dateParam);
    List<Bap> findByMasterTrxId (String id);
    Bap findOneBapByHibahNo (String hibahNo);
    List<Bap> findBapByDateMoreThanParam (String tahunParam);

}
