package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.KwitansiResponse;
import com.pupr.Dto.TransactionResponse.ValidasiResponseListV1;
import com.pupr.Transaction.Kwitansi;
import com.pupr.Transaction.Validasi;
import com.pupr.Transaction.ValidasiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/Validasi")
public class ValidasiController {

    @Autowired
    ValidasiServices services;


    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Validasi> validasiSubmit(@RequestBody Validasi payloadRequest) throws Exception, ConflictException {
//        try{
        return ResponseEntity.ok().body(this.services.submitData(payloadRequest));
//        }catch (ConflictException e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findAllV1",method = RequestMethod.GET)
    public ResponseEntity<List<ValidasiResponseListV1>> findAllDataV1() throws Exception, ConflictException {
//        try{
        return ResponseEntity.ok().body(this.services.findAllDataV1());
//        }catch (ConflictException e){
//            throw new Exception(e.getMessage());
//        }
    }
}
