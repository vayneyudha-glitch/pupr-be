package com.pupr.Master;


import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserMasterDaoSerciveImpl implements UserMasterDaoService {

    @Autowired
    UserMasterRepo userMasterRepo;

    @Override
    public UserMaster findByUserName(String userName) {
        return userMasterRepo.findByUserName(userName);
    }

    @Override
    public List<UserMaster> findAllData() {
        return userMasterRepo.findAll();
    }
}
