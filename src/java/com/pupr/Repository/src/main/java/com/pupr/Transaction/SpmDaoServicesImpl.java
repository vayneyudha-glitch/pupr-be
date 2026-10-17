package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class SpmDaoServicesImpl implements SpmDaoServices {

    @Autowired
    SpmRepo spmRepo;

    @Override
    public Spm submitData(Spm paylaodData) {
        return spmRepo.save(paylaodData);
    }

    @Override
    public void RemoveById(Long id) {
        spmRepo.deleteById(id);
    }

    @Override
    public List<Spm> findAllData() {
        return spmRepo.findAll();
    }

    @Override
    public Optional<Spm> findById(Long id) {
        return spmRepo.findById(id);
    }

    @Override
    public Spm findByBapNo(String bapNo) {

        Optional<Spm> spmDb = spmRepo.getByBapNo(bapNo);

        return (spmDb.isPresent() ? spmDb.get() : null);
    }

    @Override
    public List<Spm> findBySpmNoV2(String spmNo) {
        return spmRepo.getBySpmNoV2(spmNo);
    }

    @Override
    public Spm findBySpmNo(String spmNo) {
        Optional<Spm> spmDb = spmRepo.getBySpmNo(spmNo);
        return (spmDb.isPresent() ? spmDb.get() : null);
    }

    @Override
    public List<Spm> findSppByDatePeriode(Pageable pageable, String startDate, String endDate) {
        return spmRepo.getByFilterDateTrxPeriode(pageable,startDate,endDate);
    }

    @Override
    public List<Spm> findSppBySpmDatePeriode(Pageable pageable, String startDate, String endDate) {
        return spmRepo.getByFilterSpmDateTrxPeriode(pageable,startDate,endDate);
    }
}
