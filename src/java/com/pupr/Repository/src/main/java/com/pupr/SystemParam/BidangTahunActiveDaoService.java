package com.pupr.SystemParam;

import java.util.List;
import java.util.Optional;

public interface BidangTahunActiveDaoService {

    BidangTahunActive Submit(BidangTahunActive data);
    Optional<BidangTahunActive> findByBidangName(String bidangName);
    List<BidangTahunActive> getAllData();
}
