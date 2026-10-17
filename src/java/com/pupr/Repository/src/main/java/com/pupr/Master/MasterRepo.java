package com.pupr.Master;

import com.pupr.Transaction.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterRepo extends JpaRepository<Master, Long> {

    @Query("SELECT e FROM Master e WHERE LOWER(e.bidang) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Master> findBySingleParam(String param);
    @Query("SELECT e FROM Master e WHERE LOWER(e.bidang) LIKE LOWER(CONCAT('%', ?1, '%')) AND e.tahun = ?2")
    List<Master> findBySingleParamV2(String param,int tahun);

    @Query("SELECT e FROM Master e WHERE LOWER(e.bidang) LIKE LOWER(CONCAT('%', ?1, '%')) AND LOWER(e.pekerjaan) LIKE LOWER(CONCAT('%', ?2, '%')) AND e.tahun = ?3")
    List<Master> findMasterDataByBidangAndPekerjaan(String bidang, String pekerjaan, int tahun);

    @Query("SELECT e FROM Master e WHERE LOWER(e.bidang) LIKE LOWER(CONCAT('%', ?1, '%')) AND e.id IN ?2")
    List<Master> findByListMasterTrxId(String bidang,List<Long> masterTrxId);

    @Query("SELECT e FROM Master e WHERE e.id IN ?1")
    List<Master> findByListMasterTrxIdWithoutBidang(List<Long> masterTrxId);

    @Query("SELECT e FROM Master e WHERE LOWER(e.bidang) LIKE LOWER(CONCAT('%', ?1, '%')) AND e.tahun = ?2")
    List<Master> findMasterDataByBidang1(String bidang,int tahun);

    @Query("SELECT e FROM Master e WHERE e.no = ?1")
    List<Master> findByNoTrx(Integer no);

    @Query("SELECT e FROM Master e WHERE LOWER(e.spkNo) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Master> findBySpkNo(String param);

    @Query("SELECT e FROM Master e WHERE e.bidang = ?1 AND e.pekerjaan = ?2 AND e.tahun = ?3")
    List<Master> findByBidangAndPekejaan(String bidangCode, String pekerjaan, int tahun);

//    @Query("SELECT e FROM Master e WHERE e.id IN ?2")
//    List<Master> findByListMasterTrxId(String bidang,List<Long> masterTrxId);

}
