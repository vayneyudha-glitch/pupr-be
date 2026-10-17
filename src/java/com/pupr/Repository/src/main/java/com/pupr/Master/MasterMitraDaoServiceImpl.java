package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MasterMitraDaoServiceImpl implements MasterMitraDaoServices{

    @Autowired
    MasterMitraRepo masterMitraRepo;

    @Override
    public MasterMitra submit(MasterMitra payload) {
        masterMitraRepo.save(payload);
        return payload;
    }

    @Override
    public Optional<MasterMitra> getById(Long id) {
        return masterMitraRepo.findById(id);
    }

    @Override
    public List<MasterMitra> getByCorporateName(String name) {
        return masterMitraRepo.findByCorporateName(name);
    }

    @Override
    public List<MasterMitra> findAllData() {
        return masterMitraRepo.findAll();
    }

    @Override
    public Optional<MasterMitra> getByNpwp(String npwp) {
        return Optional.empty();
    }
}
