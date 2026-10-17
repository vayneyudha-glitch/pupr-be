package com.pupr.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.RingkasanResponse;
import com.pupr.Master.DeletedCollection;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RingkasanService {
    RingkasanResponse submit (Ringkasan ringkasan) throws Exception, ConflictException;
    RingkasanResponse findById (Long id);
    Ringkasan findByBapId (String id);
    List<Ringkasan> findByBapNo (String id);
    RingkasanResponse findByBapIdSecond (String id);
    Ringkasan findByMasterTrxId (String id);
    List<Ringkasan> findByLimit1(Pageable pageable, Map<String,String> params) ;
    List<RingkasanResponse> findByLimit1V2(Pageable pageable, Map<String,String> params) ;
    byte[] generateFaktur(Long id) throws Exception;
    byte[] generateFakturRingkasanRekapV1(Long id) throws Exception;

    Ringkasan removeById (Long id) throws Exception;
    DeletedCollection removeByIdV2 (Map<String,String> params) throws Exception;

    List<Ringkasan> findAllDataByTahun(String tahun);
}
