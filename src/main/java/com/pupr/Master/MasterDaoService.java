package com.pupr.Master;

import com.pupr.Transaction.Master;

import java.util.List;
import java.util.Optional;

public interface MasterDaoService {
    Master submit (Master payload);
    List<Master> findAllByYear(String tahun);
    List<Master> submitAll (List<Master> payload);
    Optional<Master> getById (Long id);
    List<Master> getBySpkNo (String spkNo);
    List<Master> getBySingleParam(String param);
    List<Master> getMasterByBidangAndPekerjaan(String bidang, String pekerjaan, int tahun);
    List<Master> getByListMasterTrxId (String bidang,List<Long> masterTrxId);
    List<Master> findByListMasterTrxIdWithoutBidang (List<Long> masterTrxId);

    List<Master> getMasterByBidang1(String bidang,int tahun);
    List<Master> getMasterByNoTrx(Integer noTrx);

    List<Master> fingOneMasterByPekerjaanAndBidang(String bidang, String pekerjaan, int tahun);
}
