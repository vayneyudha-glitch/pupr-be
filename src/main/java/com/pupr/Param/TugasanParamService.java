package com.pupr.Param;

import com.pupr.Dto.Param.TugasanParamReq;
import com.pupr.Dto.Param.TugasanParamResponse;

import java.util.List;

public interface TugasanParamService {

    TugasanParamResponse submitData(TugasanParamReq paramReq);
    TugasanParamResponse editData(TugasanParamReq paramReq);

    List<TugasanParamResponse> getTugasanParamByParamCode(String paramCode);
    List<TugasanParamResponse> getTugasanParamByUrusanCode(String paramCode);
}
