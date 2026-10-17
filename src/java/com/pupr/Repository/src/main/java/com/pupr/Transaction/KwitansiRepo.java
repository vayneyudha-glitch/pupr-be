package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KwitansiRepo extends JpaRepository<Kwitansi, Long> {

    @Query("SELECT e FROM Kwitansi e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Kwitansi> findByLimit1(Pageable pageable,String startDate,String endDate);

    @Query("SELECT e FROM Kwitansi e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Kwitansi> findBBapNo(String bapNo);

    @Query("SELECT e FROM Kwitansi e WHERE LOWER(e.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Kwitansi> findBBapNo2(String bapNo);

    @Query("SELECT e FROM Kwitansi e WHERE e.bapId = ?1")
    List<Kwitansi> findBBapId(String bapId);
}
