package com.pupr.Master;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMasterServicesImpl implements UserMasterServices {

    @Autowired
    UserMasterDaoService masterDaoService;


    @Override
    public UserMaster macthUserNameAndPassword(String userName, String password) {
        UserMaster userMasterResult = new UserMaster();
        UserMaster  userMaster = masterDaoService.findByUserName(userName);
        if (userMaster != null && userMaster.getPassword().equals(password)){
            userMasterResult.setId(userMaster.getId());
            userMasterResult.setUserName(userMaster.getUserName());
            userMasterResult.setPassword(userMaster.getPassword());
            userMasterResult.setBidang(userMaster.getBidang());
        }else {
            
            userMasterResult.setUserName("-");
            userMasterResult.setPassword("-");
            userMasterResult.setBidang("-");
        }


        return userMasterResult;
    }

    @Override
    public List<UserMaster> getAllData() {
        return masterDaoService.findAllData();
    }

    @Override
    public List<UserMaster> getDataByUserName(String userName) {

        List<UserMaster> list = new ArrayList<>();
        list.add(masterDaoService.findByUserName(userName));

        return list;
    }
}
