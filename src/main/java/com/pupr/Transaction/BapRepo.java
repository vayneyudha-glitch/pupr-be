package com.pupr.Transaction;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BapRepo extends JpaRepository<Bap, Long> {

    @Query("SELECT b FROM Bap b WHERE b.bapNo = ?1 AND (b.isDeleted = false OR b.isDeleted IS NULL)")
    Bap findByBapNo(String bapNp);

    @Query("SELECT b FROM Bap b WHERE b.bapNo = ?1 AND (b.isDeleted = false OR b.isDeleted IS NULL) AND b.createdDate > ?2")
    Bap findByBapNoV2WithDate(String bapNo, String dateParam);

    @Query("SELECT b FROM Bap b WHERE LOWER(b.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (b.isDeleted = false OR b.isDeleted IS NULL) AND b.createdDate > ?2")
    List<Bap> findByBapNoV2(String bapNp, String dateParam);

    // Menggunakan @Param agar Pageable di depan tetap aman dan terpetakan dengan benar
    @Query("SELECT b FROM Bap b WHERE (b.isDeleted = false OR b.isDeleted IS NULL) AND b.createdDate >= :startDate AND b.createdDate < :endDate")
    List<Bap> findByLimit1(Pageable pageable, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT b FROM Bap b WHERE (b.isDeleted = false OR b.isDeleted IS NULL) AND b.bapDateText >= :startDate AND b.bapDateText <= :endDate")
    List<Bap> findByBapDate(Pageable pageable, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT b FROM Bap b WHERE (b.isDeleted = false OR b.isDeleted IS NULL) AND b.bapDateText >= ?1 AND b.bapDateText <= ?2")
    List<Bap> findByBapDateV2(String startDate, String endDate);

    @Query("SELECT b FROM Bap b WHERE LOWER(b.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (b.isDeleted = false OR b.isDeleted IS NULL)")
    List<Bap> findByBapNoLimit(String bapNp, Pageable pageable);

    @Query("SELECT b FROM Bap b WHERE b.masterTransactionId = ?1 AND (b.isDeleted = false OR b.isDeleted IS NULL)")
    List<Bap> findByMasterTrxId(String id);

    @Query("SELECT b FROM Bap b WHERE b.createdDate > ?1")
    List<Bap> findByCreatedDateAfter(String date);

    @Query("SELECT b FROM Bap b WHERE (b.isDeleted = false OR b.isDeleted IS NULL)")
    Page<Bap> findPageAbleBapTrx(Pageable pageable);

    @Query("SELECT b FROM Bap b WHERE b.paymentMethodId = ?1")
    Optional<Bap> findOneBayByHibahNo(String no);
}