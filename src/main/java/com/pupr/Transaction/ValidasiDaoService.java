package com.pupr.Transaction;

import java.util.List;

public interface ValidasiDaoService {
    Validasi submitData(Validasi paylaodData);
    List<Validasi> findAllV1();
}
