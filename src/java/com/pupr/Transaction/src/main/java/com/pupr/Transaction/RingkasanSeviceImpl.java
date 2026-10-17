package com.pupr.Transaction;

import com.pupr.Aop.ValidateTahunDb;
import com.pupr.CustomHttpStatus.ConflictException;
import com.pupr.Dto.TransactionResponse.BapResponse;
import com.pupr.Dto.TransactionResponse.RingkasanResponse;
import com.pupr.Master.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.pupr.Util.CommonParamConstans.*;


@Service
public class RingkasanSeviceImpl implements RingkasanService{

    @Autowired
    RingkasanDaoService ringkasanDaoService;

    @Autowired
    BapServices bapServices;

    @Autowired
    MasterMitraDaoServices masterMitraDaoServices;

    @Autowired
    MasterDaoService masterDaoService;

    @Autowired
    BapDaoServices bapDaoServices;
    @Autowired
    MasterPegawaiDaoService masterPegawaiDaoService;

    @Autowired
    DeletedCollectionDaoServices collectionDaoServices;

    @Override
    @Transactional
    @ValidateTahunDb
    public RingkasanResponse submit(Ringkasan ringkasan) throws Exception, ConflictException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy");

        if (ringkasan.getId() == null){
            ringkasan.setCreatedDate(LocalDate.now().toString());
            ringkasan.setCreatedTime(LocalDateTime.now());

            if(ringkasanDaoService.findByBapNo(ringkasan.getBapNo()) != null){
                throw new ConflictException("Data Duplicated");
            }
        }else {
            Ringkasan dataDb = ringkasanDaoService.findById(ringkasan.getId());
            ringkasan.setCreatedDate(dataDb.getCreatedDate());
            ringkasan.setCreatedTime(dataDb.getCreatedTime());
        }

        Ringkasan ringkasanDb = ringkasanDaoService.findByBapNo(ringkasan.getBapNo());
        if(ringkasanDb != null){
            ringkasan.setCreatedDate(ringkasanDb.getCreatedDate());
            ringkasan.setCreatedTime(ringkasanDb.getCreatedTime());
        }

        ringkasan.setLastUpdate(LocalDate.now().toString());
        ringkasan.setLastUpdateTime(LocalDateTime.now());
        ringkasan.setIsDeleted(false);

        Ringkasan ringkasanResult =  ringkasanDaoService.submit(ringkasan);
        bapServices.bapUpdateChildTrxId(Long.valueOf(ringkasan.getBapId()),ringkasan.getId(),TRX_CODE_RINGKASAN);

        RingkasanResponse response = new RingkasanResponse();
        response.setRingkasan(ringkasan);

        //This Part For Update Some Field in Master
        Optional<Master> masterCheck = masterDaoService.getById(Long.valueOf(ringkasan.getMasterTransactionId()));
        if(masterCheck.isPresent()){
            Master master = masterCheck.get();

            System.out.println("Ini Tgl " + ringkasan.getTglStartPekerjaan());
            System.out.println("Ini Mitra " + ringkasan.getMitraId());

            if (ringkasan.getTglStartPekerjaan() != null) {
                master.setTglStartPekerjaan(formatter.format(ringkasan.getTglStartPekerjaan()));
            }

            if (ringkasan.getTglEndPekerjaan() != null) {
                master.setTglEndPekerjaan(formatter.format(ringkasan.getTglEndPekerjaan()));
            }

            master.setMitraId(ringkasan.getMitraId() != null ? ringkasan.getMitraId():master.getMitraId());
            master.setMitraName(ringkasan.getMitraName() != null ? ringkasan.getMitraName():master.getMitraName());
            master.setMitraPimpinan(ringkasan.getMitraPimpinan() != null ? ringkasan.getMitraPimpinan():master.getMitraPimpinan());
            master.setMitraNoRekening(ringkasan.getMitraNoRekening() != null  ? ringkasan.getMitraNoRekening():master.getMitraNoRekening());

            masterDaoService.submit(master);
        }

        return response;
    }

    @Override
    public RingkasanResponse findById(Long id) {
        RingkasanResponse ringkasanResponse = new RingkasanResponse();
        Ringkasan ringkasan = ringkasanDaoService.findById(id);
        if (ringkasan == null){
            return null;
        }else{
            ringkasanResponse.setRingkasan(ringkasan);
            BapResponse bapResponse = bapServices.findById(Long.valueOf(ringkasan.getBapId()));
            if(bapResponse != null){
                ringkasanResponse.setBap(bapResponse.getBap());
                ringkasanResponse.setMaster(bapResponse.getMaster());
            }

            if (ringkasan.getVendorId() != null){
                Optional<MasterMitra> masterMitraDb = masterMitraDaoServices.getById(Long.valueOf(ringkasan.getVendorId()));
                if(masterMitraDb.isPresent()){
                    ringkasanResponse.setMasterMitra(masterMitraDb.get());
                }
            }

        }

        return ringkasanResponse;
    }

    @Override
    public Ringkasan findByBapId(String id) {
        return ringkasanDaoService.findDataByBapId(id).get(0);
    }

    @Override
    public List<Ringkasan> findByBapNo(String bapNo) {

        List<Long> idList = new ArrayList<>();
        List<Ringkasan> result = new ArrayList<>();
        List<Ringkasan> ringkasanList  = ringkasanDaoService.findDataRingkasanBapNoLike(bapNo);

        for (Ringkasan ringkasan : ringkasanList){
            idList.clear();

            if(ringkasan.getMasterTransactionId() != null){
                idList.add(Long.valueOf(ringkasan.getMasterTransactionId()));
            }

            List<Master> masterList =masterDaoService.findByListMasterTrxIdWithoutBidang(idList);
            if(masterList.size() >0 ){
                Master masterResult = masterList.get(0);

                ringkasan.setSumberAnggaranDesc(masterResult.getAnggaran());
                ringkasan.setSpkNo(masterResult.getSpkNo());
                ringkasan.setSpkDate(masterResult.getTglSpk());
                ringkasan.setStartDate(masterResult.getTglStartPekerjaan());
                ringkasan.setEndDate(masterResult.getTglEndPekerjaan());
                ringkasan.setDpaDate(masterResult.getTglDpa());
                result.add(ringkasan);
            }


        }
        return result;
    }

    @Override
    public RingkasanResponse findByBapIdSecond(String id) {
        Ringkasan ringkasan = new Ringkasan();
        RingkasanResponse ringkasanResponse = new RingkasanResponse();
        List<Ringkasan> ringkasanListDb = ringkasanDaoService.findDataByBapId(id);

        List<Ringkasan> ringkasanList = Collections.singletonList(ringkasanListDb.stream()
                .filter(ringkasanSelected -> ringkasanSelected.getIsDeleted() == false)
                .findAny()
                .orElse(null));

        if (ringkasanList.size()>0) {
            ringkasan = ringkasanList.get(0);
            if (ringkasan == null){
                return null;
            }else{
                ringkasanResponse.setRingkasan(ringkasan);
                BapResponse bapResponse = bapServices.findById(Long.valueOf(ringkasan.getBapId()));
                if(bapResponse != null){
                    ringkasanResponse.setBap(bapResponse.getBap());
                    ringkasanResponse.setMaster(bapResponse.getMaster());
                }

                Optional<MasterMitra> masterMitraDb = masterMitraDaoServices.getById(Long.valueOf(ringkasan.getVendorId()));
                if(masterMitraDb.isPresent()){
                    ringkasanResponse.setMasterMitra(masterMitraDb.get());
                }

            }
        }else {
            return null;
        }

        return ringkasanResponse;
    }

    @Override
    public Ringkasan findByMasterTrxId(String id) {
        Ringkasan ringkasan = new Ringkasan();
        List<Ringkasan> ringkasanList = ringkasanDaoService.findDataMasterTrxId(id);
        if (ringkasanList.isEmpty()) {
            return ringkasan;
        }else {
            List<Ringkasan> ringkasanData = ringkasanList.stream()
                    .sorted(Comparator.comparing(Ringkasan::getCreatedTime).reversed())
                    .collect(Collectors.toList());
            return ringkasanData.get(0);
        }
    }

    @Override
    public List<Ringkasan> findByLimit1(Pageable pageable,Map<String,String> params) {
        List<Long> idList = new ArrayList<>();
        List<Ringkasan> ringkasanResulList = new ArrayList<>();


        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        List<Ringkasan> ringkasanList = ringkasanDaoService.findDataLImit1(pageable,startDate,endDate);
        ringkasanResulList = ringkasanList.stream()
                .map(this::mapperRingkasanResponseCompleteMasterAndBapInfo)
                .collect(Collectors.toList());

        return ringkasanResulList;
    }

    @Override
    public List<RingkasanResponse> findByLimit1V2(Pageable pageable, Map<String, String> params) {
        List<Long> idList = new ArrayList<>();
        List<RingkasanResponse> ringkasanResulList = new ArrayList<>();

        String noBap = params.get("bapNo");
        String bidang = params.get("bidang");
        String startDate = params.get("startDate");
        String endDate = params.get("endDate");

        System.out.println("Bap No " + noBap);
        System.out.println("Date  " + startDate + " - " + endDate);
        if(noBap == null){
            List<Bap> bapList = bapDaoServices.findDataBapDate(pageable,startDate,endDate);

            System.out.println("Bap DB size  " + bapList.size());
            if(bapList.size() > 0){
                System.out.println("Bap List " + bapList.size());
                ringkasanResulList = bapList.stream()
                        .map(this::mapperRingkasanFromBapList)
                        .collect(Collectors.toList());
            }

            if(bidang != null){
                ringkasanResulList = ringkasanResulList.stream()
                        .filter(b -> b.getBap().getMaster().getBidang().equalsIgnoreCase(bidang))
                        .collect(Collectors.toList());

                System.out.println("Bap Bidang List " + bapList.size());
            }
        }else{
            List<Bap> bapList = bapDaoServices.findByBapNoV2(noBap,START_FROM_TAHUN_ANGGARAN);
            if(bapList.size() > 0){
                ringkasanResulList = bapList.stream()
                        .map(this::mapperRingkasanFromBapList)
                        .collect(Collectors.toList());
            }
        }

        return ringkasanResulList;
    }

    private Ringkasan mapperRingkasanResponseCompleteMasterAndBapInfo(Ringkasan ringkasan){

        Master master = masterDaoService.getById(Long.valueOf(ringkasan.getMasterTransactionId())).get();
        ringkasan.setSumberAnggaranDesc(master.getAnggaran());
        ringkasan.setSpkNo(master.getSpkNo());
        ringkasan.setSpkDate(master.getTglSpk());
        ringkasan.setStartDate(master.getTglStartPekerjaan());
        ringkasan.setEndDate(master.getTglEndPekerjaan());
        ringkasan.setDpaDate(master.getTglDpa());
        ringkasan.setPekerjaan(master.getPekerjaan());

        return ringkasan;
    }

    private RingkasanResponse mapperRingkasanDtoCompleteMasterAndBapInfo(Ringkasan ringkasan){
        RingkasanResponse ringkasanResponse = new RingkasanResponse();

        Bap bap = bapDaoServices.findByBapNo(ringkasan.getBapNo());
        ringkasanResponse.setRingkasan(ringkasan);
        ringkasanResponse.setBap(bap);

        return ringkasanResponse;
    }

    private RingkasanResponse mapperRingkasanFromBapList(Bap bap){
        RingkasanResponse ringkasanResponse = new RingkasanResponse();

        ringkasanResponse.setRingkasan(bap.getRingkasan());
        ringkasanResponse.setBap(bap);

        return ringkasanResponse;
    }

    @Override
    public byte[] generateFaktur(Long id) throws Exception {

        Ringkasan ringkasan = ringkasanDaoService.findById(id);
        BapResponse response = bapServices.findByBapNo(ringkasan.getBapNo());
        MasterMitra mitraResponse = new MasterMitra();
        MasterPegawai masterPegawai = new MasterPegawai();

        if(response.getBap().getMaster() != null){
            if (response.getBap().getMaster().getMitraId() != null){
                Optional<MasterMitra> mitra = masterMitraDaoServices.getById(Long.valueOf(response.getBap().getMaster().getMitraId()));
                if(mitra.isPresent()){
                    mitraResponse = mitra.get();
                }else {
                    System.out.println("Mitra Not Found From Master");
                }
            }
        }

        if(ringkasan != null){
            if (ringkasan.getMitraId() != null){
                Optional<MasterMitra> mitra = masterMitraDaoServices.getById(Long.valueOf(ringkasan.getMitraId()));
                if(mitra.isPresent()){
                    mitraResponse = mitra.get();
                }else {
                    System.out.println("Mitra Not Found ");
                }
            }
        }

        if(response.getRingkasan().getNamaPegawai() != null){
            List<MasterPegawai> list = masterPegawaiDaoService.getByName(response.getRingkasan().getNamaPegawai());
            if(list.size() >0){
                masterPegawai = list.get(0);
            }
        }



        byte[] reportContent;

        JasperReport jasperReport;
        JasperPrint jasperPrint = null;

        try {
            jasperReport = (JasperReport)
                    JRLoader.loadObject(ResourceUtils.getFile("RingkasanFaktur.jasper"));
        } catch (FileNotFoundException | JRException e) {
            try {
                InputStream employeeReportStream
                        = getClass().getResourceAsStream("/RingkasanFaktur.jrxml");
                jasperReport = JasperCompileManager.compileReport(employeeReportStream);

                JRSaver.saveObject(jasperReport, id.toString());
            } catch (JRException ex) {
                System.out.println("Ini Exception " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }

        if (response.getRingkasan() == null || response.getMaster() == null){
            throw new Exception("Data Ringkasan Empty");
        }


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sumberAnggaran", response.getMaster().getAnggaran());
        parameters.put("noDpa", response.getBap().getMaster().getNoDpa() != null ? response.getBap().getMaster().getNoDpa() : "-");
        parameters.put("tglDpa", response.getBap().getMaster().getTglDpa() != null ? "Tgl. " +  response.getBap().getMaster().getTglDpa() : "-");
        parameters.put("noSirup", response.getBap().getRingkasan().getNoSirup() != null ? response.getBap().getRingkasan().getNoSirup() : "-");
        parameters.put("kodeRekening", response.getMaster().getKodeSubKegiatan() + response.getMaster().getKodeBelanja());
        parameters.put("program", response.getBap().getMaster().getProgramDesc() != null ? response.getBap().getMaster().getProgramDesc() : "-");
        parameters.put("kegiatan", response.getBap().getMaster().getKegiatanDesc() != null ? response.getBap().getMaster().getKegiatanDesc() : "-");
        parameters.put("subKegiatan", response.getBap().getMaster().getSubKegitanDesc() != null ? response.getBap().getMaster().getSubKegitanDesc() : "-");
        parameters.put("pekerjaan", response.getBap().getMaster().getPekerjaan() != null ? response.getBap().getMaster().getPekerjaan() : "-");
        parameters.put("lokasi", response.getRingkasan().getLocation() != null ? response.getRingkasan().getLocation()  : "-");
        parameters.put("belanja", response.getBap().getMaster().getBelanjaDesc() != null ? response.getBap().getMaster().getBelanjaDesc()  : "-");
        parameters.put("noSpk", response.getBap().getMaster().getSpkNo() != null ? response.getBap().getMaster().getSpkNo() : "-");
        parameters.put("tglSpk", response.getBap().getMaster().getTglSpk() != null ? "Tgl. " + response.getBap().getMaster().getTglSpk() : "-");
        parameters.put("adendum", response.getBap().getRingkasan().getAddendumDetail() != null ? response.getBap().getRingkasan().getAddendumDetail()  : "-");
        parameters.put("namaPerusahaan", mitraResponse.getCorporateName() != null ? mitraResponse.getCorporateName() : "-");
        parameters.put("alamatPerusahaan", mitraResponse.getAddress() != null ?  mitraResponse.getAddress() : "-");
        parameters.put("pimpinanPerusahaan", mitraResponse.getHeadOffice() != null ? mitraResponse.getHeadOffice() : "-");
        parameters.put("npwp", mitraResponse.getNpwp() != null ?  mitraResponse.getNpwp(): "-");
        parameters.put("nomorRekeningPerusahaan",mitraResponse.getRekeningNumber() != null ? mitraResponse.getRekeningNumber()  : "-");
        parameters.put("bank", mitraResponse.getBankAccountName() != null ?mitraResponse.getBankAccountName() : "-");
        parameters.put("nilaiSpk", response.getBap().getMaster().getNilaiSpk() != null ?  "Rp. " +  df.format(response.getBap().getMaster().getNilaiSpk()) : "-");
        parameters.put("nilaiBap", response.getBap().getBapAmount() != null ? "Rp. " + df.format(response.getBap().getBapAmount()) : "-");
        parameters.put("noBap", response.getBap().getBapNo() != null ? response.getBap().getBapNo() : "-");
        parameters.put("tglBap", response.getBap().getBapDate() != null ? "Tgl. " + formatterReport.format(response.getBap().getBapDate()) : "-");
        parameters.put("uraian", response.getBap().getRingkasan().getJobDesc() != null ? response.getBap().getRingkasan().getJobDesc() : "-");
        parameters.put("tipePembayaran", response.getBap().getPaymentMethodDesc() != null ? response.getBap().getPaymentMethodDesc() : "-");
        parameters.put("parameterPembayaran", "16. " + setLabelBastp(response.getRingkasan().getPaymentId()));
        parameters.put("noBastp", response.getRingkasan().getPaymentNo() != null ? response.getRingkasan().getPaymentNo() + " Tgl. " + formatterReport.format(response.getRingkasan().getPaymentDate()) : "-");
//        parameters.put("tglBastp", response.getRingkasan().getPaymentDate() != null ? "Tgl. " + formatterReport.format(response.getRingkasan().getPaymentDate())  : "-");
        parameters.put("jangkaWaktuPelaksanaan", response.getBap().getRingkasan().getDayEstimate() != null ? response.getBap().getRingkasan().getDayEstimate() : "-");
        parameters.put("tglPenyelesaian",response.getBap().getRingkasan().getTglEndPekerjaan() != null ? formatterReport.format(response.getBap().getRingkasan().getTglEndPekerjaan()) : "-");
        parameters.put("jangkaWaktuPemeliharaan", response.getBap().getRingkasan().getMaintenanceTime() != null ? response.getBap().getRingkasan().getMaintenanceTime() : "-");
        parameters.put("ppkName", response.getBap().getRingkasan().getNamaPegawai() != null ? response.getBap().getRingkasan().getNamaPegawai() : "-");
        parameters.put("nipPpk", masterPegawai.getNip() != null ? "NIP : " +  masterPegawai.getNip() : "-");


//        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
//        }catch (JRException e){
//            System.out.println( "JRE Exception" + e.getMessage());
//            throw new RuntimeException();
//        }
        return reportContent;
    }

    @Override
    public byte[] generateFakturRingkasanRekapV1(Long id) throws Exception {
        Ringkasan ringkasan = ringkasanDaoService.findById(id);
        BapResponse response = bapServices.findByBapNo(ringkasan.getBapNo());
        MasterMitra mitraResponse = new MasterMitra();
        MasterPegawai masterPegawai = new MasterPegawai();
        Rekom rekom = new Rekom();

        String tglStart = "";
        String tglEnd= "";
        String bankType = "";

        if(response.getBap().getMaster() != null){
            if(response.getBap().getMaster().getMitraId() != null){
                Optional<MasterMitra> mitra = masterMitraDaoServices.getById(Long.valueOf(response.getBap().getMaster().getMitraId()));
                if(mitra.isPresent()){
                    mitraResponse = mitra.get();
                }
            }

        }

        if(ringkasan != null){
            if (ringkasan.getMitraId() != null){
                Optional<MasterMitra> mitra = masterMitraDaoServices.getById(Long.valueOf(ringkasan.getMitraId()));
                if(mitra.isPresent()){
                    mitraResponse = mitra.get();
                }else {
                    System.out.println("Mitra Not Found ");
                }
            }
        }

        if(response.getRingkasan().getNamaPegawai() != null){
            List<MasterPegawai> list = masterPegawaiDaoService.getByName(response.getRingkasan().getNamaPegawai());
            if(list.size() >0){
                masterPegawai = list.get(0);
            }
        }

        if(mitraResponse != null && mitraResponse.getBankAccountType() != null ){
            bankType = mitraResponse.getBankAccountType().equalsIgnoreCase("Bank BJB") ? "Bank BJB" : "NON Bank BJB";
        }

        if(response.getBap().getRekom() != null){
            rekom = response.getBap().getRekom();
        }

        byte[] reportContent;

        JasperReport jasperReport;
        JasperPrint jasperPrint = null;

        try {
            jasperReport = (JasperReport)
                    JRLoader.loadObject(ResourceUtils.getFile("RingkasanFaktur.jasper"));
        } catch (FileNotFoundException | JRException e) {
            try {
                InputStream employeeReportStream
                        = getClass().getResourceAsStream("/RingkasanFakturRekapV1.jrxml");
                jasperReport = JasperCompileManager.compileReport(employeeReportStream);

                JRSaver.saveObject(jasperReport, id.toString());
            } catch (JRException ex) {
                System.out.println("Ini Exception " + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }

        if (response.getRingkasan() == null || response.getMaster() == null){
            throw new Exception("Data Ringkasan Empty");
        }

        if(response.getRingkasan().getTglStartPekerjaan() != null){
            tglStart =  formatterReport.format(response.getRingkasan().getTglStartPekerjaan());
        }

        if(response.getRingkasan().getTglEndPekerjaan() != null){
            tglEnd = formatterReport.format(response.getRingkasan().getTglEndPekerjaan());
        }

        System.out.println("Ini Tgl start : " + tglStart + " - " + tglEnd);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("sumberAnggaran", response.getMaster().getAnggaran());
        parameters.put("noDpa", response.getBap().getMaster().getNoDpa() != null ? response.getBap().getMaster().getNoDpa() : "-");
        parameters.put("tglDpa", response.getBap().getMaster().getTglDpa() != null ? "Tgl. " +  response.getBap().getMaster().getTglDpa() : "-");
//        parameters.put("tglBap", response.getBap().getBapDate() != null ? "Tgl. " +  formatterReport.format(response.getBap().getBapDate()) : "-");
        parameters.put("noBap", response.getBap().getBapNo() != null ? response.getBap().getBapNo() + " Tgl. " +  formatterReport.format(response.getBap().getBapDate()): "-");
        parameters.put("tglDpa", response.getBap().getMaster().getTglDpa() != null ? "Tgl. " +  response.getBap().getMaster().getTglDpa() : "-");
        parameters.put("noSirup", response.getBap().getRingkasan().getNoSirup() != null ? response.getBap().getRingkasan().getNoSirup() : "-");
        parameters.put("kodeRekening", response.getMaster().getKodeSubKegiatan() + response.getMaster().getKodeBelanja());
        parameters.put("program", response.getBap().getMaster().getProgramDesc() != null ? response.getBap().getMaster().getProgramDesc() : "-");
        parameters.put("kegiatan", response.getBap().getMaster().getKegiatanDesc() != null ? response.getBap().getMaster().getKegiatanDesc() : "-");
        parameters.put("kodeSubKegiatan", response.getBap().getMaster().getKodeSubKegiatan() != null ? response.getBap().getMaster().getKodeSubKegiatan() : "-");
        parameters.put("subKegiatan", response.getBap().getMaster().getSubKegitanDesc() != null ? response.getBap().getMaster().getSubKegitanDesc() : "-");
        parameters.put("pekerjaan", response.getBap().getMaster().getPekerjaan() != null ? response.getBap().getMaster().getPekerjaan() : "-");
        parameters.put("lokasi", response.getRingkasan().getLocation() != null ? response.getRingkasan().getLocation()  : "-");
        parameters.put("belanja", response.getBap().getMaster().getBelanjaDesc() != null ? response.getBap().getMaster().getBelanjaDesc()  : "-");
        parameters.put("noSpk", response.getBap().getMaster().getSpkNo() != null ? response.getBap().getMaster().getSpkNo() : "-");
        parameters.put("tglSpk", response.getBap().getMaster().getTglSpk() != null ? "Tgl. " + response.getBap().getMaster().getTglSpk() : "-");
        parameters.put("adendum", response.getBap().getRingkasan().getAddendumDetail() != null ? response.getBap().getRingkasan().getAddendumDetail()  : "-");
        parameters.put("namaPerusahaan", mitraResponse.getCorporateName() != null ? mitraResponse.getCorporateName() : "-");
        parameters.put("alamatPerusahaan", mitraResponse.getAddress() != null ?  mitraResponse.getAddress() : "-");
        parameters.put("pimpinanPerusahaan", mitraResponse.getHeadOffice() != null ? mitraResponse.getHeadOffice() : "-");
        parameters.put("npwp", mitraResponse.getNpwp() != null ?  mitraResponse.getNpwp(): "-");
        parameters.put("nomorRekeningPerusahaan",mitraResponse.getRekeningNumber() != null ? mitraResponse.getRekeningNumber()  : "-");
        parameters.put("bankCode", bankType.isEmpty() ? "-" : bankType);
        parameters.put("nilaiSpk", response.getBap().getMaster().getNilaiSpk() != null ?  "Rp. " +  df.format(response.getBap().getMaster().getNilaiSpk()) : "-");
        parameters.put("nilaiBap", response.getBap().getBapAmount() != null ? "Rp. " + df.format(response.getBap().getBapAmount()) : "-");
        parameters.put("uraian", response.getMaster().getPekerjaan() != null ? response.getMaster().getPekerjaan() : "-");
        parameters.put("tipePembayaran", response.getBap().getPaymentMethodDesc() != null ? response.getBap().getPaymentMethodDesc() : "-");
        parameters.put("parameterPembayaran", "16. " + setLabelBastp(response.getRingkasan().getPaymentId()));
        parameters.put("noBastp", response.getRingkasan().getPaymentNo() != null ? response.getRingkasan().getPaymentNo() + " Tgl. " + formatterReport.format(response.getRingkasan().getPaymentDate()) : "-");
//        parameters.put("tglBastp", response.getRingkasan().getPaymentDate() != null ? "Tgl. " + formatterReport.format(response.getRingkasan().getPaymentDate())  : "-");
        parameters.put("jangkaWaktuPelaksanaan", tglStart + " - " + tglEnd );
        parameters.put("tglPenyelesaian",response.getBap().getRingkasan().getTglEndPekerjaan() != null ? formatterReport.format(response.getBap().getRingkasan().getTglEndPekerjaan()) : "-");
        parameters.put("jangkaWaktuPemeliharaan", response.getBap().getRingkasan().getMaintenanceTime() != null ? response.getBap().getRingkasan().getMaintenanceTime() : "-");
        parameters.put("noRekom",  (rekom != null && rekom.getNoRekom() != null)?  rekom.getNoRekom() : "-");
        parameters.put("tglRekom", (rekom != null && rekom.getTglRekom() != null) ?  formatterReport.format(rekom.getTglRekom()) : "-");
        parameters.put("ppkName", response.getBap().getRingkasan().getNamaPegawai() != null ? response.getBap().getRingkasan().getNamaPegawai() : "-");
        parameters.put("nipPpk", masterPegawai.getNip() != null ? "NIP : " +  masterPegawai.getNip() : "-");


//        try {
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
//        }catch (JRException e){
//            System.out.println( "JRE Exception" + e.getMessage());
//            throw new RuntimeException();
//        }
        return reportContent;
    }

    @Override
    public Ringkasan removeById(Long id) throws Exception {
        Ringkasan ringkasanDb = ringkasanDaoService.findById(id);
        List<Bap> bap = bapServices.findByBapNoV2(ringkasanDb.getBapNo());

        if(bap.size() > 0 && bap.get(0).getKwitansi() == null){
            ringkasanDb.setDeletedDate(LocalDate.now().toString());
            ringkasanDb.setDeletedTime(LocalDateTime.now());
            ringkasanDb.setIsDeleted(true);
            ringkasanDaoService.submit(ringkasanDb);
            return ringkasanDb;
        }else {
            throw new Exception("Data Can't Be Removed");
        }



    }

    @Override
    public DeletedCollection removeByIdV2(Map<String, String> params) throws Exception {
        DeletedCollection deletedCollection = new DeletedCollection();

        String id = params.get("id");
        String deletedBy = params.get("deletedBy");

        Bap bap = bapDaoServices.findById(Long.valueOf(id));

        if(bap != null){
            deletedCollection.setNoTrx(bap.getBapNo());
            deletedCollection.setTypeTrx(TRX_CODE_RINGKASAN);
            deletedCollection.setValueTrx(String.valueOf(bap.getRingkasan()));
            deletedCollection.setDeletedBy(deletedBy);
            deletedCollection.setDeletedDate(LocalDateTime.now().toString());
            deletedCollection.setDeletedTime(LocalDateTime.now() );

            collectionDaoServices.Submit(deletedCollection);
            ringkasanDaoService.removeById(bap.getRingkasan().getId());
        }

        return deletedCollection;
    }

    @Override
    public List<Ringkasan> findAllDataByTahun(String tahun) {
        return ringkasanDaoService.findAll();
    }


    private String setLabelBastp(String pembayaran){
        switch (pembayaran){
            case "100%":
                return  "No BASTP / BASTB";
            case "95%":
                return  "No BASTP / BASTB";
            case "Uang Muka":
                return  "No Jaminan Uang Muka";
            case "5%":
                return  "No Jaminan";
            case "Termin 1":
                return  "No BASTP / BASTB";
            case "Termin 2":
                return  "No BASTP / BASTB";
            case "Termin 3":
                return  "No BASTP / BASTB";
        }

        if(pembayaran.substring(0,9).equalsIgnoreCase("Uang Muka")){
            return  "No Jaminan Uang Muka";
        }

        return "-";
    }
}
