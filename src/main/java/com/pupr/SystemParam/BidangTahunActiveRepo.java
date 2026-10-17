package com.pupr.SystemParam;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidangTahunActiveRepo extends JpaRepository<BidangTahunActive, Long> {

    @Query("SELECT e FROM BidangTahunActive e WHERE e.bidangDesc = ?1")
    Optional<BidangTahunActive> findDataByBidangName(String bidangName);
}
