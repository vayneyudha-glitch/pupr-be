package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.SppDtoResponse;
import com.pupr.Master.DeletedCollection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SppServices {
    Spp submitData(Spp payload);
    Spp submitRemoveData(Spp payload);
    DeletedCollection submitRemoveDataV2(Map<String,String> params);
    Spp findBySppNo(String sppNo);
    BapResponse findcompleteDataBySppId(String sppNo);
    List<Spp> findByBapNo(String phoNo);
    List<SppDtoResponse> getSppTrxByDatePeriode(Pageable pageable, Map<String,String> params);
}
