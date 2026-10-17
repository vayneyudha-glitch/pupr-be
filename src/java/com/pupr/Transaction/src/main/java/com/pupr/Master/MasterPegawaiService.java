package com.pupr.Master;

import java.util.List;

public interface MasterPegawaiService {
    MasterPegawai submit(MasterPegawai payload) throws Exception;
    List<MasterPegawai> getAll() throws Exception;
    List<MasterPegawai> submitAll(List<MasterPegawai> payload) throws Exception;
    MasterPegawai findById (Long id) throws Exception;
    List<MasterPegawai> findByName(String name) throws Exception;
    MasterPegawai findByNpwp(String npwp) throws Exception;
}
