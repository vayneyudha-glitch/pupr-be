package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SppRepo extends JpaRepository<Spp, Long> {

    @Query("SELECT e FROM Spp e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.createdDate >= ?1 AND e.createdDate < ?2)")
    List<Spp> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Spp e WHERE ((e.isDeleted = false OR e.isDeleted IS NULL)) AND (e.sppDateText >= ?1 AND e.sppDateText < ?2)")
    List<Spp> getBySppDate(Pageable pageable, String startDate, String endDate);

    @Query("SELECT e FROM Spp e WHERE LOWER(e.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Spp> getByBapNo(String bapNo);

    @Query("SELECT e FROM Spp e WHERE e.sppNo = ?1 AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    Optional<Spp> getBySppNo(String sppNo);

    @Query("SELECT e FROM Spp e WHERE LOWER(e.sppNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (e.isDeleted = false OR e.isDeleted IS NULL)")
    List<Spp> getBySppNoV2(String sppNo);

}
