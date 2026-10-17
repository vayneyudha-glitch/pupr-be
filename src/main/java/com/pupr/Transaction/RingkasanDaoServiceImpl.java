package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RingkasanDaoServiceImpl implements RingkasanDaoService{

    @Autowired
    RingkasanRepo ringkasanRepo;


    @Override
    public Ringkasan submit(Ringkasan ringkasan) {
        ringkasanRepo.save(ringkasan);
        return ringkasan;
    }

    @Override
    public void removeById(Long id) {

        ringkasanRepo.deleteById(id);
    }

    @Override
    public Ringkasan findById(Long id) {

        Optional<Ringkasan> ringkasanOptional = ringkasanRepo.findById(id);

        if(ringkasanOptional.isPresent()){
            return ringkasanOptional.get();
        }else{
            return null;
        }
    }

    @Override
    public Ringkasan findByBapNo(String bapNo) {
        List<Ringkasan> ringkasanList = ringkasanRepo.findByBapNo(bapNo);

        if (ringkasanList.size() > 0) {
            return ringkasanList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public Ringkasan findByBastpNo(String noTrx) {
        List<Ringkasan> ringkasan = ringkasanRepo.findByBastpNo(noTrx);
        if(!ringkasan.isEmpty()){
            int lastVal = 0;
            if(ringkasan.size() > 1){
                lastVal = ringkasan.size() - 1;
            }
            return ringkasan.get(lastVal);
        }else{
            return null;
        }
    }

    @Override
    public List<Ringkasan> findAll() {
        return ringkasanRepo.findAll();
    }

    @Override
    public List<Ringkasan> findDataLImit1(Pageable pageable,String startDate,String endDate) {
        return ringkasanRepo.findByLimit1(pageable,startDate,endDate);
    }

    @Override
    public List<Ringkasan> findDataByBapId(String bapId) {
        List<Ringkasan> ringkasanList = new ArrayList<>();
        ringkasanList = ringkasanRepo.findByBapId(bapId);
        return ringkasanList;
    }

    @Override
    public List<Ringkasan> findDataMasterTrxId(String masterTrxId) {
        return ringkasanRepo.findByMasterTrxId(masterTrxId);
    }

    @Override
    public List<Ringkasan> findDataRingkasanBapNoLike(String bapNo) {
        return ringkasanRepo.findByBapNo2(bapNo);
    }
}
