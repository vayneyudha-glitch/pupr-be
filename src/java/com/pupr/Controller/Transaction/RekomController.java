package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.RekomResponse;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Rekom;
import com.pupr.Transaction.RekomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/Rekom")
public class RekomController {

    @Autowired
    RekomServices rekomServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Rekom> bapSubmit(@RequestBody Rekom payloadRequest) throws Exception, ConflictException {
        try{
            return ResponseEntity.ok().body(this.rekomServices.Submit(payloadRequest));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findAllV1",method = RequestMethod.GET)
    public ResponseEntity<List<RekomResponse>> bapSubmit(@RequestParam Map<String,String> params) throws Exception, ConflictException {
//        try{
            return ResponseEntity.ok().body(this.rekomServices.FindAllDataWithoutFilter(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> removeById(@RequestParam Map<String,String> params) throws Exception, ConflictException {
//        try{
        return ResponseEntity.ok().body(this.rekomServices.RemoveById(params));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }



}
