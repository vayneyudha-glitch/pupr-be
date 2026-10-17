package com.pupr.Controller.Master;

import com.pupr.Master.UserMaster;
import com.pupr.Master.UserMasterServices;
import com.pupr.Transaction.Master;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping(value = "/UserMaster")
public class UserMasterController {

    @Autowired
    UserMasterServices userMasterServices;


    @RequestMapping(value = "/getMatchLogin",method = RequestMethod.GET)
    public ResponseEntity<UserMaster> masterFindById(@RequestParam Map<String,String> params) throws Exception {
        try{
            return ResponseEntity.ok().body(this.userMasterServices.macthUserNameAndPassword(params.get("userName"),params.get("password")));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getUserByUserName",method = RequestMethod.GET)
    public ResponseEntity<List<UserMaster>> UserMasterFindByUserName(@RequestParam Map<String,String> params) throws Exception {
        try{
            return ResponseEntity.ok().body(this.userMasterServices.getDataByUserName(params.get("userName")));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
    public ResponseEntity<List<UserMaster>> masterFindById() throws Exception {
        try{
            return ResponseEntity.ok().body(this.userMasterServices.getAllData());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
