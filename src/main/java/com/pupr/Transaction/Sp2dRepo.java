package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Sp2dRepo extends JpaRepository<Sp2D, Long> {

    @Query("SELECT s FROM Sp2D s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.createdDate >= ?1 AND s.createdDate < ?2")
    List<Sp2D> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Sp2D s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.sp2dDateText >= ?1 AND s.sp2dDateText < ?2")
    List<Sp2D> getByFilterDateTrxPeriodeV2(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Sp2D s WHERE s.bapNo = ?1 AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    Optional<Sp2D> getByBapNo(String bapNo);

    @Query("SELECT s FROM Sp2D s WHERE s.sp2dNo = ?1 AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    Optional<Sp2D> getBySp2dNo(String sp2dNo);
}