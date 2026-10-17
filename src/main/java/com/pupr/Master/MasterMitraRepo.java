package com.pupr.Master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterMitraRepo extends JpaRepository<MasterMitra, Long> {

    @Query("SELECT m FROM MasterMitra m WHERE LOWER(m.corporateName) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterMitra> findByCorporateName(String corporateName);

    Optional<MasterMitra> findByNpwp(String npwpNo);
}