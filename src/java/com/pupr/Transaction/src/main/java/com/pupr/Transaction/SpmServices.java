package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.SpmDtoResponse;
import com.pupr.Dto.TransactionResponse.ValidasiInfoDetailResponseV1;
import com.pupr.Dto.TransactionResponse.ValidasiResponseListV1;
import com.pupr.Master.DeletedCollection;
import org.hibernate.sql.Delete;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SpmServices {
    Spm submitData(Spm payload);
    Spm submitRemoveData(Spm payload);
    Spm findBySpmNo(String spmNo);
    Spm findByBapNo(String bapNo);
    BapResponse findcompleteDataBySppId(String spmNo);
    DeletedCollection removeById( Map<String,String> params);
    List<SpmDtoResponse> getSppTrxByDatePeriode(Pageable pageable, Map<String,String> params);
    ValidasiInfoDetailResponseV1 findSpmNoForValidInput(String spmNo);



}
