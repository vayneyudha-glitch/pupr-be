package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.KwitansiResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Bap;
import com.pupr.Transaction.Kwitansi;
import com.pupr.Transaction.KwitansiServices;
import com.pupr.Transaction.Ringkasan;
import net.sf.jasperreports.engine.JRException;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/Kwitansi")
public class KwitansiController {
    @Autowired
    KwitansiServices kwitansiServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<KwitansiResponse> kwitansiSubmit(@RequestBody Kwitansi payloadRequest) throws Exception, ConflictException {
//        try{
            return ResponseEntity.ok().body(this.kwitansiServices.submit(payloadRequest));
//        }catch (ConflictException e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/put",method = RequestMethod.PUT)
    public ResponseEntity<KwitansiResponse> kwitansiSubmitPut(@RequestBody Kwitansi payloadRequest) throws Exception, ConflictException {
        try{
            System.out.println("Udah masuk Put");
            return ResponseEntity.ok().body(this.kwitansiServices.submit(payloadRequest));
        }catch (ConflictException e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findById",method = RequestMethod.GET)
    public ResponseEntity<KwitansiResponse> kwitansiSubmit(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.kwitansiServices.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @RequestMapping(value = "/findByBapNo",method = RequestMethod.GET)
    public ResponseEntity<List<Kwitansi>> getKwitansiTrxByBapNo(@RequestParam String bapNo) throws Exception {
        try{
            return ResponseEntity.ok().body(this.kwitansiServices.findByBapNo(bapNo));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<Kwitansi>> findBapByBapNo(@RequestParam Map<String,String> params ) throws Exception {
        try{
            Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.kwitansiServices.findByLimit1(pageable,params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findDataByLimit1V2",method = RequestMethod.GET)
    public ResponseEntity<List<Bap>> findKwitansiListData(@RequestParam Map<String,String> params ) throws Exception {
        try{
            Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
            return ResponseEntity.ok().body(this.kwitansiServices.findByLimit1V2(pageable,params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> removeKwitansiById(@RequestParam Map<String,String> params) throws Exception {
        try{
            return ResponseEntity.ok().body(this.kwitansiServices.removeById2(params));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/item-report/{id}")
    public ResponseEntity<Resource> getItemReport(@PathVariable Long id)
            throws Exception {

        byte[] reportContent = this.kwitansiServices.generateFaktur(id);
        KwitansiResponse kwitansi = this.kwitansiServices.findById(id);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.inline()
                                .filename("Kwitansi-"+kwitansi.getBap().getBapNo()+".pdf")
                                .build().toString())
                .body(resource);
    }
}

