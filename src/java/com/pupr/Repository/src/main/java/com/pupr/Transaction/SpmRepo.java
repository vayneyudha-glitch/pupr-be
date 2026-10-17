package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpmRepo extends JpaRepository<Spm, Long> {
    @Query("SELECT e FROM Spm e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Spm> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Spm e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.spmDateText >= ?1 AND e.spmDateText < ?2)")
    List<Spm> getByFilterSpmDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Spm e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Optional<Spm> getByBapNo(String bapNo);
    @Query("SELECT e FROM Spm e WHERE e.spmNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Optional<Spm> getBySpmNo(String spmNo);

    @Query("SELECT e FROM Spm e WHERE LOWER(e.spmNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Spm> getBySpmNoV2(String bapNo);
}
