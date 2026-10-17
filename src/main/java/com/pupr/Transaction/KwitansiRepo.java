package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KwitansiRepo extends JpaRepository<Kwitansi, Long> {

    @Query("SELECT k FROM Kwitansi k WHERE (k.isDeleted = false OR k.isDeleted IS NULL) AND k.createdDate >= ?1 AND k.createdDate < ?2")
    List<Kwitansi> findByLimit1(Pageable pageable, String startDate, String endDate);

    @Query("SELECT k FROM Kwitansi k WHERE k.bapNo = ?1 AND (k.isDeleted = false OR k.isDeleted IS NULL)")
    List<Kwitansi> findBBapNo(String bapNo);

    @Query("SELECT k FROM Kwitansi k WHERE LOWER(k.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (k.isDeleted = false OR k.isDeleted IS NULL)")
    List<Kwitansi> findBBapNo2(String bapNo);

    @Query("SELECT k FROM Kwitansi k WHERE k.bapId = ?1")
    List<Kwitansi> findBBapId(String bapId);
}