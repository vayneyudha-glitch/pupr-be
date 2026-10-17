package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class KwitansiDaoServiceImnpl implements KwitansiDaoServices{

    @Autowired
    KwitansiRepo kwitansiRepo;

    @Override
    public Kwitansi submit(Kwitansi kwitansi) {
        kwitansiRepo.save(kwitansi);
        return kwitansi;
    }

    @Override
    public void removeById(Long id) {
        kwitansiRepo.deleteById(id);
    }

    @Override
    public Kwitansi findById(Long id) {
        return kwitansiRepo.findById(id).get();
    }

    @Override
    public Kwitansi findByBapNumber(String bapNo) {
        List<Kwitansi> kwitansiList = new ArrayList<>();

        kwitansiList = kwitansiRepo.findBBapNo(bapNo);

        if (kwitansiList.size() > 0){
            return kwitansiList.get(0);
        }else {
            return null;
        }

    }

    @Override
    public List<Kwitansi> findAllDataByTahun(String tahun) {
        return kwitansiRepo.findAll();
    }

    @Override
    public  List<Kwitansi>  findByBapId(String bapId) {
        return kwitansiRepo.findBBapId(bapId);
    }

    @Override
    public List<Kwitansi> findDataLImit1(Pageable pageable,String startDate, String endDate) {
        return kwitansiRepo.findByLimit1(pageable,startDate,endDate);
    }

    @Override
    public List<Kwitansi> findByBapNoLike(String bapNo) {
        return kwitansiRepo.findBBapNo2(bapNo);
    }
}
