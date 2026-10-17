package com.pupr.Master;


import com.pupr.Dto.DashBoardResponse;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.MasterRealisationResponse;
import com.pupr.Dto.TransactionResponse.PekerjaanResponseDto;
import com.pupr.Mapper.MasterMapper;
import com.pupr.Transaction.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;

@Service
public class MasterServicesImpl implements MasterServices {

    @Autowired
    MasterDaoService masterDaoService;

    @Autowired
    BapServices bapServices;

    @Autowired
    RekomDaoServices rekomDaoServices;

    @Autowired
    RingkasanService ringkasanService;

    @Autowired
    KwitansiDaoServices kwitansiDaoServices;

    @Autowired
    SppDaoServices sppDaoServices;

    @Autowired
    SpmDaoServices spmDaoServices;

    @Autowired
    Sp2dDaoServices sp2dDaoServices;

    @Autowired
    private MasterMapper masterMapper;


    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public Master submit(Master payload) throws Exception {

        if(payload.getId() == null){
//            payload.setId(getUuidNumber());
            return masterDaoService.submit(payload);
        }else {
            Optional<Master>  payloaddB = masterDaoService.getById(payload.getId());
            if (payloaddB.isPresent()) {
                Master master = payloaddB.get();

                if (payload.getNilaiDpa() !=null){
                    master.setNilaiDpa(payload.getNilaiDpa());
                }

                if (payload.getSpkNo() !=null){
                    master.setSpkNo(payload.getSpkNo());
                }

                if (payload.getNilaiSpk() !=null){
                    master.setNilaiSpk(payload.getNilaiSpk());
                }

                if (payload.getTglSpk() !=null){
                    master.setTglSpk(payload.getTglSpk());
                }

                System.out.println("Ini No Reekning " + payload.getMitraNoRekening());

                return masterDaoService.submit(master);
            }else{
                throw new Exception("Data Gagal Input");
            }
        }
    }

    @Override
    public List<Master> submitAll(List<Master> payload) throws Exception {
        return masterDaoService.submitAll(mapperMasterList(payload));
    }

    @Override
    public Master findById(Long id) throws Exception {
        Optional<Master> master;
        master = masterDaoService.getById(id);
        return master.get();
    }

    @Override
    public List<MasterRealisationResponse> findDataRealisation(Map<String, String> params) throws Exception {

        List<MasterRealisationResponse> masterRealistionResult = new ArrayList<>();

        String bidang = params.get("bidang");
        int tahun   = Integer.parseInt(params.get("tahun"));


        List<Master> masterList = masterDaoService.getMasterByBidang1(bidang,tahun);
        for(Master master : masterList){

            MasterRealisationResponse masterRealisationResponse = new MasterRealisationResponse();
            List<BapResponse> bapResponseList = new ArrayList<>();

            masterRealisationResponse.setMaster(master);
            List<Bap> bapDb = bapServices.findByAllBapMasterTrxId(String.valueOf(master.getId()));

            for (Bap bap : bapDb ){
                bapResponseList.add(bapServices.findByBapNo(bap.getBapNo()));
            }

            masterRealisationResponse.setBapResponseList(bapResponseList);
            masterRealistionResult.add(masterRealisationResponse);

        }

        return masterRealistionResult;
    }

    @Override
    public List<Master> findAllByTahunAnggaran(Map<String, String> params) throws Exception {

        String tahun = params.get("tahun");

        return masterDaoService.findAllByYear(tahun);
    }

    @Override
    public List<Master> findDataByBidangYear(Map<String, String> params) throws Exception {

        List<Master> masterList = new ArrayList<>();

        String bidang = params.get("bidang");
        String pekerjaan =params.get("pekerjaan");
        String noTrx = params.get("noTrx");

        if (bidang != null){
            syncPekerjaanByBidang(bidang);
        }

        if (noTrx == null){

            masterList = masterDaoService.findAllByYear(String.valueOf(TAHUN_ANGGARAN)); //Untuk tahun anggaran pencarian di setting dari BE
            masterList = masterList.stream()
                    .filter(thn -> thn.getTahun().equals(TAHUN_ANGGARAN))
                    .collect(Collectors.toList());

            if(bidang != null) {
                masterList = masterList.stream()
                        .filter(bdg -> bdg.getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());
            }

            if(pekerjaan != null) {
                masterList = masterList.stream()
                        .filter(pkr -> pkr.getPekerjaan().toLowerCase().contains(pekerjaan.toLowerCase()))
                        .collect(Collectors.toList());
            }


        }else{
            masterList = masterDaoService.getMasterByNoTrx(Integer.valueOf(noTrx));
        }

        return masterList;
    }

    @Override
    public Master submitSpkMaster(Master payload) throws Exception {

        System.out.println("Ini SPK FORM date" + payload.getTglSpkForm());
        if(payload.getId() == null ){
            return masterDaoService.submit(payload);
        }else {
            Optional<Master> payloaddB = masterDaoService.getById(payload.getId());
            if (payloaddB.isPresent()) {
                Master master = payloaddB.get();

                if (payload.getSpkNo() != null) {
                    master.setSpkNo(payload.getSpkNo());
                }

                if (payload.getNilaiSpk() != null) {
                    master.setNilaiSpk(payload.getNilaiSpk());
                }

                if (payload.getTglSpk() != null) {
                    master.setTglSpk(payload.getTglSpk());
                }

                if(payload.getTglSpkForm() != null){
                    master.setTglSpkForm(payload.getTglSpkForm());
                }

                if (payload.getTglStartPekerjaan() != null) {
                    master.setTglStartPekerjaan(payload.getTglStartPekerjaan());
                }

                if (payload.getTglEndPekerjaan() != null) {
                    master.setTglEndPekerjaan(payload.getTglEndPekerjaan());
                }

                master.setMitraId(payload.getMitraId() != null ? payload.getMitraId():master.getMitraId());
                master.setMitraName(payload.getMitraName() != null ? payload.getMitraName():master.getMitraName());
                master.setMitraPimpinan(payload.getMitraPimpinan() != null ? payload.getMitraPimpinan():master.getMitraPimpinan());
                master.setMitraNoRekening(payload.getMitraNoRekening() != null  ? payload.getMitraNoRekening():master.getMitraNoRekening());

                return masterDaoService.submit(master);
            } else {
                throw new Exception("Data Gagal Input");
            }
        }
    }

    @Override
    public List<Master> findBySpkNo(String spkNo) throws Exception {
        return masterDaoService.getBySpkNo(spkNo);
    }

    @Override
    public List<DashBoardResponse> getDashBoardInformation(Map<String, String> params) throws Exception {

        Map<String,Integer> mapResult = new HashMap<>();
        List<DashBoardResponse> responseList = new ArrayList<>();

        BigDecimal nilaiKontrak =  new BigDecimal(0);
        BigDecimal nilaiDpa = new BigDecimal(0);
        BigDecimal nilaiBap = new BigDecimal(0);

        long kontrak=0;
        long bap = 0;
        long ringkasan = 0;
        long kwitansi = 0;
        long rekom = 0;
        long spp = 0;
        long spm = 0;
        long sp2d = 0;

        String tahun = params.get("tahun");
        String bidang = params.get("bidang");



        List<Master> masterList = masterDaoService.findAllByYear(tahun);
        List<Bap> bapList = bapServices.findAllDataByYear(tahun);

        masterList = masterList.stream()
                .filter(thn -> thn.getTahun().toString().equalsIgnoreCase(tahun))
                .collect(Collectors.toList());

        if(bidang != null){
            masterList = masterList.stream()
                    .filter(bdg -> bdg.getBidang().equalsIgnoreCase(bidang))
                    .collect(Collectors.toList());

            bapList = bapList.stream()
                    .filter(bdg -> bdg.getMaster().getBidang().equalsIgnoreCase(bidang))
                    .collect(Collectors.toList());
        }





//        List<Ringkasan> ringkasanList = ringkasanService.findAllDataByTahun(tahun);
//        List<Rekom> rekomList  = rekomDaoServices.findAllDataNoFilter();
//        List<Kwitansi> kwitansis = kwitansiDaoServices.findAllDataByTahun(tahun);
//        List<Spp> sppList = sppDaoServices.findAllData();
//        List<Spm> spmList = spmDaoServices.findAllData();
//        List<Sp2D> sp2DList = sp2dDaoServices.findAllData();

        kontrak = masterList.stream()
                .filter(spk -> spk.getSpkNo() != null
                        && spk.getNilaiSpk().compareTo(new BigDecimal(0)) > 0
                )
                .count();

        bap = bapList.stream()
                .count();

        ringkasan = bapList.stream()
                .filter(rks -> rks.getRingkasan() != null)
                .count();

        kwitansi = bapList.stream()
                .filter(rks -> rks.getKwitansi() != null)
                .count();

        spp = bapList.stream()
                .filter(rks -> rks.getSpp() != null)
                .count();

        rekom = bapList.stream()
                .filter(rks -> rks.getRekom() != null)
                .count();

        spm = bapList.stream()
                .filter(rks -> rks.getSpm() != null)
                .count();

        sp2d =bapList.stream()
                .filter(rks -> rks.getSp2D() != null)
                .count();

        for (Master master : masterList){
            if(master.getNilaiSpk() != null && master.getNilaiDpa() != null){
                nilaiKontrak = nilaiKontrak.add(master.getNilaiSpk());
                nilaiDpa = nilaiDpa.add(master.getNilaiDpa());
            }
        }

        for(Bap bapLoop : bapList){
            nilaiBap = nilaiBap.add(bapLoop.getBapAmount());
        }

        nilaiKontrak = nilaiKontrak.subtract(nilaiBap);
        nilaiDpa = nilaiDpa.subtract(nilaiBap);

        responseList.add(new DashBoardResponse("Pekerjaan", String.valueOf(masterList.size())));
        responseList.add(new DashBoardResponse("Kontrak", df.format(kontrak)));
        responseList.add(new DashBoardResponse("BAP", df.format(bap)));
        responseList.add(new DashBoardResponse("RINGKON", df.format(ringkasan)));
        responseList.add(new DashBoardResponse("KWITANSI", df.format(kwitansi)));
        responseList.add(new DashBoardResponse("SPP", df.format(spp)));
        responseList.add(new DashBoardResponse("SPM", df.format(spm)));
        responseList.add(new DashBoardResponse("SP2D", df.format(sp2d)));
        responseList.add(new DashBoardResponse("REKOM", df.format(rekom)));
        responseList.add(new DashBoardResponse("Sisa Kontrak", "Rp. " + df.format(nilaiKontrak)));
        responseList.add(new DashBoardResponse("Sisa Anggaran", "Rp. " +  df.format(nilaiDpa)));

        return responseList;
    }

    @Override
    public Master removeById(Long id) throws Exception {
        Optional <Master> masterDb = masterDaoService.getById(id);
        Master master = masterDb.isPresent() ? masterDb.get() : null;

        if (master != null){
            master.setIsDeleted(true);
        }

        return master;
    }

    @Override
    public List<Master> syncPekerjaanByBidang(String bidangCode) throws Exception {

        List<Master>  masterResult = new ArrayList<>();

//        try {
            RestTemplate rest = new RestTemplate();

            // 1. Call external API
            String url = IP_MASTER_SERVICE + "/api/pekerjaan/by-bidang-code/"  + bidangCode;

            System.out.println("MASTER URL " + url);

            ResponseEntity<PekerjaanResponseDto[]> result = rest.getForEntity(
                    url,
                    PekerjaanResponseDto[].class
            );

            // 4. Convert array to list
            List<PekerjaanResponseDto> pekerjaanList = Arrays.asList(result.getBody());

            // 2. Validate response
            if (pekerjaanList == null || pekerjaanList.isEmpty()) {
                throw new Exception("No pekerjaan data found for bidangCode: " + bidangCode);
            }

            List<Master> masterList = pekerjaanList.stream()
                    .map( pekerjaan -> {
                        return masterMapper.toMasterFromPekerjaanResponse(pekerjaan);
                    })
                    .collect(Collectors.toList());

            for (Master master : masterList){

                List<Master> masters  = masterDaoService.fingOneMasterByPekerjaanAndBidang(master.getBidang(),master.getPekerjaan(), master.getTahun() != null ? master.getTahun() : TAHUN_ANGGARAN);

                if(masters.isEmpty()){
                    master.setTahun(TAHUN_ANGGARAN);
                    masterDaoService.submit(master);

                    masterResult.add(master);
                }

            }

            return masterResult;

//        } catch (Exception e) {
//            throw new Exception("Failed to sync pekerjaan for bidangCode: "
//                    + bidangCode + " | Reason: " + e.getMessage());
//        }
    }

    @Override
    public List<Master> syncPekerjaanByBidangPatch(String bidangCode) throws Exception {

        List<Master>  masterResult = new ArrayList<>();

        try {
            RestTemplate rest = new RestTemplate();

            // 1. Call external API
            String url = IP_MASTER_SERVICE + "/api/pekerjaan/by-bidang-code/"  + bidangCode;

            ResponseEntity<PekerjaanResponseDto[]> result = rest.getForEntity(
                    url,
                    PekerjaanResponseDto[].class
            );

            // 4. Convert array to list
            List<PekerjaanResponseDto> pekerjaanList = Arrays.asList(result.getBody());

            // 2. Validate response
            if (pekerjaanList == null || pekerjaanList.isEmpty()) {
                throw new Exception("No pekerjaan data found for bidangCode: " + bidangCode);
            }

            List<Master> masterList = pekerjaanList.stream()
                    .map( pekerjaan -> {
                        return masterMapper.toMasterFromPekerjaanResponse(pekerjaan);
                    })
                    .collect(Collectors.toList());

            for (Master master : masterList){

                List<Master> masters  = masterDaoService.fingOneMasterByPekerjaanAndBidang(master.getBidang(),master.getPekerjaan(), master.getTahun() != null ? master.getTahun() : TAHUN_ANGGARAN);

                if(!masters.isEmpty()){
                    // Patch existing record with fresh data from external API
                    Master existing = masters.get(0);
                    master.setId(existing.getId());
                    master.setTahun(existing.getTahun() != null ? existing.getTahun() : TAHUN_ANGGARAN);
                    master.setSpkNo(existing.getSpkNo());
                    master.setTglSpk(existing.getTglSpk());
                    master.setMitraId(existing.getMitraName());
                    master.setMitraName(existing.getMitraName());
                    master.setMitraPimpinan(existing.getMitraPimpinan());
                    master.setMitraNoRekening(existing.getMitraNoRekening());
                    master.setTglStartPekerjaan(existing.getTglStartPekerjaan());
                    master.setTglEndPekerjaan(existing.getTglEndPekerjaan());
                    masterDaoService.submit(master);
                    masterResult.add(master);
                }

            }

            return masterResult;

        } catch (Exception e) {
            throw new Exception("Failed to sync & patch pekerjaan for bidangCode: "
                    + bidangCode + " | Reason: " + e.getMessage());
        }
    }

    private List<Master> mapperMasterList(List<Master> payload){
        List<Master> masterList = payload.stream()
                .collect(Collectors.toList());

        return masterList;
    }
}
