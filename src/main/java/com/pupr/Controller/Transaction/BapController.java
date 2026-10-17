package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.RemoveFatkturValidationResponse;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Bap;
import com.pupr.Transaction.BapServices;
import com.pupr.Transaction.Ringkasan;
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
@RequestMapping(value = "/Bap")
public class BapController {

    @Autowired
    BapServices bapServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Bap> bapSubmit(@RequestBody Bap payloadRequest) throws Exception, ConflictException {
//        try{
            return ResponseEntity.ok().body(this.bapServices.submit(payloadRequest));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/submitValidItemanis",method = RequestMethod.GET)
    public ResponseEntity<Bap> bapSubmitValidItemanis(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        try{
            return ResponseEntity.ok().body(this.bapServices.updateValidItemanis(params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public ResponseEntity<BapResponse> bapSubmit(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.bapServices.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findByNo",method = RequestMethod.GET)
    public ResponseEntity<BapResponse> findBapByBapNo(@RequestParam String bapNo) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.bapServices.findByBapNo(bapNo));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findByNoV2",method = RequestMethod.GET)
    public ResponseEntity<List<Bap>> findBapByBapNoV2(@RequestParam String bapNo) throws Exception {
//        try{
        return ResponseEntity.ok().body(this.bapServices.findByBapNoV2(bapNo));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<Bap>> findBapByBapNo(@RequestParam Map<String,String> params) throws Exception {
//        try{
            Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.bapServices.findByLimit1(pageable,params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findDataByBapNoLimit",method = RequestMethod.GET)
    public ResponseEntity<List<Bap>> findBapByBapNoLimit(@RequestParam String bapNo) throws Exception {
        try{
            Pageable pageable  = PageRequest.of(0,50, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.bapServices.findByBapNoLimit(bapNo,pageable));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByMasterTrxId",method = RequestMethod.GET)
    public ResponseEntity<Bap> findDataByMasterTrxId(@RequestParam String masterTrxId) throws Exception {
        try{
            return ResponseEntity.ok().body(this.bapServices.findByMasterTrxId(masterTrxId));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByMasterTrxIdV2",method = RequestMethod.GET)
    public ResponseEntity<List<Bap>> findDataByMasterTrxIdV2(@RequestParam String masterTrxId) throws Exception {
        try{
            return ResponseEntity.ok().body(this.bapServices.findByMasterTrxIdV2(masterTrxId));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> removeBapById(@RequestParam Map<String,String> params) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.bapServices.removeByIdV2(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findBapNoHibah",method = RequestMethod.GET)
    public ResponseEntity<Bap> findBapByNoHibah(@RequestParam String noHibah) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.bapServices.findOneBapByHibahNo(noHibah));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/getRealisationReport",method = RequestMethod.GET)
    public ResponseEntity< List<Bap>> getRealisasiReport(@RequestParam Map<String, String> params) throws Exception {
//        try{
        Pageable pageable  = PageRequest.of(0,3000, Sort.by("createdTime"));
        return ResponseEntity.ok().body(this.bapServices.findByLimit1(pageable,params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/getRealisationView",method = RequestMethod.GET)
    public ResponseEntity< List<Bap>> getRealisasiview(@RequestParam Map<String, String> params) throws Exception {
//        try{
        Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
        return ResponseEntity.ok().body(this.bapServices.findByLimit1(pageable,params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/getValidateRemove",method = RequestMethod.GET)
    public ResponseEntity<RemoveFatkturValidationResponse> validateRemove(@RequestParam Map<String, String> params) throws Exception {
//        try{
        return ResponseEntity.ok().body(this.bapServices.removeFakturValidatio(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }


    @RequestMapping(value = "/findByBastp",method = RequestMethod.GET)
    public ResponseEntity<Bap> findByBast(@RequestParam String noTrx) throws Exception {
//        try{
        return ResponseEntity.ok().body(this.bapServices.findOneBapByBastp(noTrx));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    // DISABLED: /submitTestCrs endpoint removed with OpenFeign/Eureka deactivation
    //@RequestMapping(value = "/submitTestCrs",method = RequestMethod.POST)
    //public ResponseEntity<?> submitTestCrs(@RequestBody Bap payloadRequest) throws Exception, ConflictException {
    //    return ResponseEntity.ok().body("DISABLED");
    //}

}
