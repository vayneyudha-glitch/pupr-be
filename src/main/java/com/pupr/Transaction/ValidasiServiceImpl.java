package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.ValidasiResponseListV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.pupr.Util.CommonParamConstans.TRX_CODE_VALIDASI;

@Service
public class ValidasiServiceImpl implements ValidasiServices{


    @Autowired
    ValidasiDaoService validasiDaoService;

    @Autowired
    BapServices bapServices;

    @Override
    public Validasi submitData(Validasi payload) {
        if (payload.getId() == null){
        }

        if (payload.getVadlidBy1() != null || !payload.getVadlidBy1().isEmpty()){
            payload.setCreatedTimeValid1(new Date());
        }

        List<Bap> bapList = bapServices.findByBapNoV2(payload.getBapNo());
        if(!bapList.isEmpty()) {
            Bap bapDb = bapList.get(0);
            payload.setUraian(bapDb.getKwitansi().getDescription());
        }

        validasiDaoService.submitData(payload);
        bapServices.bapUpdateChildTrxId(Long.valueOf(payload.getBapId()),payload.getId(),TRX_CODE_VALIDASI);

        return payload;
    }

    @Override
    public List<ValidasiResponseListV1> findAllDataV1() {
        List<Validasi> validasiList = validasiDaoService.findAllV1();

        List<ValidasiResponseListV1> responseListV1List = validasiList.stream().map(this::mapperFromEntity).toList();

        return responseListV1List;
    }

    @Override
    public ValidasiResponseListV1 mapperFromBapResponseToValidasiResponse(BapResponse bapResponse) {
        return null;
    }

    private ValidasiResponseListV1 mapperFromEntity(Validasi validasi){
        ValidasiResponseListV1 response = new ValidasiResponseListV1();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        response.setId(validasi.getId());
        response.setValidDate1(formatter.format(validasi.getCreatedTimeValid1()).toString());
        response.setSpmNo(validasi.getSpmNo());
        response.setUraian(validasi.getUraian());

        return response;
    }
}
