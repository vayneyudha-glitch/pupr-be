package com.pupr.Master;


import com.pupr.Dto.DashBoardResponse;
import com.pupr.Dto.TransactionResponse.MasterRealisationResponse;
import com.pupr.Dto.TransactionResponse.PekerjaanResponseDto;
import com.pupr.Transaction.Master;

import java.util.List;
import java.util.Map;

public interface MasterServices {
    Master submit(Master payload) throws Exception;
    List<Master> submitAll(List<Master> payload) throws Exception;
    Master findById (Long id) throws Exception;
    List<MasterRealisationResponse> findDataRealisation(Map<String,String> params) throws Exception;
    List<Master> findAllByTahunAnggaran(Map<String,String> params) throws Exception;
    List<Master> findDataByBidangYear(Map<String,String> params) throws Exception;
    Master submitSpkMaster(Master payload) throws Exception;
    List<Master> findBySpkNo (String spkNo) throws Exception;
    List<DashBoardResponse> getDashBoardInformation (Map<String,String> params) throws Exception;
    Master removeById (Long id) throws Exception;

    List<Master> syncPekerjaanByBidang(String bidangCode) throws Exception;

    List<Master> syncPekerjaanByBidangPatch(String bidangCode) throws Exception;

}
