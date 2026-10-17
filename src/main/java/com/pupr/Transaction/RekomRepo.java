package com.pupr.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RekomRepo extends JpaRepository<Rekom, Long> {

    @Query("SELECT r FROM Rekom r WHERE r.noRekom = ?1")
    Optional<Rekom> findOneByNoRekom(String noRekom);

    @Query("SELECT r FROM Rekom r WHERE LOWER(r.noRekom) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Rekom> findOneByNoRekomV2(String noRekom);

    @Query("SELECT r FROM Rekom r WHERE r.bastpNo = ?1")
    Optional<Rekom> findOneByNoBastp(String noBastp);

    @Query("SELECT r FROM Rekom r WHERE r.tglRekom >= ?1 AND r.tglRekom < ?2")
    List<Rekom> findByLimit1(String startDate, String endDate);

}