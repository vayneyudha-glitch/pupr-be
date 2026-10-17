package com.pupr.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignOff {

    private String createdBy;
    private String createdDate;
    private LocalDateTime createdTime;

    private String updateBy;
    private String lastUpdate;
    private LocalDateTime lastUpdateTime;

    private String deletedBy;
    private Boolean isDeleted;
    private String deletedDate;
    private LocalDateTime deletedTime;
}
