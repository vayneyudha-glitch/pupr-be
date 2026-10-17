package com.pupr.Master;

import java.util.List;

public interface UserMasterServices {
    UserMaster macthUserNameAndPassword(String userName,String password);

    List<UserMaster> getAllData();
    List<UserMaster> getDataByUserName(String userName);
}
