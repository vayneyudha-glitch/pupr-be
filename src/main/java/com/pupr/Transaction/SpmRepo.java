package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpmRepo extends JpaRepository<Spm, Long> {

    @Query("SELECT s FROM Spm s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.createdDate >= ?1 AND s.createdDate < ?2")
    List<Spm> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Spm s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.spmDateText >= ?1 AND s.spmDateText < ?2")
    List<Spm> getByFilterSpmDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Spm s WHERE s.bapNo = ?1 AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    Optional<Spm> getByBapNo(String bapNo);

    @Query("SELECT s FROM Spm s WHERE s.spmNo = ?1 AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    Optional<Spm> getBySpmNo(String spmNo);

    @Query("SELECT s FROM Spm s WHERE LOWER(s.spmNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    List<Spm> getBySpmNoV2(String bapNo);
}