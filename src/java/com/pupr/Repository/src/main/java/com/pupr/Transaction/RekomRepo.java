package com.pupr.Transaction;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RekomRepo extends JpaRepository<Rekom, Long> {

    @Query("SELECT e FROM Rekom e WHERE e.noRekom = ?1")
    Optional<Rekom> findOneByNoRekom(String noRekom);

    @Query("SELECT e FROM Rekom e WHERE LOWER(e.noRekom) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Rekom> findOneByNoRekomV2(String noRekom);

    @Query("SELECT e FROM Rekom e WHERE e.bastpNo = ?1")
    Optional<Rekom> findOneByNoBastp(String noBastp);

    @Query("SELECT e FROM Rekom e WHERE (e.tglRekom >= ?1 AND e.tglRekom < ?2)")
    List<Rekom> findByLimit1(String startDate, String endDate);


}
