package com.pupr.MasterParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TugasanParamRepo extends JpaRepository<MasterTugasanParam, Long> {

    @Query("SELECT e FROM MasterTugasanParam e WHERE LOWER(e.tugasanKode) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterTugasanParam> findMasterTugasanParamByTugasanCode(String tugasanCode);

    @Query("SELECT e FROM MasterTugasanParam e WHERE LOWER(e.tugasanName) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterTugasanParam> findMasterTugasanParamByTugasanName(String tugasanName);

    @Query("SELECT e FROM MasterTugasanParam e WHERE LOWER(e.urusanKode) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<MasterTugasanParam> findMasterTugasanParamByUrusanCode(String urusanCode);
}
