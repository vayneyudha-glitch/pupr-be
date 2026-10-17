package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ValidasiDaoServicesImpl implements ValidasiDaoService{

    @Autowired
    ValidasiRepo validasiRepo;

    @Override
    public Validasi submitData(Validasi paylaodData) {
        return validasiRepo.save(paylaodData);
    }

    @Override
    public List<Validasi> findAllV1() {
        return validasiRepo.findAll();
    }
}
