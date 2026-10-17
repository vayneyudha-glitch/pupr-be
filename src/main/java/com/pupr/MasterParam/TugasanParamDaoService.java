package com.pupr.MasterParam;

import java.util.List;

public interface TugasanParamDaoService {

    MasterTugasanParam submit(MasterTugasanParam param);
    MasterTugasanParam findById (Long id);
    List<MasterTugasanParam> fectchTugasanParamByTugasanCode(String tugasanCode);
    List<MasterTugasanParam> fectchTugasanParamByUrusanCode(String urusanCode);
}
