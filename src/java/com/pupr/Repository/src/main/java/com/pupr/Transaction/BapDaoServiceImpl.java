package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public class BapDaoServiceImpl implements BapDaoServices {

    @Autowired
    BapRepo bapRepo;

    @Override
    public void submit(Bap payload) {
        bapRepo.save(payload);
    }

    @Override
    public void removeById(Long id) {
        bapRepo.deleteById(id);
    }

    @Override
    public Bap findById(Long id) {
        return bapRepo.findById(id).get();
    }

    @Override
    public Bap findByBapNo(String bapNo) {
        return bapRepo.findByBapNo(bapNo);
    }

    @Override
    public Bap findBAPByBapNoDateParam(String bapNo, String dateParam) {
        System.out.println("Date Param " + dateParam);
        return bapRepo.findByBapNoV2WithDate(bapNo,dateParam);
    }

    @Override
    public List<Bap> findAll() {
        return bapRepo.findAll();
    }

    @Override
    public List<Bap> findDataLImit1(Pageable pageable,String startDate, String endDate) {
        return bapRepo.findByLimit1(pageable,startDate,endDate);
    }

    @Override
    public List<Bap> findDataBapDate(Pageable pageable, String startDate, String endDate) {
        return bapRepo.findByBapDateV2(startDate,endDate);
    }

    @Override
    public List<Bap> findDataByBapNoLimit(String bapNo,Pageable pageable) {
        return bapRepo.findByBapNoLimit(bapNo,pageable);
    }

    @Override
    public List<Bap> findByBapNoV2(String bapNo,String dateparam) {
        return bapRepo.findByBapNoV2(bapNo,dateparam);
    }

    @Override
    public List<Bap> findByMasterTrxId(String id) {
        return bapRepo.findByMasterTrxId(id);
    }

    @Override
    public Bap findOneBapByHibahNo(String hibahNo) {
        Optional<Bap> bapData = bapRepo.findOneBayByHibahNo(hibahNo);
        if (bapData.isPresent()){
            System.out.println("Masuk 54");
            return bapRepo.findOneBayByHibahNo(hibahNo).get();
        }else{
            System.out.println("Masuk 55 " + hibahNo);
            return null;
        }
    }

    @Override
    public List<Bap> findBapByDateMoreThanParam(String tahunParam) {
        return bapRepo.findByCreatedDateAfter(tahunParam);
    }
}
