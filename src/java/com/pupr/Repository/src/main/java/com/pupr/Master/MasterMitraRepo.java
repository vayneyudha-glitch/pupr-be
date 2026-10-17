package com.pupr.Master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterMitraRepo extends JpaRepository<MasterMitra, Long> {
//    The i option makes the query case-insensitive.
//    The Reges for like query in SQL
    @Query("SELECT e FROM MasterMitra e WHERE LOWER(e.corporateName) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterMitra> findByCorporateName(String corporateName);

    @Query("SELECT e FROM MasterMitra e WHERE e.npwp = ?1")
    Optional<MasterMitra> findByNpwp(String npwpNo);
}
