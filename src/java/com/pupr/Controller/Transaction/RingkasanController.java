package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.RingkasanResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Ringkasan;
import com.pupr.Transaction.RingkasanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/Ringkasan")
public class RingkasanController {

    @Autowired
    RingkasanService ringkasanService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<RingkasanResponse> ringkasanSubmit(@RequestBody Ringkasan payloadRequest) throws Exception, ConflictException {
//        try{
            return ResponseEntity.ok().body(this.ringkasanService.submit(payloadRequest));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public ResponseEntity<RingkasanResponse> kwitansiSubmit(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.ringkasanService.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<Ringkasan>> findBapByBidangPeriodTrxDate(@RequestParam Map<String,String> params ) throws Exception {
        try{
            Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.ringkasanService.findByLimit1(pageable,params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByLimit1V2",method = RequestMethod.GET)
    public ResponseEntity<List<RingkasanResponse>> findBapByBidangPeriodTrxDateV2(@RequestParam Map<String,String> params ) throws Exception {
//        try{
            Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.ringkasanService.findByLimit1V2(pageable,params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/findDataByBapId",method = RequestMethod.GET)
    public ResponseEntity<RingkasanResponse> findBapByBapId(@RequestParam String bapId) throws Exception {
        try{
            return ResponseEntity.ok().body(this.ringkasanService.findByBapIdSecond(bapId));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByBapNo",method = RequestMethod.GET)
    public ResponseEntity<List<Ringkasan>> findBapByBapNo(@RequestParam String bapNo) throws Exception {
        try{
            return ResponseEntity.ok().body(this.ringkasanService.findByBapNo(bapNo));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByMasterTrxId",method = RequestMethod.GET)
    public ResponseEntity<Ringkasan> findBapByMasterTrxId(@RequestParam String masterId) throws Exception {
        try{
            return ResponseEntity.ok().body(this.ringkasanService.findByMasterTrxId(masterId));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> removeRingkasanById(@RequestParam Map<String,String> params) throws Exception {
        try{
//            return ResponseEntity.ok().body(this.ringkasanService.removeById(id));
            return ResponseEntity.ok().body(this.ringkasanService.removeByIdV2(params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/item-report/{id}")
    public ResponseEntity<Resource> getItemReport(@PathVariable Long id)
            throws Exception {

        System.out.println("Ini Id " + id);
        RingkasanResponse ringkasan = this.ringkasanService.findById(id);
        byte[] reportContent = this.ringkasanService.generateFaktur(id);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("Ringkasan-Hal1-"+ringkasan.getBap().getBapNo()+".pdf")
                                .build().toString())
                .body(resource);
    }

    @GetMapping("/RekapReportV1/{id}")
    public ResponseEntity<Resource> getiRingkasanRekapReportV1(@PathVariable Long id)
            throws Exception {

        System.out.println("Ini Id " + id);
        RingkasanResponse ringkasan = this.ringkasanService.findById(id);
        byte[] reportContent = this.ringkasanService.generateFakturRingkasanRekapV1(id);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("Ringkasan-Hal2-"+ringkasan.getBap().getBapNo()+".pdf")
                                .build().toString())
                .body(resource);
    }
}
