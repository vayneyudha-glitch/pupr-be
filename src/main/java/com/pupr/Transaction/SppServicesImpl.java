package com.pupr.Transaction;

import com.pupr.Aop.ValidateTahunDb;
import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.SppDtoResponse;
import com.pupr.Master.DeleteCollectionServices;
import com.pupr.Master.DeletedCollection;
import com.pupr.Master.DeletedCollectionDaoServices;
import com.pupr.Master.MasterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class SppServicesImpl implements SppServices{

    @Autowired
    SppDaoServices sppDaoServices;

    @Autowired
    BapServices bapServices;

    @Autowired
    MasterServices masterServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Autowired
    BapDaoServices bapDaoServices;

    @Override
    @Transactional
    @ValidateTahunDb
    public Spp submitData(Spp payload) throws ConflictException{

        System.out.println("Ini Id " + payload.getId());
        if(payload.getId() == null){
            payload.setSppDateText(formatterV1.format(payload.getSppDate()));
            payload.setCreatedDate(LocalDate.now().toString());
            payload.setCreatedTime(LocalDateTime.now());
            payload.setLastUpdate(LocalDate.now().toString());

            List<Spp> sppByBapNo = sppDaoServices.findByBapNo(payload.getBapNo());
            Spp sppBySppNo = sppDaoServices.findBySppNo(payload.getSppNo());

            if(sppByBapNo.size() == 0 && sppBySppNo == null){
                sppDaoServices.submitData(payload);
                bapServices.bapUpdateChildTrxId(Long.valueOf(payload.getBapId()),payload.getId(),TRX_CODE_SPP);
            }else{
                throw new ConflictException("Data Duplicated");
            }
        }else {
            Optional <Spp> sppDb = sppDaoServices.findById(payload.getId());
            if(sppDb.isPresent()){
                payload.setCreatedDate(sppDb.get().getCreatedDate());
                payload.setCreatedTime(sppDb.get().getCreatedTime());
            }

            payload.setSppDateText(formatterV1.format(payload.getSppDate()));
            payload.setLastUpdate(LocalDate.now().toString());

            System.out.println("Ini Payload Req " + String.valueOf(payload));
            sppDaoServices.submitData(payload);
        }

        payload.setIsDeleted(false);
        return payload;
    }

    @Override
    public Spp submitRemoveData(Spp payload) {
        Optional <Spp> sppDB = sppDaoServices.findById(payload.getId());

        if(sppDB.isPresent()) {
            Spp spp = sppDB.get();
            spp.setDeletedBy(payload.getDeletedBy());
            spp.setDeletedDate(LocalDate.now().toString());
            spp.setDeletedTime(LocalDateTime.now());
            spp.setIsDeleted(true);
            return sppDaoServices.submitData(spp);
        }else{
            return null;
        }

    }

    @Override
    public DeletedCollection submitRemoveDataV2(Map<String, String> params) {

        DeletedCollection deletedCollection = new DeletedCollection();
        String bapid = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapid));
        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_SPP);
            deletedCollection.setValueTrx(String.valueOf(bap.getKwitansi()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            sppDaoServices.removeDataById(bap.getSpp().getId());
        }

        return deletedCollection;
    }

    @Override
    public Spp findBySppNo(String sppNo) {
        return sppDaoServices.findBySppNo(sppNo);
    }

    @Override
    public BapResponse findcompleteDataBySppId(String sppNo) {
        BapResponse bapData = new BapResponse();
         Spp sppData = sppDaoServices.findBySppNo(sppNo);

         if (sppData != null){
             bapData = bapServices.findById(Long.valueOf(sppData.getBapId()));
         }
         return bapData;
    }

    @Override
    public List<Spp> findByBapNo(String bapNo) {
        return sppDaoServices.findByBapNo(bapNo);
    }

    @Override
    public List<SppDtoResponse> getSppTrxByDatePeriode(Pageable pageable, Map<String, String> params) {

        String sppNo = params.get("sppNo");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        List<Spp> sppListDb = new ArrayList<>();
        List<SppDtoResponse> sppListResult = new ArrayList<>();
        List<SppDtoResponse> sppDtoResponses = new ArrayList<>();

        if(sppNo != null){
            sppListDb=  sppDaoServices.findBySppNoV2((sppNo));

            sppListResult = sppListDb.stream()
                    .map(this::mapperFromEntityToResponse)
                    .collect(Collectors.toList());
        }else{
            sppListDb =  sppDaoServices.findSppBySppDate(pageable,startDate,endDate);
            sppDtoResponses = sppListDb.stream()
                    .map(this::mapperFromEntityToResponse)
                    .collect(Collectors.toList());

            if(bidang != null){
                sppListResult = sppDtoResponses.stream()
                        .filter(bdg -> bdg.getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }else{
                sppListResult = sppDtoResponses;
            }
        }

        return sppListResult;
    }

    private SppDtoResponse mapperFromEntityToResponse(Spp spp){

        SppDtoResponse response = new SppDtoResponse();
        Bap bap = bapDaoServices.findByBapNo(spp.getBapNo());

        response.setSpp(spp);
        response.setIsSpm(false);

        if (bap != null){
            response.setPekerjaan(bap.getMaster().getPekerjaan());
            response.setBidang(bap.getMaster().getBidang());

            if (bap.getSpm() != null){
                response.setIsSpm(true);
            }
        }else {
            response.setPekerjaan("-");
            response.setBidang("-");
        }



        return response;
    }
}
