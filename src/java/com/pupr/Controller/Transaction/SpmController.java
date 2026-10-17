package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.SpmDtoResponse;
import com.pupr.Dto.TransactionResponse.ValidasiInfoDetailResponseV1;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Spm;
import com.pupr.Transaction.SpmServices;
import com.pupr.Transaction.Spp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/Spm")
public class SpmController {

    @Autowired
    SpmServices spmServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Spm> spmSubmit(@RequestBody Spm payloadRequest) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.spmServices.submitData(payloadRequest));
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> spmSoftRemoveById(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.spmServices.removeById(params));
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<SpmDtoResponse>> findDataByTglTrxPeriod(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
        return ResponseEntity.ok().body(this.spmServices.getSppTrxByDatePeriode(pageable,params));
    }

    @RequestMapping(value = "/findBySpmNo",method = RequestMethod.GET)
    public ResponseEntity<Spm> sppFindBySpmNo(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.spmServices.findBySpmNo(params.get("spmNo")));
    }

    @RequestMapping(value = "/findBySpmNoV2",method = RequestMethod.GET)
    public ResponseEntity<BapResponse> findBySpmNoV2(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.spmServices.findcompleteDataBySppId(params.get("spmNo")));
    }

    @RequestMapping(value = "/findBySpmNoValidInput",method = RequestMethod.GET)
    public ResponseEntity<ValidasiInfoDetailResponseV1> findBySpmNoValidInput(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.spmServices.findSpmNoForValidInput(params.get("spmNo")));
    }

}
