package com.pupr.Master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterRepo extends JpaRepository<UserMaster, Long> {

    @Query("SELECT e FROM UserMaster e WHERE e.userName = ?1")
    UserMaster findByUserName(String userName);

}
