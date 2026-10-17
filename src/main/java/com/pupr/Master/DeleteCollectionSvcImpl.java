package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeleteCollectionSvcImpl implements DeleteCollectionServices{


    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Override
    public DeletedCollection Submit(DeletedCollection collection) {

        return collectionDaoServices.Submit(collection);
    }
}
