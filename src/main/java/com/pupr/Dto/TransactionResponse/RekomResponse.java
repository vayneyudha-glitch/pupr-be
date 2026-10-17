package com.pupr.Dto.TransactionResponse;

import com.pupr.Transaction.Rekom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RekomResponse extends Rekom {

    private String bidang;
}
