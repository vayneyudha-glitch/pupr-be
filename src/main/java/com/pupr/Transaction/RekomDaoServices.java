package com.pupr.Transaction;

import java.util.List;

public interface RekomDaoServices {
    void Submit (Rekom rekom);

    void RemoveById(Long id);

    Rekom findByNoBastp(String noBastp);

    Rekom findOneByNoRekom(String noRekom);

    List<Rekom> findOneByNoRekomV2(String noRekom);

    List<Rekom> findAllDataV1(String startDate, String endDate);
    List<Rekom> findAllDataNoFilter();
}
