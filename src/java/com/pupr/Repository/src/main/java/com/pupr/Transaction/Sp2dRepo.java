package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Sp2dRepo extends JpaRepository<Sp2D, Long> {
    @Query("SELECT e FROM Sp2D e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Sp2D> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Sp2D e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.sp2dDateText >= ?1 AND e.sp2dDateText < ?2)")
    List<Sp2D> getByFilterDateTrxPeriodeV2(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Sp2D e WHERE e.bapNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Optional<Sp2D> getByBapNo(String bapNo);

    @Query("SELECT e FROM Sp2D e WHERE e.sp2dNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Optional<Sp2D> getBySp2dNo(String sp2dNo);
}
