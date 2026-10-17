package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class SppDaoSericesImpl implements  SppDaoServices{

    @Autowired
    SppRepo sppRepo;

    @Override
    public Spp submitData(Spp paylaodData) {
        return sppRepo.save(paylaodData);
    }

    @Override
    public void removeDataById(Long id) {
        sppRepo.deleteById(id);
    }

    @Override
    public Optional<Spp> findById(Long id) {
        return sppRepo.findById(id);
    }

    @Override
    public List<Spp> findAllData() {
        return sppRepo.findAll();
    }

    @Override
    public  List<Spp> findByBapNo(String bapNo) {
        return sppRepo.getByBapNo(bapNo);
    }

    @Override
    public Spp findBySppNo(String sppNo) {
        Optional <Spp> sppDb = sppRepo.getBySppNo(sppNo);

        return (sppDb.isPresent() ? sppDb.get() : null);
    }

    @Override
    public List<Spp> findBySppNoV2(String sppNo) {
        return sppRepo.getBySppNoV2(sppNo);
    }

    @Override
    public List<Spp> findSppByDatePeriode(Pageable pageable, String startDate, String endDate) {
        return sppRepo.getByFilterDateTrxPeriode(pageable,startDate,endDate);
    }

    @Override
    public List<Spp> findSppBySppDate(Pageable pageable, String startDate, String endDate) {
        return sppRepo.getBySppDate(pageable,startDate,endDate);
    }
}
