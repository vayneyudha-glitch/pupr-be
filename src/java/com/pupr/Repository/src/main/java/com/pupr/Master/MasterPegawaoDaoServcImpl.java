package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MasterPegawaoDaoServcImpl implements MasterPegawaiDaoService{

    @Autowired
    MasterPegawaiRepo masterPegawaiRepo;

    @Override
    public MasterPegawai submit(MasterPegawai payload) {
        masterPegawaiRepo.save(payload);
        return payload;
    }

    @Override
    public List<MasterPegawai> submitAll(List<MasterPegawai> payload) {

        return masterPegawaiRepo.findAll();
    }

    @Override
    public List<MasterPegawai> findAll() {
        return masterPegawaiRepo.findAll();
    }

    @Override
    public Optional<MasterPegawai> getById(Long id) {
        return masterPegawaiRepo.findById(id);
    }

    @Override
    public List<MasterPegawai> getByName(String name) {
        return masterPegawaiRepo.findByName(name);
    }

    @Override
    public Optional<MasterPegawai> getByNpwp(String npwp) {
        return masterPegawaiRepo.findByNpwp(npwp);
    }
}
