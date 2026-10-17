package com.pupr.Transaction;

import com.pupr.Dto.TransactionResponse.RekomResponse;
import com.pupr.Master.DeletedCollection;
import org.hibernate.sql.Delete;

import java.util.List;
import java.util.Map;

public interface RekomServices {

    Rekom Submit(Rekom rekom);
    DeletedCollection RemoveById(Map<String,String> params);
    List<RekomResponse> FindAllDataWithoutFilter(Map<String,String> params);

}
