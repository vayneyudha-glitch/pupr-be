package com.pupr.Controller.Master;

import com.pupr.Master.MasterMitra;
import com.pupr.Master.MasterMitraService;
import com.pupr.Master.MasterPegawai;
import com.pupr.Transaction.Kwitansi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@RequestMapping(value = "/MasterMitra")
public class MasterMitraController {

    @Autowired
    MasterMitraService masterMitraService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<MasterMitra> masterSubmit(@RequestBody MasterMitra reqPayload) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterMitraService.submit(reqPayload));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getByName",method = RequestMethod.GET)
    public ResponseEntity<List<MasterMitra>> getBySingleParam(@RequestParam String corporateName) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterMitraService.findByCorporateName(corporateName));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResponseEntity<List<MasterMitra>> getAllData() throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterMitraService.getAllData());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findId",method = RequestMethod.GET)
    public ResponseEntity<MasterMitra> findByIf(@RequestParam Long id) throws Exception {
//        try{
            return ResponseEntity.ok().body(this.masterMitraService.findById(id));
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
    }

    @RequestMapping(value = "/removeById",method = RequestMethod.PUT)
    public ResponseEntity<MasterMitra> removeMitraById(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterMitraService.removeById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
