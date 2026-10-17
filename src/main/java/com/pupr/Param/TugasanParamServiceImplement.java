package com.pupr.Param;

import com.pupr.Dto.Param.TugasanParamReq;
import com.pupr.Dto.Param.TugasanParamResponse;
import com.pupr.MasterParam.MasterTugasanParam;
import com.pupr.MasterParam.TugasanParamDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TugasanParamServiceImplement implements TugasanParamService{

    @Autowired
    TugasanParamDaoService tugasanParamDaoService;


    @Override
    public TugasanParamResponse submitData(TugasanParamReq paramReq) {

        TugasanParamResponse response = new TugasanParamResponse();
        MasterTugasanParam masterTugasanParam = mapperFromTugasanReqToEntity(paramReq);

        response = mapperTugasanParamFromEntityToResponse(tugasanParamDaoService.submit(masterTugasanParam));

        return response;
    }

    @Override
    public TugasanParamResponse editData(TugasanParamReq paramReq) {

        

        return null;
    }

    @Override
    public List<TugasanParamResponse> getTugasanParamByParamCode(String paramCode) {

        List<TugasanParamResponse> result = new ArrayList<>();
        return result;
    }

    @Override
    public List<TugasanParamResponse> getTugasanParamByUrusanCode(String paramCode) {

        List<TugasanParamResponse> result = new ArrayList<>();

        return result;
    }

    private MasterTugasanParam mapperFromTugasanReqToEntity(TugasanParamReq paramReq){

        MasterTugasanParam tugasanParam = new MasterTugasanParam() ;

        tugasanParam.setTugasanKode(paramReq.getTugasanKode());
        tugasanParam.setTugasanName(paramReq.getTugasanName());
        tugasanParam.setUrusanKode(paramReq.getUrusanKode());

        return tugasanParam;
    }

    private TugasanParamResponse mapperTugasanParamFromEntityToResponse(MasterTugasanParam masterTugasanParam){

        TugasanParamResponse tugasanParamResponse = new TugasanParamResponse();

        tugasanParamResponse.setTugasanKode(masterTugasanParam.getTugasanKode());
        tugasanParamResponse.setTugasanName(masterTugasanParam.getTugasanName());
        tugasanParamResponse.setUrusanKode(masterTugasanParam.getUrusanKode());

        return tugasanParamResponse;
    }
}
