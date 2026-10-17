package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RingkasanRepo extends JpaRepository<Ringkasan, Long> {

    @Query("SELECT r FROM Ringkasan r WHERE (r.isDeleted = false OR r.isDeleted IS NULL) AND r.createdDate >= ?1 AND r.createdDate < ?2")
    List<Ringkasan> findByLimit1(Pageable pageable, String startDate, String endDate);

    @Query("SELECT r FROM Ringkasan r WHERE r.bapId = ?1")
    List<Ringkasan> findByBapId(String id);

    @Query("SELECT r FROM Ringkasan r WHERE r.bapNo = ?1 AND (r.isDeleted = false OR r.isDeleted IS NULL)")
    List<Ringkasan> findByBapNo(String bapNo);

    @Query("SELECT r FROM Ringkasan r WHERE LOWER(r.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (r.isDeleted = false OR r.isDeleted IS NULL)")
    List<Ringkasan> findByBapNo2(String bapNo);

    @Query("SELECT r FROM Ringkasan r WHERE r.masterTransactionId = ?1 AND (r.isDeleted = false OR r.isDeleted IS NULL)")
    List<Ringkasan> findByMasterTrxId(String id);

    @Query("SELECT r FROM Ringkasan r WHERE r.paymentNo = ?1")
    List<Ringkasan> findByBastpNo(String noTtrx);
}