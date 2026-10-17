package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MasterMitraServiceImpl implements MasterMitraService {

    @Autowired
    MasterMitraDaoServices masterMitraDaoServices;


    @Override
    public MasterMitra submit(MasterMitra payload) throws Exception {

        if (payload.getId() == null) {
            try {
                masterMitraDaoServices.submit(payload);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }

        }else {
            MasterMitra masterMitra = masterMitraDaoServices.getById(payload.getId()).get();

            masterMitra.setCorporateName(payload.getCorporateName());
            masterMitra.setHeadOffice(payload.getHeadOffice());
            masterMitra.setAddress(payload.getAddress());
            masterMitra.setNpwp(payload.getNpwp());
            masterMitra.setRekeningNumber(payload.getRekeningNumber());
            masterMitra.setBankAccountName(payload.getBankAccountName());
            masterMitra.setBankAccountType(payload.getBankAccountType());

            try {
                masterMitraDaoServices.submit(masterMitra);
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }

        return payload;
    }

    @Override
    public List<MasterMitra> submitAll(List<MasterMitra> payload) throws Exception {
        return null;
    }

    @Override
    public MasterMitra findById(Long id) throws Exception {
        return masterMitraDaoServices.getById(id).get();
    }

    @Override
    public List<MasterMitra> findByCorporateName(String name) throws Exception {
        return masterMitraDaoServices.getByCorporateName(name);
    }

    @Override
    public List<MasterMitra> getAllData() throws Exception {
        return masterMitraDaoServices.findAllData();
    }

    @Override
    public MasterMitra findByNpwp(String npwp) throws Exception {
        return null;
    }

    @Override
    public MasterMitra removeById(Long id) throws Exception {
        Optional<MasterMitra> mitraDb = masterMitraDaoServices.getById(id);
        if(mitraDb.isPresent()){
            MasterMitra masterMitra = mitraDb.get();
//            masterMitra.set
        }

        return null;
    }
}
