package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.ValidasiResponseListV1;

import java.util.List;

public interface ValidasiServices {
    Validasi submitData(Validasi payload);
    List<ValidasiResponseListV1> findAllDataV1();

    ValidasiResponseListV1 mapperFromBapResponseToValidasiResponse(BapResponse bapResponse);
}
