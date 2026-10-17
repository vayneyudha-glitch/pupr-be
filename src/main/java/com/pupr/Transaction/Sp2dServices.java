package com.pupr.Transaction;

import com.pupr.Master.DeletedCollection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface Sp2dServices {
    Sp2D submitData(Sp2D payload);
    Sp2D submitRemoveData(Sp2D payload);
    Sp2D findBySp2dNo(String sp2dNo);
    Sp2D findByBapNo(String bapNo);
    DeletedCollection removeById(Map<String,String> params);
    List<Sp2D> getSppTrxByDatePeriode(Pageable pageable, Map<String,String> params);
}
