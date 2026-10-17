package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.RingkasanResponse;
import com.pupr.Dto.TransactionResponse.SppDtoResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Spp;
import com.pupr.Transaction.SppServices;
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
@RequestMapping(value = "/Spp")
public class SppController {

    @Autowired
    SppServices sppServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Spp> sppSubmit(@RequestBody Spp payloadRequest) throws Exception, ConflictException {
//        try{
//            System.out.println("Udah masuk Put");
            return ResponseEntity.ok().body(this.sppServices.submitData(payloadRequest));
//        }catch (ConflictException e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> sppSoftRemoveById(@RequestParam Map<String,String> param) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.sppServices.submitRemoveDataV2(param));
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<SppDtoResponse>> findDataByTglTrxPeriod(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
        return ResponseEntity.ok().body(this.sppServices.getSppTrxByDatePeriode(pageable,params));
    }

    @RequestMapping(value = "/findBySppNo",method = RequestMethod.GET)
    public ResponseEntity<Spp> sppFindBySppNo(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.sppServices.findBySppNo(params.get("sppNo")));
    }

    @RequestMapping(value = "/findBySppNoV2",method = RequestMethod.GET)
    public ResponseEntity<BapResponse> findBySppNoV2(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.sppServices.findcompleteDataBySppId(params.get("sppNo")));
    }

    @RequestMapping(value = "/findDataByBapNo",method = RequestMethod.GET)
    public ResponseEntity<List<Spp>> findBapByBapNo(@RequestParam String bapNo) throws Exception {
        try{
            return ResponseEntity.ok().body(this.sppServices.findByBapNo(bapNo));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
