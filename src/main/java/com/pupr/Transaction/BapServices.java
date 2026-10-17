package com.pupr.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.RemoveFatkturValidationResponse;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Master.DeletedCollection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BapServices {

    Bap submit (Bap bap) throws Exception, ConflictException;
    BapResponse findById (Long id);
    BapResponse findByBapNo (String bapNo);
    Bap findByMasterTrxId (String id);
    List<Bap> findByMasterTrxIdV2 (String id);
    List<Bap> findAllDataByYear (String tahun);
    List<Bap> findByLimit1(Pageable pageable, Map<String,String> params) ;
    List<Bap> findByBapNoLimit(String bapNo,Pageable pageable) ;
    List<Bap> findByAllBapMasterTrxId (String id);
    List<Bap>  findByBapNoV2 (String bapNo);
    Bap removeById (Long id)throws Exception;
    DeletedCollection removeByIdV2 (Map<String,String> params)throws Exception;
    List<Bap>  findByHibahNo (String hibahNo);
    Bap  findOneBapByHibahNo (String hibahNo);
    Bap  findOneBapByBastp (String notrx);
    void bapUpdateChildTrxId(Long bapId, Long trxId, String trxType);
    Bap updateValidItemanis (Map<String,String> params);

    RemoveFatkturValidationResponse removeFakturValidatio(Map<String,String> params);

    // DISABLED: syncToCrs method removed with OpenFeign/Eureka deactivation

}
