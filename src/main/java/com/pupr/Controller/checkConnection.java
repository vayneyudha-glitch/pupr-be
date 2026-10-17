package com.pupr.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/checkConnection")
public class checkConnection {
    @RequestMapping(value = "/ping",method = RequestMethod.POST)
    public ResponseEntity<String> bapSubmit() throws Exception {
        try{
            return ResponseEntity.ok().body("Connection Success");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
