package com.pupr.Master;


import java.util.List;

public interface UserMasterDaoService {
    UserMaster findByUserName(String userName);

    List<UserMaster> findAllData();
}
