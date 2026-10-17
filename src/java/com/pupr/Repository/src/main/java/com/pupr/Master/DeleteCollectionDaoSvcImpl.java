package com.pupr.Master;

import org.springframework.beans.factory.annotation.Autowired;

public class DeleteCollectionDaoSvcImpl implements DeletedCollectionDaoServices {

    @Autowired
    DeletedCollectionRepo deletedRepo;

    @Override
    public DeletedCollection Submit(DeletedCollection deletedCollection) {
        System.out.println(deletedCollection.getId());
        deletedRepo.save(deletedCollection);
        return deletedCollection;
    }
}
