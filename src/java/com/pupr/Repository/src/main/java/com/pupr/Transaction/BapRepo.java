package com.pupr.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface BapRepo extends JpaRepository<Bap, Long> {
    @Query("SELECT e FROM Bap e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Bap findByBapNo(String bapNp);

    @Query("SELECT e FROM Bap e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL) AND e.createdDate > ?2")
    Bap findByBapNoV2WithDate(String bapNo, String dateParam);

    @Query("SELECT e FROM Bap e WHERE LOWER(e.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL) AND e.createdDate > ?2")
    List<Bap> findByBapNoV2(String bapNp,String dateParam);

    @Query("SELECT e FROM Bap e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Bap> findByLimit1(Pageable pageable,String startDate,String endDate);

    @Query("SELECT e FROM Bap e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.bapDateText >= ?1 AND e.bapDateText <= ?2)")
    List<Bap> findByBapDate(Pageable pageable,String startDate,String endDate);

    @Query("SELECT e FROM Bap e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.bapDateText >= ?1 AND e.bapDateText <= ?2)")
    List<Bap> findByBapDateV2(String startDate,String endDate);

    @Query("SELECT e FROM Bap e WHERE LOWER(e.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Bap> findByBapNoLimit(String bapNp,Pageable pageable);

    @Query("SELECT e FROM Bap e WHERE e.masterTransactionId = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Bap> findByMasterTrxId(String id);

    @Query("SELECT e FROM Bap e WHERE e.createdDate > ?1")
    List<Bap> findByCreatedDateAfter(String date);

    @Query("SELECT e FROM Bap e WHERE (e.isDeleted = false OR e.isDeleted IS NULL)")
    Page<Bap> findPageAbleBapTrx(Pageable pageable);

//    @Query("SELECT e FROM Bap e WHERE e.paymentId = '95%' AND e.paymentNo = ?1 AND ((e.isDeleted = false OR e.isDeleted IS NULL))")
    @Query("SELECT e FROM Bap e WHERE e.paymentNo = ?1")
    Optional <Bap> findOneBayByHibahNo(String no);
}
