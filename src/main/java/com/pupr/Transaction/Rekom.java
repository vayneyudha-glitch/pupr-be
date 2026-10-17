package com.pupr.Transaction;

import com.pupr.Dto.SignOff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rekom_transaction")
public class Rekom extends SignOff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bapId;
    private String bapNo;
    private String bastpNo;
    private String noRekom;

    @Temporal(TemporalType.DATE)
    private Date rekomeDate;

    private String tglRekom;
    private String pekerjaan;
}