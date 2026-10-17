package com.pupr.MasterParam;

import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class TugasanParamDaoServiceImpl implements TugasanParamDaoService{

    @Autowired
    TugasanParamRepo tugasanParamRepo;

    @Override
    public MasterTugasanParam submit(MasterTugasanParam param) {

        tugasanParamRepo.save(param);
        return param;
    }

    @Override
    public MasterTugasanParam findById(Long id) {

        Optional<MasterTugasanParam> tugasanParam = tugasanParamRepo.findById(id);

        if(tugasanParam.isPresent()){
            return tugasanParam.get();
        }else{
            return null;
        }
    }

    @Override
    public List<MasterTugasanParam> fectchTugasanParamByTugasanCode(String tugasanCode) {

        return tugasanParamRepo.findMasterTugasanParamByTugasanCode(tugasanCode);
    }

    @Override
    public List<MasterTugasanParam> fectchTugasanParamByUrusanCode(String urusanCode) {
        return tugasanParamRepo.findMasterTugasanParamByUrusanCode(urusanCode);
    }
}
