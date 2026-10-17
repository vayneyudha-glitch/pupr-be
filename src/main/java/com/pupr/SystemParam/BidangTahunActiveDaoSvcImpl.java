package com.pupr.SystemParam;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BidangTahunActiveDaoSvcImpl implements BidangTahunActiveDaoService {

    @Autowired
    BidangTahunActiveRepo repo;

    @Override
    public BidangTahunActive Submit(BidangTahunActive data) {
        return repo.save(data);
    }

    @Override
    public Optional<BidangTahunActive> findByBidangName(String bidangName) {
        return repo.findDataByBidangName(bidangName);
    }

    @Override
    public List<BidangTahunActive> getAllData() {
        return repo.findAll();
    }
}
