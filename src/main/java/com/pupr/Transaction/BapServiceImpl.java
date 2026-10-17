package com.pupr.Transaction;


import com.pupr.Aop.ValidateTahunDb;
import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.RemoveFatkturValidationResponse;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Master.*;
// DISABLED with OpenFeign/Eureka
//import com.pupr.client.CrsTransactionClient;
//import com.pupr.client.dto.CrsTransactionRequest;
//import com.pupr.client.dto.CrsTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.rmi.StubNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class BapServiceImpl implements BapServices {

    @Autowired
    BapDaoServices bapDaoServices;

    @Autowired
    MasterDaoService masterDaoService;

    @Autowired
    RingkasanDaoService ringkasanDaoService;

    @Autowired
    KwitansiDaoServices kwitansiDaoServices;

    @Autowired
    MasterMitraDaoServices masterMitraDaoServices;

    @Autowired
    SppDaoServices sppDaoServices;

    @Autowired
    SpmDaoServices spmDaoServices;

    @Autowired
    Sp2dDaoServices sp2dDaoServices;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @PersistenceContext
    private EntityManager entityManager;

    // DISABLED with OpenFeign/Eureka
    //@Autowired
    //CrsTransactionClient crsTransactionClient;

    @Override
    @Transactional
    @ValidateTahunDb
    public Bap submit(Bap payloadReq) throws Exception, ConflictException {
        if (payloadReq.getId() == null){
            payloadReq.setCreatedDate(LocalDate.now().toString());
            payloadReq.setCreatedTime(LocalDateTime.now());

            Bap bapDb =  bapDaoServices.findByBapNo(payloadReq.getBapNo());
            if (bapDb != null) {
                throw new ConflictException("Data Duplicated");
            }

            Master master = masterDaoService.getById(Long.valueOf(payloadReq.getMasterTransactionId()))
                    .orElseThrow(() -> new IllegalArgumentException("Master transaction was not found"));
            payloadReq.setMaster(master);

        }else {
            System.out.println("BAP ID " + payloadReq.getId());
            Bap bapDb = bapDaoServices.findById(payloadReq.getId());
            payloadReq.setCreatedDate(bapDb.getCreatedDate());
            payloadReq.setCreatedTime(bapDb.getCreatedTime());

            payloadReq.setMaster(bapDb.getMaster());
            payloadReq.setRingkasan(bapDb.getRingkasan());
            payloadReq.setKwitansi(bapDb.getKwitansi());
            payloadReq.setSpp(bapDb.getSpp());
        }

        payloadReq.setBapDateText(formatterV1.format(payloadReq.getBapDate()));
        payloadReq.setLastUpdate(LocalDate.now().toString());
        payloadReq.setLastUpdateTime(LocalDateTime.now());
        payloadReq.setIsDeleted(false);

        if(payloadReq.getHibahDate() == null){
            payloadReq.setHibahDate(new Date());
        }

        bapDaoServices.submit(payloadReq);
        return payloadReq;
    }

    @Override
    public BapResponse findById(Long id) {
        BapResponse bapResponse  = new BapResponse();

        Bap bap = bapDaoServices.findById(id);

        if (bap != null){
            bapResponse.setBap(bap);
            Optional<Master> master = masterDaoService.getById(Long.valueOf(bap.getMasterTransactionId()));

            if (master.get() != null){
                bapResponse.setMaster(master.get());
            }
        }

        return bapResponse;
    }

    @Override
    public BapResponse findByBapNo(String bapNo) {
        BapResponse bapResponse  = new BapResponse();

        Bap bap =bapDaoServices.findBAPByBapNoDateParam(bapNo,START_FROM_TAHUN_ANGGARAN);

//        System.out.println("Bap No " + String.valueOf(bap));
//        for show object to string

        if (bap != null){
            bapResponse.setBap(bap);
            Optional<Master> master = masterDaoService.getById(Long.valueOf(bap.getMasterTransactionId()));

            if (master.get() != null){
                bapResponse.setMaster(master.get());
            }

            Ringkasan ringkasan = ringkasanDaoService.findByBapNo(bapNo);
            if(ringkasan != null){
                bapResponse.setRingkasan(ringkasan);

                if (ringkasan.getVendorId() != null) {
                    Optional<MasterMitra> masterMitra = masterMitraDaoServices.getById(Long.valueOf(ringkasan.getVendorId()));
                    if (masterMitra.isPresent()) {
                        bapResponse.setMasterMitra(masterMitra.get());
                    }
                }

            }

            Kwitansi kwitansi = kwitansiDaoServices.findByBapNumber(bapNo);
            if(kwitansi != null) {
                bapResponse.setKwitansi(kwitansi);
            }

//            Spp spp = sppDaoServices.findByBapNo(bapNo);
//            if (spp != null){
//                bapResponse.setSpp(spp);
//            }

            Spm spm = spmDaoServices.findByBapNo(bapNo);
            if (spm != null){
                bapResponse.setSpm(spm);
            }

            Sp2D sp2d  = sp2dDaoServices.findByBapNo(bapNo);
            if (sp2d != null){
                bapResponse.setSp2d(sp2d);
            }

        }


        return bapResponse;
    }

    @Override
    public Bap findByMasterTrxId(String id) {
        Bap bap = new Bap();
        List<Bap> bapList = bapDaoServices.findByMasterTrxId(id);
        if (bapList.isEmpty()) {
            return bap;
        }else {
            List<Bap> bapData = bapList.stream()
                    .sorted(Comparator.comparing(Bap::getCreatedTime).reversed())
                    .collect(Collectors.toList());
            return bapData.get(0);
        }
    }

    @Override
    public List<Bap> findByMasterTrxIdV2(String id) {
        List<Bap> bapList = bapDaoServices.findByMasterTrxId(id);
        if (bapList.isEmpty()) {
            return null;
        }else {
            List<Bap> bapData = bapList.stream()
                    .sorted(Comparator.comparing(Bap::getCreatedTime).reversed())
                    .collect(Collectors.toList());
            return bapData;
        }
    }

    @Override
    public List<Bap> findAllDataByYear(String tahun) {

        tahun = tahun + "-01-01";
        List<Bap> bapList = bapDaoServices.findBapByDateMoreThanParam(tahun);

        System.out.println("Param Tahun : " + tahun);
//        List<Bap> bapList = bapDaoServices.findAll();
//        bapList = bapList.stream().filter(thn -> thn.getCreatedDate().substring(0,4).equalsIgnoreCase(tahun))
//                .collect(Collectors.toList());

        return bapList;
    }


    @Override
    public List<Bap> findByLimit1(Pageable pageable, Map<String,String> params) {
        List<Long> idList = new ArrayList<>();
        List<Bap> bapResultList = new ArrayList<>();

        String bapNo = params.get("bapNo");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        if(bapNo == null){
            bapResultList = bapDaoServices.findDataBapDate(pageable,startDate,endDate);

            /*if(bidang != null){
                bapResultList = bapResultList.stream()
                        .filter(result -> result.getMaster().getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }*/
        }else{
            bapResultList =  bapDaoServices.findByBapNoV2(bapNo,START_FROM_TAHUN_ANGGARAN);


        }

        if(bidang != null){
            System.out.println("Masuk Bidang : " + bidang);
            bapResultList = bapResultList.stream()
                    .filter(result -> result.getMaster().getBidang().equalsIgnoreCase(bidang))
                    .collect(Collectors.toList());
        }

        return bapResultList;
    }

    @Override
    public List<Bap> findByBapNoLimit(String bapNo, Pageable pageable) {
        List<Bap> bapList = bapDaoServices.findDataByBapNoLimit(bapNo,pageable);
        return bapList;
    }

    @Override
    public List<Bap> findByAllBapMasterTrxId(String id) {
        return bapDaoServices.findByMasterTrxId(id);
    }

    @Override
    public List<Bap> findByBapNoV2(String bapNo) {
        return bapDaoServices.findByBapNoV2(bapNo,START_FROM_TAHUN_ANGGARAN);
    }

    @Override
    public Bap removeById(Long id) throws Exception {
        Bap bapDb = bapDaoServices.findById(id);

        if (bapDb.getRingkasan() == null){
            bapDb.setDeletedDate(LocalDate.now().toString());
            bapDb.setDeletedTime(LocalDateTime.now());
            bapDb.setIsDeleted(true);
            bapDaoServices.submit(bapDb);
            return bapDb;
        }else{
            throw new Exception("Data Can't Be Removed");
        }
    }

    @Override
    public DeletedCollection removeByIdV2(Map<String,String> params) throws Exception {

        DeletedCollection deletedCollection = new DeletedCollection();
        String bapId = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(bapId));

        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_BAP);
            deletedCollection.setValueTrx(String.valueOf(bap));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            bapDaoServices.removeById(bap.getId());
        }

        return deletedCollection;
    }

    @Override
    public List<Bap> findByHibahNo(String bapNo) {

        return null;
    }

    @Override
    public Bap findOneBapByHibahNo(String hibahNo) {
        return bapDaoServices.findOneBapByHibahNo(hibahNo);
    }

    @Override
    public Bap findOneBapByBastp(String notrx) {
        Bap result = new Bap();
        Ringkasan ringkasan = ringkasanDaoService.findByBastpNo(notrx);

        if(ringkasan != null){
            result = bapDaoServices.findByBapNo(ringkasan.getBapNo());
        }

        return result;
    }

    @Override
    public void bapUpdateChildTrxId(Long bapId, Long trxId, String trxType) {
        Bap bapDb = bapDaoServices.findById(Long.valueOf(bapId));
        if (bapDb == null) {
            throw new IllegalArgumentException("BAP transaction was not found");
        }

        switch (trxType) {
            case TRX_CODE_RINGKASAN:
                bapDb.setRingkasan(entityManager.getReference(Ringkasan.class, trxId));
                break;
            case TRX_CODE_KWITANSI:
                bapDb.setKwitansi(entityManager.getReference(Kwitansi.class, trxId));
                break;
            case TRX_CODE_SPP:
                bapDb.setSpp(entityManager.getReference(Spp.class, trxId));
                break;
            case TRX_REKOM:
                bapDb.setRekom(entityManager.getReference(Rekom.class, trxId));
                break;
            case TRX_CODE_SPM:
                bapDb.setSpm(entityManager.getReference(Spm.class, trxId));
                break;
            case TRX_CODE_VALIDASI:
                bapDb.setValidasi(entityManager.getReference(Validasi.class, trxId));
                break;
            case TRX_CODE_SP2D:
                bapDb.setSp2D(entityManager.getReference(Sp2D.class, trxId));
                break;
            default:
                throw new IllegalArgumentException("Unsupported BAP child transaction type: " + trxType);
        }
        bapDaoServices.submit(bapDb);
    }

    @Override
    public Bap updateValidItemanis(Map<String,String> params) {

        String bapId = params.get("bapId");
        Boolean statusValidItem = Boolean.valueOf(params.get("validStatus"));

        Bap bapDb = bapDaoServices.findById(Long.valueOf(bapId));
        bapDb.setValidItemanis(statusValidItem);

        bapDaoServices.submit(bapDb);
        return bapDb;
    }

    @Override
    public RemoveFatkturValidationResponse removeFakturValidatio(Map<String, String> params) {

        RemoveFatkturValidationResponse response = new RemoveFatkturValidationResponse();
        Boolean statsFlag = null;
        String noFaktur;
        String trxType = null;

        String noBap = params.get("noBap");
        String currentTrxType = params.get("currentTrxType");

        if(noBap != null && currentTrxType !=null){
            Bap bapresult = bapDaoServices.findByBapNo(noBap);

            switch (currentTrxType){
                case "Bap":
                    statsFlag = bapresult.getRingkasan() != null ? true : false;
                    if(statsFlag){
                        trxType = "Ringkasan";
                    }
                    break;
                case "Ringkasan":
                    statsFlag = bapresult.getKwitansi() != null ? true : false;
                    if(statsFlag){
                        trxType = "Kwitansi";
                    }
                    break;
                case "Kwitansi" :
                    statsFlag = bapresult.getSpp() != null ? true : false;
                    if(statsFlag){
                        trxType = "Spp";
                        response.setUsedTrxNo(bapresult.getSpp().getSppNo());
                    }
                    break;
                case"SPP":
                    statsFlag = bapresult.getSpm() != null ? true : false;
                    if(statsFlag){
                        trxType = "Spm";
                        response.setUsedTrxNo(bapresult.getSpm().getSpmNo());
                    }
                    break;
                case "SPM" :
                    statsFlag = bapresult.getSp2D() != null ? true : false;
                    if(statsFlag){
                        trxType = "Sp2d";
                        response.setUsedTrxNo(bapresult.getSp2D().getSp2dNo());
                    }
                    break;
            }
        }
        
        if(statsFlag){
            response.setDeleted(false);
        }else{
            response.setDeleted(true);
        }
        
        response.setUsedTrx(trxType);
        

        return response;
    }

    // DISABLED with OpenFeign/Eureka
    //@Override
    //public CrsTransactionResponse syncToCrs(String bapId) throws Exception {
    //    Bap bap = bapDaoServices.findById(Long.valueOf(bapId));
    //    if (bap == null) {
    //        throw new Exception("BAP not found for id: " + bapId);
    //    }
//
    //    CrsTransactionRequest request = new CrsTransactionRequest();
    //    request.setBapId(bap.getId());
    //    request.setBapNo(bap.getBapNo());
    //    request.setMasterTransactionId(bap.getMasterTransactionId());
//
    //    if (bap.getBapDate() != null) {
    //        request.setTransactionDate(bap.getBapDateText());
    //    }
//
    //    if (bap.getRingkasan() != null) {
    //        request.setAmount(bap.getBapAmount());
    //        request.setDescription(bap.getBapNo());
    //    }
//
    //    return crsTransactionClient.createTransaction(request);
    //}
}
