package com.pupr.Master;

import java.util.List;

public interface MasterMitraService {
    MasterMitra submit(MasterMitra payload) throws Exception;
    List<MasterMitra> submitAll(List<MasterMitra> payload) throws Exception;
    MasterMitra findById (Long id) throws Exception;
    List<MasterMitra> findByCorporateName(String name) throws Exception;
    List<MasterMitra> getAllData() throws Exception;
    MasterMitra findByNpwp(String npwp) throws Exception;
    MasterMitra removeById(Long id) throws Exception;
}
