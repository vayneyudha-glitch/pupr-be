package com.pupr.Master;

import java.util.List;
import java.util.Optional;

public interface MasterMitraDaoServices {
    MasterMitra submit (MasterMitra payload);
    Optional<MasterMitra> getById (Long id);
    List<MasterMitra> getByCorporateName(String name);
    List<MasterMitra> findAllData();
    Optional<MasterMitra> getByNpwp(String npwp);
}
