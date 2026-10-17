package com.pupr.Controller.Master;

import com.pupr.Dto.DashBoardResponse;
import com.pupr.Dto.TransactionRequest.SyncBidangRequest;
import com.pupr.Dto.TransactionResponse.MasterRealisationResponse;
import com.pupr.Transaction.Master;
import com.pupr.Master.MasterServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/Master")
public class MasterController {

    @Autowired
    MasterServices masterServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Master> masterSubmit(@RequestBody Master trxRequest) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.submit(trxRequest));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/submitAll",method = RequestMethod.POST)
    public ResponseEntity<List<Master>> masterSubmitAll(@RequestBody List<Master> trxRequest) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.submitAll(trxRequest));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public ResponseEntity<Master> masterFindById(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

//    @RequestMapping(value = "/getBySingleParam",method = RequestMethod.GET)
//    public ResponseEntity<List<Master>> getBySingleParam(@RequestParam String bidang, @RequestParam String filter2) throws Exception {
//        try{
//            return ResponseEntity.ok().body(this.masterServices.findMasterByBidangAndPekerjaan(bidang,filter2));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<Master> removeById(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.removeById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllByTahunAnggaran",method = RequestMethod.GET)
    public ResponseEntity<List<Master>> getAllByTahunAnggaran(@RequestParam Map<String, String> params) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.findAllByTahunAnggaran(params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getRealisationReport",method = RequestMethod.GET)
    public ResponseEntity<List<MasterRealisationResponse>> getRealisationReport(@RequestParam Map<String, String> params) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.findDataRealisation(params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getMasterTrxList",method = RequestMethod.GET)
    public ResponseEntity<List<Master>> getMasterTrxList(@RequestParam Map<String, String> params) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.masterServices.findDataByBidangYear(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/submitSpkMaster",method = RequestMethod.POST)
    public ResponseEntity<Master> submitSpkMaster(@RequestBody Master trxRequest) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterServices.submitSpkMaster(trxRequest));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getBySpkNo",method = RequestMethod.GET)
    public ResponseEntity<List<Master>> getBySpkNo(@RequestParam String spkNo) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.masterServices.findBySpkNo(spkNo));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/getDashBoardInformation",method = RequestMethod.GET)
    public ResponseEntity<List<DashBoardResponse>> getDashBoardInformation(@RequestParam Map<String, String> params) throws Exception {
//        try{
        return ResponseEntity.ok().body(this.masterServices.getDashBoardInformation(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/syncPekerjaanByBidang", method = RequestMethod.GET)
    public ResponseEntity<List<Master>> syncPekerjaanByBidang(@RequestParam String bidangCode) throws Exception {
//        try {
            return ResponseEntity.ok().body(this.masterServices.syncPekerjaanByBidang(bidangCode));
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/syncPekerjaanByBidangPatch", method = RequestMethod.PATCH)
    public ResponseEntity<List<Master>> syncPekerjaanByBidangPatch(@RequestBody SyncBidangRequest request) throws Exception {
        return ResponseEntity.ok().body(this.masterServices.syncPekerjaanByBidangPatch(request.getBidangCode()));
    }

}
