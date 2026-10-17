package com.pupr.Controller.Transaction;

import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Sp2D;
import com.pupr.Transaction.Sp2dServices;
import com.pupr.Transaction.Spm;
import org.hibernate.sql.Delete;
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
@RequestMapping(value = "/Sp2d")
public class Sp2dController {

    @Autowired
    Sp2dServices sp2dServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<Sp2D> spmSubmit(@RequestBody Sp2D payloadRequest) throws Exception, ConflictException {
        System.out.println("Masuk LINE 28 sp2d controller" + payloadRequest.getSp2dNo());
        return ResponseEntity.ok().body(this.sp2dServices.submitData(payloadRequest));
    }

    @RequestMapping(value = "/findDataByLimit1",method = RequestMethod.GET)
    public ResponseEntity<List<Sp2D>> findDataByTglTrxPeriod(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        Pageable pageable  = PageRequest.of(0,1000, Sort.by("createdTime"));
        return ResponseEntity.ok().body(this.sp2dServices.getSppTrxByDatePeriode(pageable,params));
    }

    @RequestMapping(value = "/findBySp2dNo",method = RequestMethod.GET)
    public ResponseEntity<Sp2D> sppFindBySp2dNo(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.sp2dServices.findBySp2dNo(params.get("sp2dNo")));
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<DeletedCollection> sp2dSoftRemoveById(@RequestParam Map<String,String> params) throws Exception, ConflictException {
        return ResponseEntity.ok().body(this.sp2dServices.removeById(params));
    }

}
