package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MasterPegawaiServicesImpl implements MasterPegawaiService {

    @Autowired
    MasterPegawaiDaoService masterPegawaiDaoService;

    @Override
    public MasterPegawai submit(MasterPegawai payload) throws Exception {
        Optional<MasterPegawai> masterPegawaiDb = masterPegawaiDaoService.getByNpwp(payload.getNpwp());
        if (masterPegawaiDb.get() ==  null) {
            return masterPegawaiDaoService.submit(payload);
        }else{
            throw new Exception("Data Already Exist");
        }
    }

    @Override
    public List<MasterPegawai> getAll() throws Exception {
        return masterPegawaiDaoService.findAll();
    }

    @Override
    public List<MasterPegawai> submitAll(List<MasterPegawai> payload) throws Exception {
        return null;
    }

    @Override
    public MasterPegawai findById(Long id) throws Exception {
        return masterPegawaiDaoService.getById(id).get();
    }

    @Override
    public List<MasterPegawai> findByName(String name) throws Exception {
        return masterPegawaiDaoService.getByName(name);
    }

    @Override
    public MasterPegawai findByNpwp(String npwp) throws Exception {
        MasterPegawai masterPegawai = new MasterPegawai();
        Optional<MasterPegawai> masterPegawaiResult = masterPegawaiDaoService.getByNpwp(npwp);

        if(masterPegawaiResult.get() != null){
            masterPegawai = masterPegawaiResult.get();
        }
        return masterPegawai;
    }
}
