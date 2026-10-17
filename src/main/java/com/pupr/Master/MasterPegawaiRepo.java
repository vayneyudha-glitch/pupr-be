package com.pupr.Master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MasterPegawaiRepo extends JpaRepository<MasterPegawai, Long> {

    @Query("SELECT m FROM MasterPegawai m WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterPegawai> findByName(String name);

    Optional<MasterPegawai> findByNpwp(String npwpNo);
}