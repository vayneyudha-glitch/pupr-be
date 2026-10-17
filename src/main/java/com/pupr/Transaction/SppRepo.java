package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SppRepo extends JpaRepository<Spp, Long> {

    @Query("SELECT s FROM Spp s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.createdDate >= ?1 AND s.createdDate < ?2")
    List<Spp> getByFilterDateTrxPeriode(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Spp s WHERE (s.isDeleted = false OR s.isDeleted IS NULL) AND s.sppDateText >= ?1 AND s.sppDateText < ?2")
    List<Spp> getBySppDate(Pageable pageable, String startDate, String endDate);

    @Query("SELECT s FROM Spp s WHERE LOWER(s.bapNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    List<Spp> getByBapNo(String bapNo);

    @Query("SELECT s FROM Spp s WHERE s.sppNo = ?1 AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    Optional<Spp> getBySppNo(String sppNo);

    @Query("SELECT s FROM Spp s WHERE LOWER(s.sppNo) LIKE LOWER(CONCAT('%', ?1, '%')) AND (s.isDeleted = false OR s.isDeleted IS NULL)")
    List<Spp> getBySppNoV2(String sppNo);
}