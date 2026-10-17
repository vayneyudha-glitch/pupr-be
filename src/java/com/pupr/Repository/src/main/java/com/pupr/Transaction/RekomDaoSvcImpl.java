package com.pupr.Transaction;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RekomDaoSvcImpl implements RekomDaoServices{

    @Autowired
    RekomRepo rekomRepo;

    @Override
    public void Submit(Rekom rekom) {
        rekomRepo.save(rekom);
    }

    @Override
    public void RemoveById(Long id) {
        rekomRepo.deleteById(id);
    }

    @Override
    public Rekom findByNoBastp(String noBastp) {
        Optional<Rekom> rekom = rekomRepo.findOneByNoBastp(noBastp);

        if(rekom.isPresent()){
            return rekom.get();
        }else{
            return null;
        }

    }

    @Override
    public Rekom findOneByNoRekom(String noRekom) {

        Optional<Rekom> rekom = rekomRepo.findOneByNoRekom(noRekom);
        if(rekom.isPresent()){
            return rekom.get();
        }else{
            return null;
        }

    }

    @Override
    public List<Rekom> findOneByNoRekomV2(String noRekom) {
        return rekomRepo.findOneByNoRekomV2(noRekom);
    }

    @Override
    public List<Rekom> findAllDataV1(String startDate, String endDate) {

        return rekomRepo.findByLimit1(startDate,endDate);
    }

    @Override
    public List<Rekom> findAllDataNoFilter() {
        return rekomRepo.findAll();
    }
}
