package com.pupr.Controller.Master;

import com.pupr.Master.DeleteCollectionServices;
import com.pupr.Master.DeletedCollection;
import com.pupr.Transaction.Master;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(value = "/DeleteCollection")
public class DeleteCollectionController {

    @Autowired
    DeleteCollectionServices collectionServices;

    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    public ResponseEntity<DeletedCollection> masterSubmit(@RequestBody DeletedCollection collection) throws Exception {
        try{
            return ResponseEntity.ok().body(collectionServices.Submit(collection));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
