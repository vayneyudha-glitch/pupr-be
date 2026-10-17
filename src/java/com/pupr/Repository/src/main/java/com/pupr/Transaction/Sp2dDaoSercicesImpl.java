package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class Sp2dDaoSercicesImpl implements Sp2dDaoServices{

    @Autowired
    Sp2dRepo sp2dRepo;


    @Override
    public Sp2D submitData(Sp2D paylaodData) {
        return sp2dRepo.save(paylaodData);
    }

    @Override
    public void RemoveById(Long id) {
        sp2dRepo.deleteById(id);
    }

    @Override
    public List<Sp2D> findAllData() {
        return sp2dRepo.findAll();
    }

    @Override
    public Optional<Sp2D> findById(Long id) {
        return sp2dRepo.findById(id);
    }

    @Override
    public Sp2D findByBapNo(String bapNo) {
        Optional<Sp2D> sp2dDb = sp2dRepo.getByBapNo(bapNo);

        return (sp2dDb.isPresent() ? sp2dDb.get() : null);
    }

    @Override
    public Sp2D findBySp2dNo(String sp2dNo) {
        Optional<Sp2D> sp2dDb = sp2dRepo.getBySp2dNo(sp2dNo);

        return (sp2dDb.isPresent() ? sp2dDb.get() : null);
    }

    @Override
    public List<Sp2D> findSp2dByDatePeriode(Pageable pageable, String startDate, String endDate) {
        return sp2dRepo.getByFilterDateTrxPeriodeV2(pageable,startDate,endDate);
    }
}
