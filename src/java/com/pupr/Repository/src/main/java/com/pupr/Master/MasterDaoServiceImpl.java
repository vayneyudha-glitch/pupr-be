package com.pupr.Master;

import com.pupr.Transaction.Master;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MasterDaoServiceImpl implements MasterDaoService {

    @Autowired
    MasterRepo masterRepo;

    @Override
    public Master submit(Master payload) {
        return masterRepo.save(payload);
    }

    @Override
    public List<Master> findAllByYear(String tahun) {
        List<Master> result =  masterRepo.findAll();
//        return result.stream()
//                .filter(thn -> thn.getTahun().equals(tahun))
//                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Master> submitAll(List<Master> payload) {
        masterRepo.saveAll(payload);
        return payload;
    }

    @Override
    public Optional<Master> getById(Long id) {
        return masterRepo.findById(id);
    }

    @Override
    public List<Master> getBySpkNo(String spkNo) {
        return masterRepo.findBySpkNo(spkNo);
    }

    @Override
    public List<Master> getBySingleParam(String param) {
        return masterRepo.findBySingleParam(param);
    }

    @Override
    public List<Master> getMasterByBidangAndPekerjaan(String bidang, String pekerjaan, int tahun) {

        List<Master> masterList = new ArrayList<>();

        if( pekerjaan != null && !pekerjaan.isEmpty()){
            masterList = masterRepo.findMasterDataByBidangAndPekerjaan(bidang,pekerjaan,tahun );
        }else{
            masterList  = masterRepo.findBySingleParamV2(bidang,tahun);
        }

        System.out.println("Master List Result " + masterList.size());
        return masterList;
    }

    @Override
    public List<Master> getByListMasterTrxId(String bidang,List<Long> masterTrxId) {
//        masterRepo.findAllById(masterTrxId);
        return masterRepo.findByListMasterTrxId(bidang,masterTrxId);
    }

    @Override
    public List<Master> findByListMasterTrxIdWithoutBidang(List<Long> masterTrxId) {
        return masterRepo.findByListMasterTrxIdWithoutBidang(masterTrxId);
    }

    @Override
    public List<Master> getMasterByBidang1(String bidang,int tahun) {
        return masterRepo.findMasterDataByBidang1(bidang,tahun);
    }

    @Override
    public List<Master> getMasterByNoTrx(Integer noTrx) {
        return masterRepo.findByNoTrx(noTrx);
    }

    @Override
    public List<Master> fingOneMasterByPekerjaanAndBidang(String bidang, String pekerjaan, int tahun) {
        return masterRepo.findByBidangAndPekejaan(bidang,pekerjaan, tahun);
    }
}
