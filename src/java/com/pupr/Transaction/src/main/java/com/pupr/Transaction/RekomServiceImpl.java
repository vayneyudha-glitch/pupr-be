package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.RekomResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Master.DeletedCollectionDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.TRX_CODE_KWITANSI;
import static com.pupr.Util.CommonParamConstans.TRX_REKOM;

@Service
public class RekomServiceImpl implements RekomServices {

    @Autowired
    RekomDaoServices rekomDaoServices;

    @Autowired
    BapServices bapServices;

    @Autowired
    BapDaoServices bapDaoServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Override
    public Rekom Submit(Rekom rekom) {
        if(rekom.getBapId() == null ){
//           Bap bapDb = bapServices.findByBapNo(rekom.getBapNo());
//           rekom.setBapId(r.getId());
//           rekom.setBapNo(bapDb.getBapNo());
        }
        rekom.setCreatedDate(new Date().toString());

        rekomDaoServices.Submit(rekom);

        bapServices.bapUpdateChildTrxId(Long.valueOf(rekom.getBapId()),rekom.getId(),TRX_REKOM);
        return rekom;
    }

    @Override
    public DeletedCollection RemoveById(Map<String, String> params) {
        DeletedCollection deletedCollection = new DeletedCollection();

        String bapid = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapid));
        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_REKOM);
            deletedCollection.setValueTrx(String.valueOf(bap.getKwitansi()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            rekomDaoServices.RemoveById(bap.getRekom().getId());
        }

        return deletedCollection;
    }

    @Override
    public List<RekomResponse> FindAllDataWithoutFilter(Map<String,String> params) {

        List<RekomResponse> responsesResult = new ArrayList<>();

        String noRekom = params.get("noRekom");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        if(noRekom != null){
            List<Rekom> rekomList = rekomDaoServices.findOneByNoRekomV2(noRekom);
            responsesResult = rekomList.stream()
                    .map(this::mapperFromRekomToDtoResponse)
                    .collect(Collectors.toList());
        }else{
            List<Rekom> rekomList = rekomDaoServices.findAllDataV1(startDate,endDate);
            responsesResult = rekomList.stream()
                    .map(this::mapperFromRekomToDtoResponse)
                    .collect(Collectors.toList());

            if(bidang != null){
                responsesResult =responsesResult.stream()
                        .filter(bdg -> bdg.getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }
        }


        return responsesResult;
    }


    private RekomResponse mapperFromRekomToDtoResponse(Rekom entity){

        RekomResponse response = new RekomResponse();
        if( entity.getBapNo() != null && !entity.getBapNo().isEmpty()){
            Bap bap = bapDaoServices.findByBapNo(entity.getBapNo());
            response.setBidang( bap != null ?  bap.getMaster().getBidang() : "-");
        }

        response.setId(entity.getId());
        response.setBapId(entity.getBapId());
        response.setBapNo(entity.getBapNo());
        response.setBastpNo(entity.getBastpNo());
        response.setNoRekom(entity.getNoRekom());
        response.setRekomeDate(entity.getRekomeDate());
        response.setTglRekom(entity.getTglRekom());
        response.setPekerjaan(entity.getPekerjaan());

        return response;
    }

}
