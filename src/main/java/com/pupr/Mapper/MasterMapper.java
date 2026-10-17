package com.pupr.Mapper;

import com.pupr.Dto.TransactionResponse.PekerjaanResponseDto;
import com.pupr.Transaction.Master;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.pupr.Util.CommonParamConstans.formatterReport;

@Component
public class MasterMapper {

    public Master toMasterFromPekerjaanResponse(PekerjaanResponseDto responseDto) {

        Master.MasterBuilder builder = Master.builder()
                .bidang(responseDto.getBidangCode())
                .anggaran(responseDto.getAnggaranDesc())
                .kategory(responseDto.getCategoryType())
                .kodeProgram(responseDto.getProgramCode())
                .programDesc(responseDto.getProgramDescription())
                .kodeKegiatan(responseDto.getKegiatanCodeRef())
                .kegiatanDesc(responseDto.getKegiatanDescRef())
                .kodeSubKegiatan(responseDto.getProgramCode() + "."
                        + responseDto.getKegiatanCodeRef() + "."
                        + responseDto.getSubKegiatanCodeRef()+".")
                .subKegitanDesc(responseDto.getSubKegiatanDescRef())
                .kodeBelanja(responseDto.getBelanjaCodeRef())
                .belanjaDesc(responseDto.getBelanjaDescRef())
                .pekerjaan(responseDto.getDescription())
                .noDpa(responseDto.getNoDpa())
                .tglDpa(responseDto.getDpaDate() != null ? formatterReport.format(responseDto.getDpaDate()):"")
                .nilaiDpa(new BigDecimal(responseDto.getDpaValue()));

        return builder.build();
    }
}
