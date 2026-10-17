package com.pupr.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.KwitansiResponse;
import com.pupr.Master.DeletedCollection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface KwitansiServices {
    KwitansiResponse submit (Kwitansi kwitansi) throws Exception, ConflictException;
    KwitansiResponse findById(Long id) throws Exception;
    List<Kwitansi> findByBapId(String id) throws Exception;
    List<Kwitansi> findByLimit1(Pageable pageable, Map<String,String> params) ;

    List<Bap> findByLimit1V2(Pageable pageable, Map<String,String> params) ;
    List<Kwitansi> findByBapNo(String bapNo) throws Exception;
    byte[] generateFaktur(Long id) throws Exception;

    Kwitansi removeById(Long id) throws Exception;

    DeletedCollection removeById2 (Map<String,String> params);
}
