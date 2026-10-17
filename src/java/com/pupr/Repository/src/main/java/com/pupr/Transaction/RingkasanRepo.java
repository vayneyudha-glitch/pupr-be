package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RingkasanRepo extends JpaRepository<Ringkasan, Long> {

    @Query("SELECT e FROM Ringkasan e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Ringkasan> findByLimit1(Pageable pageable,String startDate,String endDate);

    @Query("SELECT e FROM Ringkasan e WHERE e.bapId = ?1")
    List<Ringkasan> findByBapId(String id);

    @Query("SELECT e FROM Ringkasan e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Ringkasan> findByBapNo(String bapNo);

    @Query("SELECT e FROM Ringkasan e WHERE LOWER(e.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Ringkasan> findByBapNo2(String bapNo);

    @Query("SELECT e FROM Ringkasan e WHERE e.masterTransactionId = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Ringkasan> findByMasterTrxId(String id);

    @Query("SELECT e FROM Ringkasan e WHERE e.paymentNo = ?1")
    List<Ringkasan> findByBastpNo(String noTtrx);


}
