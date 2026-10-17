package com.pupr.Controller.Master;


import com.pupr.Master.MasterPegawai;
import com.pupr.Master.MasterPegawaiService;
import com.pupr.Util.CommonParamConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/MasterPegawai")

public class MasterPegawaiController {

    @Autowired
    MasterPegawaiService masterPegawaiService;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<MasterPegawai> masterSubmit(@RequestBody MasterPegawai reqPayload) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterPegawaiService.submit(reqPayload));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public ResponseEntity<MasterPegawai> masterPegawaiFindById(@RequestParam Long id) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterPegawaiService.findById(id));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getBySingleParam",method = RequestMethod.GET)
    public ResponseEntity<List<MasterPegawai>> getBySingleParam(@RequestParam String name) throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterPegawaiService.findByName(name));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public ResponseEntity<List<MasterPegawai>> findAll() throws Exception {
        try{
            return ResponseEntity.ok().body(this.masterPegawaiService.getAll());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
