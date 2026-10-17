package com.pupr.Master;


import java.util.List;
import java.util.Optional;

public interface MasterPegawaiDaoService {
    MasterPegawai submit (MasterPegawai payload);
    List<MasterPegawai> submitAll (List<MasterPegawai> payload);
    List<MasterPegawai> findAll ();
    Optional<MasterPegawai> getById (Long id);
    List<MasterPegawai> getByName(String name);
    Optional<MasterPegawai> getByNpwp(String npwp);
}
