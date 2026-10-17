package com.pupr.Dto.TransactionResponse;


import com.pupr.Transaction.Master;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterRealisationResponse {
    private Master master;
    private List<BapResponse> bapResponseList;
}
