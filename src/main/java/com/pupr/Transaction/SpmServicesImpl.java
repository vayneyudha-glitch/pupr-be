package com.pupr.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.SpmDtoResponse;
import com.pupr.Dto.TransactionResponse.ValidasiInfoDetailResponseV1;
import com.pupr.Dto.TransactionResponse.ValidasiResponseListV1;
import com.pupr.Master.DeletedCollection;
import com.pupr.Master.DeletedCollectionDaoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class SpmServicesImpl implements SpmServices {

    @Autowired
    SpmDaoServices spmDaoServices;

    @Autowired
    BapServices bapServices;

    @Autowired
    BapDaoServices bapDaoServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Override
    public Spm submitData(Spm payload) {
        payload.setCreatedDate(LocalDate.now().toString());
        payload.setCreatedTime(LocalDateTime.now());
        payload.setIsDeleted(false);

        Spm spmByBapNo = spmDaoServices.findByBapNo(payload.getBapNo());
        Spm spmBySppNo = spmDaoServices.findBySpmNo(payload.getSpmNo());

        if(spmByBapNo == null && spmBySppNo == null){
            payload.setSpmDateText(formatterV1.format(payload.getSpmDate()));
            spmDaoServices.submitData(payload);
            bapServices.bapUpdateChildTrxId(Long.valueOf(payload.getBapId()),payload.getId(),TRX_CODE_SPM);
            return payload;
        }else{
            throw new ConflictException("Data Duplicated");
        }
    }

    @Override
    public Spm submitRemoveData(Spm payload) {
        Optional<Spm> spmDB = spmDaoServices.findById(payload.getId());

        if(spmDB.isPresent()) {
            Spm Spm = spmDB.get();
            Spm.setDeletedBy(payload.getDeletedBy());
            Spm.setDeletedDate(LocalDate.now().toString());
            Spm.setDeletedTime(LocalDateTime.now());
            Spm.setIsDeleted(true);
            return spmDaoServices.submitData(Spm);
        }else{
            return null;
        }
    }

    @Override
    public Spm findBySpmNo(String spmNo) {
        return spmDaoServices.findBySpmNo(spmNo);
    }

    @Override
    public Spm findByBapNo(String bapNo) {
        return spmDaoServices.findByBapNo(bapNo);
    }

    @Override
    public BapResponse findcompleteDataBySppId(String spmNo) {
        BapResponse bapResponse = new BapResponse();
        Spm spm = spmDaoServices.findBySpmNo(spmNo);

        if (spm != null){
            bapResponse = bapServices.findById(Long.valueOf(spm.getBapId()));
        }
        return bapResponse;
    }

    @Override
    public DeletedCollection removeById(Map<String, String> params) {

        DeletedCollection deletedCollection = new DeletedCollection();
        String bapid = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapid));
        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_SPM);
            deletedCollection.setValueTrx(String.valueOf(bap.getKwitansi()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            spmDaoServices.RemoveById(bap.getSpm().getId());
        }

        return deletedCollection;
    }

    @Override
    public List<SpmDtoResponse> getSppTrxByDatePeriode(Pageable pageable, Map<String, String> params) {

        List<SpmDtoResponse> spmDtoResponses = new ArrayList<>();

        String spmNo = params.get("spmNo");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        if (spmNo != null){
            List<Spm> spmList = spmDaoServices.findBySpmNoV2(spmNo);
            spmDtoResponses = spmList.stream()
                    .map(this::mapperFromEntityToResponse)
                    .collect(Collectors.toList());
        }else{
            List<Spm> spmList = spmDaoServices.findSppBySpmDatePeriode(pageable,startDate,endDate);
            spmDtoResponses = spmList.stream()
                    .map(this::mapperFromEntityToResponse)
                    .collect(Collectors.toList());

            if (bidang != null){
                spmDtoResponses = spmDtoResponses.stream()
                        .filter(bdg -> bdg.getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }
        }

        return spmDtoResponses;
    }

    @Override
    public ValidasiInfoDetailResponseV1 findSpmNoForValidInput(String spmNo) {
        BapResponse bapResponse = new BapResponse();
        ValidasiInfoDetailResponseV1 responseListV1 = new ValidasiInfoDetailResponseV1();

        Spm spm = spmDaoServices.findBySpmNo(spmNo);

        if (spm != null){
            bapResponse = bapServices.findById(Long.valueOf(spm.getBapId()));
        }

        if (bapResponse != null) {
            responseListV1 = mapperFromBapResponseToValidastionRspnsListV1(bapResponse);
        }

        return responseListV1;
    }


    //This method for mapper from Bapresponse to ValidasiResponse needed for Validastion checker, call by Spm No
    private ValidasiInfoDetailResponseV1 mapperFromBapResponseToValidastionRspnsListV1(BapResponse response){

        ValidasiInfoDetailResponseV1 listV1 = new ValidasiInfoDetailResponseV1();

        //MasterTrx Section
        if (response.getMaster() != null){
            listV1.setSumberAnggaranDesc(response.getMaster().getAnggaran());
            listV1.setDpaDate(response.getMaster().getTglDpa());

            listV1.setProgramDesc(response.getMaster().getProgramDesc());
            listV1.setKegiatanDesc(response.getMaster().getKegiatanDesc());
            listV1.setSubKegitanDesc(response.getMaster().getSubKegitanDesc());
            listV1.setPekerjaan(response.getMaster().getPekerjaan());

            listV1.setSpkNo(response.getMaster().getSpkNo());
            listV1.setTglSpk(response.getMaster().getTglSpk());

            listV1.setSpkAmount(response.getMaster().getNilaiSpk());
            listV1.setBapAmount(response.getMaster().getNilaiSpk());
        }

        if(response.getRingkasan() != null){
            listV1.setNoSirup(response.getRingkasan().getNoSirup());
            listV1.setLocation(response.getRingkasan().getLocation());
            listV1.setAddendumDetail(response.getRingkasan().getAddendumDetail());
        }

        if(response.getBap() != null){
            listV1.setSpkAmount(response.getBap().getBapAmount());
            listV1.setHibahNo(response.getBap().getHibahNo());
        }

        if (response.getKwitansi() != null){
            listV1.setReciptDescription(response.getKwitansi().getDescription());
        }

        return  listV1;
    }

    private SpmDtoResponse mapperFromEntityToResponse(Spm spm){

        SpmDtoResponse dtoResponse = new SpmDtoResponse();

        dtoResponse.setId(spm.getId());
        dtoResponse.setMasterTransactionId(spm.getMasterTransactionId());
        dtoResponse.setBapId(spm.getBapId());
        dtoResponse.setBapNo(spm.getBapNo());
        dtoResponse.setRingkasanId(spm.getRingkasanId());
        dtoResponse.setKwitansiId(spm.getKwitansiId());
        dtoResponse.setMitraId(spm.getMitraId());
        dtoResponse.setSppId(spm.getSppId());
        dtoResponse.setSppNo(spm.getSppNo());
        dtoResponse.setSpmNo(spm.getSpmNo());
        dtoResponse.setSpmDate(spm.getSpmDate());
        dtoResponse.setDesc(spm.getDesc());
        dtoResponse.setBapAmount(spm.getBapAmount());

        if (spm.getBapNo() != null){
            Bap bap = bapDaoServices.findByBapNo(spm.getBapNo());

            dtoResponse.setBidang(bap != null ? bap.getMaster().getBidang() : "-");
            dtoResponse.setPekerjaan(bap != null ? bap.getMaster().getBidang() : "-");
        }

        return dtoResponse;
    }

}
